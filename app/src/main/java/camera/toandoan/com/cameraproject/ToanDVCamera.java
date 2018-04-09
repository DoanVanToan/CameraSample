package camera.toandoan.com.cameraproject;

import android.hardware.Camera;

/**
 * Created by doan.van.toan on 4/9/18.
 */

public class ToanDVCamera {
    private int mCameraType;
    private Camera mCamera;

    public ToanDVCamera(int cameraType, Camera camera) {
        mCameraType = cameraType;
        mCamera = camera;
    }

    public int getCameraType() {
        return mCameraType;
    }

    public void setCameraType(int cameraType) {
        mCameraType = cameraType;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
    }
}
