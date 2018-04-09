package camera.toandoan.com.cameraproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by doan.van.toan on 4/9/18.
 */

public class CameraUtils {
    private CameraUtils() {
    }

    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    public static Camera getCameraInstance(int type) {
        Camera c;
        try {
            c = Camera.open(type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return c;
    }
}
