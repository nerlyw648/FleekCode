package fleek.code.models;

import java.nio.file.Path;

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
}
