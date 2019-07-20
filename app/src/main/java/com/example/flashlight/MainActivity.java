package com.example.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    Camera camera=null;
    Camera.Parameters parameters;
    boolean isflash=false;
    boolean isOn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = (ImageButton) findViewById(R.id.img_btn);
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH));
        {
            try
            {
                camera = Camera.open();
                parameters = camera.getParameters();
                isflash = true;
            }
            catch (Exception e)
            {
               // Toast.makeText(getApplicationContext(),"Not found",Toast.LENGTH_LONG).show();
            }
        }


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isflash)
                {
                           if(!isOn)
                           {
                               imageButton.setImageResource(R.drawable.on);
                               parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                               camera.setParameters(parameters);
                               camera.startPreview();
                               isOn = true;
                           }
                           else
                           {
                               imageButton.setImageResource(R.drawable.off);
                               parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                               camera.setParameters(parameters);
                               camera.startPreview();
                               isOn = false;
                           }
                }
                else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("Flash light is not available in this device");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(camera != null)
        {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
