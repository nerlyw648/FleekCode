package fleek.code.utils.storage;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fleek.code.App;
import fleek.code.utils.ObjectList;

public class AssetHelper {

    public static void copyAssets(Context context, String assetDir, String destDir) throws IOException {
        AssetManager assetManager = context.getAssets();
        String[] files = assetManager.list(assetDir);
        if (files == null) return;

        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdirs();
        }

        for (String file : files) {
            String assetPath = assetDir + "/" + file;
            String destPath = destDir + "/" + file;

            try {
                if (isAssetDir(assetManager, assetPath)) {
                    copyAssets(context, assetPath, destPath);
                } else {
                    copyAssetFile(assetManager, assetPath, destPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isAssetDir(AssetManager assetManager, String path) {
        try {
            String[] files = assetManager.list(path);
            return files != null && files.length > 0;
        } catch (IOException e) {
            return false;
        }
    }

    private static void copyAssetFile(AssetManager assetManager, String assetPath, String destPath) throws IOException {
        InputStream in = assetManager.open(assetPath);
        OutputStream out = new FileOutputStream(destPath);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        in.close();
        out.flush();
        out.close();
    }

    public static ObjectList<String> getAssetDirectories(Context context, String assetDir) throws IOException {
        AssetManager assetManager = context.getAssets();
        ObjectList<String> directories = new ObjectList<>();

        String[] files = assetManager.list(assetDir);
        if (files == null) return directories;

        for (String file : files) {
            String assetPath = assetDir + "/" + file;
            if (isAssetDir(assetManager, assetPath)) {
                directories.add(assetPath);
            }
        }

        return directories;
    }

    public static Drawable getDrawableFromAssets(String path) {
        try {
            final InputStream ims = App.getContext().getAssets().open(path);
            final Drawable drawable = Drawable.createFromStream(ims, null);
            ims.close();
            return drawable;
        } catch (IOException e) {
            return null;
        }
    }
}