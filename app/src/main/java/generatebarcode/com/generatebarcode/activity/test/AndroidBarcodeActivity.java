package generatebarcode.com.generatebarcode.activity.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.fragment.BarcodeFragment;

public class AndroidBarcodeActivity extends Activity {
    FrameLayout frameLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_barcode);
        frameLayout= (FrameLayout) findViewById(R.id.frameLayout);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, new BarcodeFragment(),"Login");
        ft.commit();

      /*  ButterKnife.bind(this);
        Intent intent = getIntent();
        String code_type = intent.getStringExtra("code_type");
        String footer = intent.getStringExtra("footer");

        AndroidBarcodeView view = new AndroidBarcodeView(this, footer, code_type);
        main_layout.addView(view);*/


    }

}