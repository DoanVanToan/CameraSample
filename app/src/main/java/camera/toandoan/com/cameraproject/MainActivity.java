package camera.toandoan.com.cameraproject;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_CAMERA_PERMISION = 1;

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private int mCameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionButton();
        if (!CameraUtils.checkCameraHardware(this)) {
            return;
        }
        if (!checkCameraPermission()) {
            return;
        }
        initCameraPreview(mCameraType);
    }

    private void initActionButton() {
        findViewById(R.id.button_screen_shot).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CAMERA_PERMISION) {
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initCameraPreview(mCameraType);
        }
    }

    @Override
    public void onClick(View view) {
        mCameraPreview.takePicture(new CameraPreview.TakePictureCallback() {
            @Override
            public void onTakePictureSuccess(File file) {
                Toast.makeText(MainActivity.this, file.getPath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTakePictureFailure(Exception error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCameraPreview(int typeCamera) {
        mCamera = CameraUtils.getCameraInstance(typeCamera);
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout previewLayout = findViewById(R.id.frame_camera);
        previewLayout.addView(mCameraPreview);
    }

    private boolean isCameraPermisionGrant(String permission) {
        if (ActivityCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermision(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            // TODO: 4/9/18
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    REQUEST_CAMERA_PERMISION);
        }
    }

    private boolean checkCameraPermission() {
        boolean result = true;
        for (String permission : PERMISSIONS) {
            if (!isCameraPermisionGrant(permission)) {
                requestPermision(permission);
                result = false;
            }
        }
        return result;
    }


}
