package generatebarcode.com.generatebarcode.activity.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.utils.Helpers;
import generatebarcode.com.generatebarcode.utils.IBarcode;
import generatebarcode.com.generatebarcode.utils.TypefaceCache;

public class SelfGenerateActivity extends AppCompatActivity {
    ImageView gambarBarcode;
    TextView textView1;
    TextView textView2;
    Bitmap bitmap = null;
    int code_type;
    String input_data;
    String description;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_latest);
        initActionbar();
        gambarBarcode = (ImageView) findViewById(R.id.img_barcode);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        input_data = intent.getStringExtra("footer");
        description = intent.getStringExtra("header");
        textView2.setText(description);
        bitmap = Helpers.barcodeBitmap(code_type, input_data, 600, 300);
        if (code_type== IBarcode.UPCA){
            input_data = Helpers.getUPCACheckSumString(input_data);
        }
        if (bitmap != null) {
            gambarBarcode.setImageBitmap(bitmap);
            textView1.setText(input_data);
        } else {
            textView1.setText("Barcode is empty");
        }
       /* try {
            bitmap = Helpers.encodeAsBitmap(footer, BarcodeFormat.CODE_128, 600, 300);
            gambarBarcode.setImageBitmap(bitmap);
            textnya.setText(footer);
        } catch (WriterException e) {
            e.printStackTrace();
        }*/
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#293339")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("Barcode");
      //  actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
