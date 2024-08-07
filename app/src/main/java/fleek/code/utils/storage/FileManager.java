package fleek.code.utils.storage;

import android.content.Context;
import android.os.Environment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import fleek.code.utils.ObjectList;

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

    public static void copyDirectory(String sourceDir, String targetDir) {
        try {
            final Path sourcePath = Paths.get(sourceDir);
            final Path targetPath = Paths.get(targetDir);

            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath);
            }

            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = targetPath.resolve(sourcePath.relativize(dir));
                    if (!Files.exists(targetDir)) {
                        Files.createDirectory(targetDir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, targetPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {}
    }

    public static void deleteDirectory(String dirPath) {
        try {
            final Path directory = Paths.get(dirPath);

            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {}
    }

    public static boolean writeFile(String filePath, String data) {
        final Path path = Paths.get(filePath);

        try {
            Files.write(path, data.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            return false;
        }
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