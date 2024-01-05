package jtamaro.internal.playground.executor;

import java.util.Map;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControlProvider;
import jdk.jshell.spi.ExecutionEnv;

/**
 * Provider for the {@link LocalJvmExecutionControl} Execution Control.
 */
public class LocalJvmExecutionControlProvider implements ExecutionControlProvider {

    private LocalJvmExecutionControl control;

    public LocalJvmExecutionControl get() {
        return control;
    }

    @Override
    public String name() {
        return "LocalJvm";
    }

    @Override
    public ExecutionControl generate(ExecutionEnv env, Map<String, String> parameters) {
        this.control = new LocalJvmExecutionControl();
        return control;
    }
}