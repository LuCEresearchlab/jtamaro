package jtamaro.internal.shell.executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import jdk.jshell.execution.DirectExecutionControl;
import jdk.jshell.spi.SPIResolutionException;

/**
 * An ExecutionControl very similar to {@link jdk.jshell.execution.LocalExecutionControl} but which
 * also logs the actual result of an invocation before being serialized.
 *
 * <p>Based on <a href="https://github.com/SpencerPark/IJava">work</a> by Spencer Park
 * published under the MIT License.
 */
public class LocalJvmExecutionControl extends DirectExecutionControl {
    private static final Object NULL = new Object();

    private static final AtomicInteger EXECUTOR_THREAD_ID = new AtomicInteger(0);

    private final ExecutorService executor;

    private final Map<String, Object> results = new ConcurrentHashMap<>();

    public LocalJvmExecutionControl() {
        this.executor = Executors.newCachedThreadPool(r ->
                new Thread(r, "LocalJVM-executor-" + EXECUTOR_THREAD_ID.getAndIncrement()));
    }

    public Object takeResult(String key) {
        Object result = this.results.remove(key);
        if (result == null)
            throw new IllegalStateException("No result with key: " + key);
        return result == NULL ? null : result;
    }

    private Object execute(String key, Method doitMethod) throws Exception {
        final Future<Object> runningTask = this.executor.submit(() -> doitMethod.invoke(null));
        try {
            return runningTask.get();
        } catch (CancellationException e) {
            if (this.executor.isShutdown()) {
                // If the executor is shutdown, the situation is the former in which
                // case the protocol is to throw an ExecutionControl.StoppedException.
                throw new StoppedException();
            } else {
                throw e;
            }
        } catch (ExecutionException e) {
            // The execution threw an exception. The actual exception is the cause of the ExecutionException.
            Throwable cause = e.getCause();
            if (cause instanceof InvocationTargetException) {
                // Unbox further
                cause = cause.getCause();
            }
            if (cause == null) {
                throw new UserException("null", "Unknown Invocation Exception", e.getStackTrace());
            } else if (cause instanceof SPIResolutionException sre) {
                throw new ResolutionException(sre.id(), cause.getStackTrace());
            } else {
                throw new UserException(String.valueOf(cause.getMessage()),
                        cause.getClass().getName(), cause.getStackTrace());
            }
        }
    }

    /**
     * This method was hijacked and actually only returns a key that can be
     * later retrieved via {@link #takeResult(String)}. This should be called
     * for every invocation as the objects are saved and not taking them will
     * leak the memory.
     * <p></p>
     * {@inheritDoc}
     *
     * @returns the key to use for {@link #takeResult(String) looking up the result}.
     */
    @Override
    protected String invoke(Method doitMethod) throws Exception {
        final String id = UUID.randomUUID().toString();
        final Object value = this.execute(id, doitMethod);
        this.results.put(id, value);
        return id;
    }
}