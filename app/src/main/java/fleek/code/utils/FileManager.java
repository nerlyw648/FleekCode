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

    public static ObjectList<Path> getFilesFromDir(boolean recursive) throws IOException {
        final Path path = getPath();

        final ObjectList<Path> fileList = new ObjectList<>();
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
        return fileList;
    }
}