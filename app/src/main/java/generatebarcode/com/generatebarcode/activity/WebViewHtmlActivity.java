package generatebarcode.com.generatebarcode.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.model.RandomDataModel;
import generatebarcode.com.generatebarcode.utils.Helpers;
import generatebarcode.com.generatebarcode.utils.IBarcode;

public class WebViewHtmlActivity extends AppCompatActivity {
    WebView webView;
    String input_data, header, footer, code_name;
    int code_type, quantity;
    String imageString = "";
    byte[] bufferChild;
    String htmlContent = "";
    public static ProgressDialog mProgressDialog;
    private AlertDialog dialog;
    Bitmap bitmap = null;
    public ArrayList<RandomDataModel> arrayList;
    private FloatingActionButton mFloatingButton;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_html);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        mFloatingButton = (FloatingActionButton) findViewById(R.id.floating_button);
        mFloatingButton.bringToFront();

        Intent intent = getIntent();
        input_data = intent.getStringExtra("input_data");
        header = intent.getStringExtra("header");
        footer = intent.getStringExtra("footer");
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        quantity = Integer.parseInt(intent.getStringExtra("quantity"));
        code_name = intent.getStringExtra("code_name");
        initActionbar(code_name);
        Helpers.darkenStatusBar(WebViewHtmlActivity.this,R.color.colorPrimary);
        mProgressDialog = new ProgressDialog(WebViewHtmlActivity.this);
        mProgressDialog.setMessage("Wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (input_data.equals("")) {
            mFloatingButton.setVisibility(View.GONE);
        } else {
            mFloatingButton.setVisibility(View.VISIBLE);
        }
        arrayList = new ArrayList<>();
        generateBarcode(input_data, header, footer, quantity, code_type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.plus_button_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void add(View v) {
        fun();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.icon_id:
                createWebPrintJob(webView);
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

    public void fun() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogForm = inflater.inflate(R.layout.add_barcode_dailog, null, false);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        final EditText mInputData = (EditText) dialogForm.findViewById(R.id.input_data);
        final TextView codeName = (TextView) dialogForm.findViewById(R.id.code_name);
        final EditText mHeader = (EditText) dialogForm.findViewById(R.id.header);
        final EditText mFooter = (EditText) dialogForm.findViewById(R.id.footer);
        codeName.setText(code_name);
        validationCheck(code_type, mInputData, input_data.length());
        mHeader.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = s.toString().indexOf("\n");
                if (i != -1) {
                    s.replace(i, i + 1, "<br>");
                }
                System.out.println(s);
            }
        });
        Button buttonSubmit = (Button) dialogForm.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bool = "";
                if (!mInputData.getText().toString().equals("")) {
                    if (!mHeader.getText().toString().equals("")) {
                        if (!mFooter.getText().toString().equals("")) {
                            bool = submitFun(code_type, mInputData, input_data.length());
                            if (bool.equals("")) {
                                dialog.dismiss();
                                // htmlContent = "";
                                //   generateBarcode(mInputData.getText().toString(), mHeader.getText().toString(), mInputData.getText().toString(), 1, code_type);
                                String input = mInputData.getText().toString();
                                addMoreBarcode( mFooter.getText().toString(), Helpers.barcodeBitmap(code_type, input, widthCheck(code_type), heightCheck(code_type)), mHeader.getText().toString());
                            } else {
                                mInputData.setError(bool);
                            }
                        } else {
                            mFooter.setError("Footer can't be empty!");
                        }
                    } else {
                        mHeader.setError("Header can't be empty!");
                    }
                } else {
                    mInputData.setError("Input data can't be empty!");
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    void generateBarcode(String input_data, String header, String footer, int quantity, int code_type) {
        for (int i = 0; i < quantity; i++) {
            if (input_data.equals("")) {
                String random = Helpers.barcodeRandomString(code_type);
                arrayList.add(new RandomDataModel(footer, Helpers.barcodeBitmap(code_type, random, widthCheck(code_type), heightCheck(code_type)), header));
            } else {
                bitmap = Helpers.barcodeBitmap(code_type, input_data, widthCheck(code_type), heightCheck(code_type));
                arrayList.add(new RandomDataModel(footer, bitmap, header));
            }
        }
        waitFun();
    }

    void waitFun() {
        if (input_data.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showWebView();
                }
            }, 3 * 1000);
        } else {
            if (quantity != 1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showWebView();
                    }
                }, 4 * 1000);
            } else {
                //showWebView();
                addMoreBarcode(footer, Helpers.barcodeBitmap(code_type, input_data, widthCheck(code_type), heightCheck(code_type)), header);
            }
        }
    }

    void showWebView() {
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                Bitmap bitmap = arrayList.get(i).bitmap;
                String header = arrayList.get(i).header;
                String footer = arrayList.get(i).footer;
                int sizeChild = 0;
                InputStream isChild = getAssets().open("barcode.html");
                sizeChild = isChild.available();
                bufferChild = new byte[sizeChild];
                isChild.read(bufferChild);
                isChild.close();
                imageString = new String(bufferChild);

                // String dataURL = "data:image/png;base64," + BitMapToString(BitmapFactory.decodeResource(this.getResources(), R.drawable.pdf_icon));
                imageString = imageString.replace("Header", header);
                String dataURL = "data:image/png;base64," + BitMapToString(bitmap);
                imageString = imageString.replace("{SIGNATURE_PLACEHOLDER}", dataURL);
                imageString = imageString.replace("Footer", footer);
                htmlContent = htmlContent + imageString;
            }
        } catch (Exception e) {
            Toast.makeText(WebViewHtmlActivity.this, "catch", Toast.LENGTH_SHORT).show();
        }
        webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null);
        mProgressDialog.dismiss();
    }

    void addMoreBarcode(String footer, Bitmap bitmap, String header) {
        try {
            int sizeChild = 0;
            InputStream isChild = getAssets().open("barcode.html");
            sizeChild = isChild.available();
            bufferChild = new byte[sizeChild];
            isChild.read(bufferChild);
            isChild.close();
            imageString = new String(bufferChild);

            // String dataURL = "data:image/png;base64," + BitMapToString(BitmapFactory.decodeResource(this.getResources(), R.drawable.pdf_icon));
            imageString = imageString.replace("Header", header);
            String dataURL = "data:image/png;base64," + BitMapToString(bitmap);
            imageString = imageString.replace("{SIGNATURE_PLACEHOLDER}", dataURL);
            imageString = imageString.replace("Footer", footer);
            htmlContent = htmlContent + imageString;
        } catch (Exception e) {
            Toast.makeText(WebViewHtmlActivity.this, "catch", Toast.LENGTH_SHORT).show();
        }
        webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null);
    }

    void validationCheck(int code_type, EditText input_data, int length) {
        if (code_type == IBarcode.CODE39) {
            input_data.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            // input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        } else if (code_type == IBarcode.EAN13) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        } else if (code_type == IBarcode.EAN8) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (code_type == IBarcode.CODABAR) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (code_type == IBarcode.UPCE) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (code_type == IBarcode.UPCA) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        } else if (code_type == IBarcode.ITF) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
        } else {
            input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        }
    }

    String submitFun(int code_type, EditText input_data, int limit) {
        String bool = "";
        if (code_type == IBarcode.CODE39) {
            if (input_data.getText().toString().contains("_")) {
                bool = "No special  _  character required!";
            }
        } else if (code_type == IBarcode.EAN13) {
            if (input_data.getText().toString().length() != 12) {
                bool = "Input Data is 12 digit!";
            }
        } else if (code_type == IBarcode.EAN8) {
            if (input_data.getText().toString().length() != 8) {
                bool = "Input Data is 8 digit!";
            }
        } else if (code_type == IBarcode.UPCE) {
            if (input_data.getText().toString().length() != 6) {
                bool = "Input Data is 6 digit!";
            }
        } else if (code_type == IBarcode.UPCA) {
            if (input_data.getText().toString().length() != 11) {
                bool = "Input Data is 11 digit!";
            }
        } else if (code_type == IBarcode.ITF) {
            if (input_data.getText().toString().length() != 14) {
                bool = "Input Data is 14 digit!";
            }
        } /*else {
            if (input_data.getText().toString().length() != limit) {
                bool = "Input Data is " + limit + " digit!";
            }*/
        return bool;
    }

    int widthCheck(int code_type) {
        int width = 250;
        if (code_type == IBarcode.QR_CODE) {
            width = 180;
        } else if (code_type == IBarcode.CODABAR) {
            width = 1000;
        } else if (code_type == IBarcode.DATA_MATRIX) {
            width = 0;
        }else if (code_type == IBarcode.ITF) {
            width = 290;
        }
        return width;
    }

    int heightCheck(int code_type) {
        int height = 80;
        if (code_type == IBarcode.QR_CODE) {
            height = 150;
        } else if (code_type == IBarcode.CODABAR) {
            height = 280;
        } else if (code_type == IBarcode.DATA_MATRIX) {
            height = 0;
        } else if (code_type == IBarcode.AZTEC) {
            height = 200;
        }else if (code_type == IBarcode.PDF417) {
            height = 100;
        }
        return height;
    }
}