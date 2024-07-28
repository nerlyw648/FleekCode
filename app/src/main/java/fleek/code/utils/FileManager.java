package fleek.code.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileManager {
    public static void initWorkspace(Context context) throws IOException {
        final Path path = getPath();
        if (!Files.exists(path)) Files.createDirectories(path);
    }

    public static Path getPath() {
        return Paths.get(Environment.getExternalStorageDirectory() + "/FleekCode");
    }

    public static boolean exists(String path) {
        return Files.exists(Paths.get(getPath().toString() + "/" + path));
    }

    public static String readFile(String path) {
        try {
            final ObjectList<String> lines = ObjectList.of();
            Files.lines(Paths.get(path)).forEach(lines::add);
            return String.join("\n", lines);
        } catch (Exception error) {
            return null;
        }
    }

    public static ObjectList<Path> getFilesFromDir(boolean recursive) {
        return getFilesFromDir(null, recursive);
    }

    public static ObjectList<Path> getFilesFromDir(String dirPath, boolean recursive) {
        final Path path = (dirPath == null ? getPath() : Paths.get(dirPath));
        final ObjectList<Path> fileList = new ObjectList<>();

        try {
            if (recursive) {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        fileList.add(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        fileList.add(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                    for (Path entry : stream) {
                        fileList.add(entry);
                    }
                }
            }
        } catch (IOException error) {}

        return fileList;
    }
}