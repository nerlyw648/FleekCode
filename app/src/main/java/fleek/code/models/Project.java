package fleek.code.models;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fleek.code.utils.FileManager;
import fleek.code.utils.ObjectList;

public class Project {
    public static String ANDROID = "android";
    public static String JAVA = "java";
    public static String UNKNOWN = "unknown";
    public static String KOTLIN = "kotlin"; // todo: add

    public String name;
    public String type;
    public ObjectList<Path> openedFiles = new ObjectList<>();

    public boolean isGradleSupport = true;

    public static Project loadProject(Path projectPath) {
        final Project project = new Project();
        project.name = projectPath.getFileName().toString();

        final Path path = Paths.get(projectPath.toString() + "/settings.gradle");
        if (Files.exists(path) && !Files.isDirectory(path)) {
            final String settings = FileManager.readFile(path.toString());
            final Matcher includeMatcher = Pattern.compile("include(\s|)':(.*?)'").matcher(settings);

            if (includeMatcher.find()) {
                final String includeModule = includeMatcher.group(2);
                final Path manifestPath = Paths.get(projectPath + "/" + includeModule + "/src/main/AndroidManifest.xml");

                if (Files.exists(manifestPath) && !Files.isDirectory(manifestPath)) {
                    project.type = Project.ANDROID;
                } else project.type = Project.JAVA;

            }
        } else {
            final ObjectList<Path> javaNonGradleProject = FileManager.getFilesFromDir(projectPath.toString(), true);

            if (javaNonGradleProject.stream()
                    .filter(filePath ->
                            !Files.isDirectory(filePath) && Pattern.compile("\\.java")
                                    .matcher(filePath.getFileName().toString()).find())
                    .findFirst().orElse(null) != null) {
                project.type = Project.JAVA;
                project.isGradleSupport = false;
            } else project.type = Project.UNKNOWN;
        }

        return project;
    }
}
