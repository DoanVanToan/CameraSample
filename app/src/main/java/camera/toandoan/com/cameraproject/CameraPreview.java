package camera.toandoan.com.cameraproject;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by doan.van.toan on 4/9/18.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mHolder = surfaceHolder;
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mHolder.getSurface() == null) {
            return;
        }

        mCamera.stopPreview();
        initCamera();

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {

    }

    public void takePicture(final TakePictureCallback callback) {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                savePicture(bytes, callback);
            }
        });
    }

    private void savePicture(byte[] bytes, TakePictureCallback callback) {
        File pictureFile = FileUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            callback.onTakePictureFailure(new Exception("File not found exeption"));
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onTakePictureFailure(e);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onTakePictureFailure(e);
        }
        callback.onTakePictureSuccess(pictureFile);
    }

    private void initCamera() {
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewCallback(this);
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    /**
     * Release camera when finish
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public interface TakePictureCallback {
        void onTakePictureSuccess(File file);

        void onTakePictureFailure(Exception error);
    }

}