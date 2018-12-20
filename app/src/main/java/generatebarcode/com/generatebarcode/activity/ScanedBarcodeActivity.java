package generatebarcode.com.generatebarcode.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.utils.Helpers;
import generatebarcode.com.generatebarcode.utils.IBarcode;

public class ScanedBarcodeActivity extends AppCompatActivity {
    Bitmap bitmap = null;
    String input = "";
    int code_type;
    @Bind(R.id.image_view)
    ImageView mImageView;
    @Bind(R.id.image_text)
    TextView mImageText;
    @Bind(R.id.code_value)
    TextView mCodeValue;
    @Bind(R.id.code_name)
    TextView mCodeName;
    @Bind(R.id.date_time)
    TextView mDateTime;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.submit_icon)
    ImageView mSubmitIcon;
    @Bind(R.id.submit_txt)
    TextView mSubmitText;
    @Bind(R.id.copy)
    LinearLayout mCopyLayout;
    @Bind(R.id.share)
    LinearLayout mShare;

    public static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaned_barcode);
        ButterKnife.bind(this);
        Helpers.darkenStatusBar(ScanedBarcodeActivity.this, R.color.colorPrimary);
        Intent intent = getIntent();
        input = intent.getStringExtra("input");
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        String checker = barTitle(input);
        initActionbar("Scanned Barcode");
        if (!checker.equals("")) {
            mSubmitText.setText("Open in browser");
            mSubmitIcon.setImageResource(R.drawable.browser);
        } else {
            mSubmitText.setText("Search on the web");
            mSubmitIcon.setImageResource(R.drawable.browser);
        }
        if (code_type == IBarcode.EAN13) {
            bitmap = Helpers.barcodeBitmap(code_type, input.substring(0, input.length() - 1), 900, 700);
        } else {
            bitmap = Helpers.barcodeBitmap(code_type, input, 900, 700);
        }
        if (bitmap!=null){
            mImageView.setImageBitmap(bitmap);
            mImageView.setVisibility(View.VISIBLE);
            mImageText.setVisibility(View.GONE);
        }else {
            mImageView.setVisibility(View.GONE);
            mImageText.setVisibility(View.VISIBLE);
        }
        String arr[] = Helpers.barcodeScan(code_type, input).split("=");
        mCodeName.setText(arr[0]);
        mCodeValue.setText(input);

        mCopyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipBoard(input);
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt(input);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.startsWith("http")) {
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(input));
                    startActivity(browserIntent);
                } else {
                    String query = null;
                    try {
                        query = URLEncoder.encode(input, "utf-8");
                        String url = "http://www.google.com/search?q=" + query;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

    private void initActionbar(String code_name) {
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
        actionbarTitle.setText(code_name);
        //  actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    String barTitle(String input) {
        if (input.startsWith("http")) {
            input = "Website";
        } else {
            input = "";
        }
        return input;
    }

    private void copyToClipBoard(String input) {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(
                "text label",
                input);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(ScanedBarcodeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private void shareIt(String input) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Scanned Barcode");
        i.putExtra(android.content.Intent.EXTRA_TEXT, input);
        startActivity(Intent.createChooser(i, "Share via"));
    }
}
