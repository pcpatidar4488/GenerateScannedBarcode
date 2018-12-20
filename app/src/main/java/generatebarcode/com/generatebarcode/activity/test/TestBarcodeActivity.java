package generatebarcode.com.generatebarcode.activity.test;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.utils.Helpers;

public class TestBarcodeActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    @Bind(R.id.spinner)
    Spinner mSpinner;
    @Bind(R.id.edit_txt)
    EditText mEditText;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.barcode_layout)
    LinearLayout mBarcodeLayout;
    @Bind(R.id.barcode_txt)
    TextView barcode_txt;
    public ArrayAdapter<String> mAdapter;
    AndroidBarcodeTestView view;
    CoordinatorLayout layout;
    int height;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        ButterKnife.bind(this);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        double ratio = ((float) (width)) / 300.0;
        height = (int) (ratio * 90);

        mAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, Helpers.barcodeStringList());
        mAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mEditText.getText().toString().equals("")){
                    if (mSpinner.getSelectedItem().toString().contains("Select Barcode Type")){
                        Toast.makeText(TestBarcodeActivity.this, "Plese select barcode type!!!", Toast.LENGTH_SHORT).show();
                    }else if (mSpinner.getSelectedItem().toString().contains("CODABAR")){
                        String s = "CODABAR";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                        barcode_txt.setVisibility(View.GONE);
                    } else  if (mSpinner.getSelectedItem().toString().contains("CODE-128")){
                        String s = "CODE-128";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("CODE-39")){
                        String s = "CODE-39";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("CODE-93")){
                        String s = "CODE-93";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("EAN-13")){
                        String s = "EAN-13";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("EAN-8")){
                        String s = "EAN-8";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("UPC-E")){
                        String s = "UPC-E";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("UPC-A")){
                        String s = "UPC-A";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    } else  if (mSpinner.getSelectedItem().toString().contains("ITF")){
                        String s = "ITF";
                        view = new AndroidBarcodeTestView(getApplicationContext(),mEditText.getText().toString(),s);
                        layout = (CoordinatorLayout) findViewById(R.id.main_layout);
                        view.setLayoutParams(new FrameLayout.LayoutParams(CoordinatorLayout.LayoutParams.FILL_PARENT, height));
                        layout.addView(view);
                    }
                }else {
                    Toast.makeText(TestBarcodeActivity.this, "Plese enter input data!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}