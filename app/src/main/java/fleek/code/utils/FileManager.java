package fleek.code.utils;

import android.content.Context;
import android.os.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public static void initWorkspace(Context context) throws IOException {
        final Path path = Paths.get(Environment.getExternalStorageDirectory() + "/FleekCode");
        if (!Files.exists(path)) Files.createDirectories(path);
    }
}