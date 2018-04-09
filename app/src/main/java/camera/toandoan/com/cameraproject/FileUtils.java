package camera.toandoan.com.cameraproject;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by doan.van.toan on 4/9/18.
 */

public class FileUtils {
    private static final String APP_FOLDER = "MyCameraApp";
    private static final java.lang.String DATE_FOR_MAT_YYYY_MM_DD_HH_MM_SS = "yyyyMMdd_HHmmss";
    private static final java.lang.String IMAGE_ATTENDTION = ".jpg";
    private static final java.lang.String VIDEO_ATTENDTION = ".mp4";

    private FileUtils() {
    }

    public static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type) {
        File mediaStorageDir =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        APP_FOLDER);

        if (!mediaStorageDir.exists()) {
            boolean makeDir = mediaStorageDir.mkdir();
            if (!makeDir) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat(DATE_FOR_MAT_YYYY_MM_DD_HH_MM_SS).format(new Date());
        File mediaFile;
        switch (type) {
            default:
            case MEDIA_TYPE_IMAGE:
                mediaFile = new File(mediaStorageDir.getPath()
                        + File.separator + "IMG_ "
                        + timeStamp + IMAGE_ATTENDTION);
                break;
            case MEDIA_TYPE_VIDEO:
                mediaFile = new File(mediaStorageDir.getPath()
                        + File.separator + "VID_ "
                        + timeStamp + VIDEO_ATTENDTION);
                break;
        }
        return mediaFile;

    }
}
