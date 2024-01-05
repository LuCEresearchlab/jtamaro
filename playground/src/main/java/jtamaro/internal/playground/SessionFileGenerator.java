package jtamaro.internal.playground;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import jdk.jshell.SnippetEvent;

public class SessionFileGenerator {
    private static final String EXPORT_TEMPLATE = """
            // Playground session (%1$s)
            %2$s
            public class %3$s {

                public static void main(String[] $args) {
                    %4$s
                }
                %5$s
            }
            """;

    private SessionFileGenerator() {
    }

    public static Optional<Exception> exportSnippetHistory(JFrame parent, List<SnippetEvent> history) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save session");
        fileChooser.setSelectedFile(new File("Session.java"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Java file", "java"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        final int userSelection = fileChooser.showSaveDialog(parent);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return Optional.of(new CancellationException());
        }

        final Path filePath = fileChooser.getSelectedFile().toPath();
        final String className = filePath.getFileName().toString()
                .replaceAll("[-| ]", "_")
                .replaceAll("\\.java$", "");

        final String contents = generateFileContents(className, history);
        try {
            Files.writeString(filePath, contents);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static String generateFileContents(String className, List<SnippetEvent> history) {
        final List<String> importStatements = new ArrayList<>();
        final List<String> otherDeclarations = new ArrayList<>();
        final List<String> mainStatements = new ArrayList<>();
        final String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        int exprVarCounter = 0;

        for (final SnippetEvent event : history) {
            final String source = event.snippet().source().trim();
            switch (event.snippet().kind()) {
                case IMPORT -> importStatements.add(source);
                case TYPE_DECL -> otherDeclarations.add(source);
                case METHOD -> otherDeclarations.add(source.startsWith("static")
                    ? source
                    : ("static " + source));
                case VAR, STATEMENT -> mainStatements.add(source.endsWith(";")
                    ? source
                    : (source + ";"));
                case EXPRESSION -> mainStatements.add(String.format("var expr%1$d = %2$s;", exprVarCounter++, source));
                case ERRONEOUS -> mainStatements.add("// " + source);
            }
        }

        return String.format(EXPORT_TEMPLATE,
                timeStamp,
                String.join("\n", importStatements),
                className,
                String.join("\n        ", mainStatements),
                String.join("\n    ", otherDeclarations));
    }
}
