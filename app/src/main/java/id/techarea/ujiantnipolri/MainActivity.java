package id.techarea.ujiantnipolri;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static int Splash_Time_Out = 3000;

    public static final int requestCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        askPermissions();

    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.INTERNET",
                "android.permission.ACCESS_NETWORK_STATE"
        };

        int permissionCheckInternalStorage = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadStorage = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionInternet = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET);
        int permissionNetwork = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE);

        // Here, thisActivity is the current activity
        if (permissionCheckInternalStorage != PackageManager.PERMISSION_GRANTED ||
                permissionReadStorage != PackageManager.PERMISSION_GRANTED||
                permissionInternet != PackageManager.PERMISSION_GRANTED||
                permissionNetwork != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    permissions,
                    requestCode);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent appIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(appIntent);
                    finish();
                }
            },Splash_Time_Out);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if(this.requestCode == requestCode) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent appIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(appIntent);
                        finish();
                    }
                },Splash_Time_Out);


            } else {
                askPermissions();
            }
            return;
        }
    }
}
