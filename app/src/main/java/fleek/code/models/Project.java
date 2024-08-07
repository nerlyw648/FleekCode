package fleek.code.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fleek.code.App;
import fleek.code.R;
import fleek.code.utils.storage.AssetHelper;
import fleek.code.utils.storage.FileManager;
import fleek.code.utils.ObjectList;

public class Project {
    public static String ANDROID = "android";
    public static String JAVA = "java";
    public static String UNKNOWN = "unknown";
    public static String KOTLIN = "kotlin"; // todo: add

    public String name;
    public String type = UNKNOWN;
    public ObjectList<Path> openedFiles = new ObjectList<>();

    public boolean isGradleSupport = true;

    public static String parseSdkVersion(String sdkVersion) {
        try {
            final Matcher matcher = Pattern.compile("API (.*?) ").matcher(sdkVersion);
            return matcher.find() ? matcher.group(1) : "";
        } catch (Exception error) {
            return "";
        }
    }

    public static Project createProject(String name, String packageName, String minSdkVersion,
                                        String language, String templateName) throws IOException {
        final String projectPath = FileManager.getPath().toString() + "/" + name;

        AssetHelper.copyAssets(App.getContext(), "templates/" + templateName + "/java", projectPath);

        final String[] sdkVersions = App.getContext().getResources().getStringArray(R.array.sdkVersions);
        final String targetSdkVersion = parseSdkVersion(sdkVersions[sdkVersions.length - 1]);

        final ObjectList<Path> projectFiles = FileManager.getFilesFromDir(projectPath, true);
        final String packageVariable = "$packagename";

        projectFiles.forEach(filePath -> {
            String fileData = FileManager.readFile(filePath.toString());
            if (fileData != null) {
                fileData = fileData.replaceAll("\\" + packageVariable, packageName)
                        .replaceAll("\\$appname", name)
                        .replaceAll("\\$\\{minSdkVersion\\}", minSdkVersion)
                        .replaceAll("\\$\\{targetSdkVersion\\}", targetSdkVersion);
                FileManager.writeFile(filePath.toString(), fileData);
            }
        });

        final Path packagePath = projectFiles.stream().filter(path ->
                Pattern.compile("\\" + packageVariable)
                        .matcher(path.toString()).find()).findFirst().orElse(null);

        if (packagePath != null) {
            final String fullPath = packagePath.toString().split("\\" + packageVariable)[0];
            FileManager.copyDirectory(fullPath + packageVariable,
                    fullPath + packageName.replaceAll("\\.", "/"));
            FileManager.deleteDirectory(fullPath + packageVariable);
        }

        final Project project = new Project();
        project.name = name;

        return project;
    }

    public String getProjectType() {
        if (type.equals(Project.ANDROID)) {
            return "Android Project";
        } else if (type.equals(Project.JAVA) && isGradleSupport) {
            return "Java Project (with Gradle)";
        } else return "Java Project";
    }

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
