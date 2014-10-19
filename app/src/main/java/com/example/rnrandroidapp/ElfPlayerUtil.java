package com.example.rnrandroidapp;

import java.io.File;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class ElfPlayerUtil {

    public static Uri getFileinSD(String filename) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {


            File sdDir = Environment.getExternalStorageDirectory();

            Log.i("yao", "sdDir ==> " + sdDir.toString());

            File file = new File(sdDir, File.separator + "wandoujia" + File.separator + "music" + File.separator + filename);
            Uri uri = Uri.parse("file://" + file.getAbsolutePath());
            Log.i("yao", "uri ==> " + uri.toString());
            return uri;

        } else {

            return null;
        }

    }
}
