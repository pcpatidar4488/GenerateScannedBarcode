package generatebarcode.com.generatebarcode.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;

public class SplashActivity extends Activity {

    @Bind(R.id.mainLayout)
    RelativeLayout mainLayout;
    @Bind(R.id.textView)
    TextView textView;
    final int PERMISSIONS_BUZZ_REQUEST = 0xABC;
    String s = "Barcode Generator ";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion <= 22) {
            handler(s.charAt(i));
        } else {
            checkPermissions();
        }
    }

    void handler(final char c) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i < s.length() - 1) {
                    textView.append("" + c);
                    i = i + 1;
                    handler(s.charAt(i));
                } else {
                    startActivity(new Intent(getApplicationContext(), MenuNavigationActivity.class));
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                    finish();
                }
            }
        }, 200);
    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    PERMISSIONS_BUZZ_REQUEST);
        }else {
            handler(s.charAt(i));
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case PERMISSIONS_BUZZ_REQUEST:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler(s.charAt(i));
                        }
                    }, 1000);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
