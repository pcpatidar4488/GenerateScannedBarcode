package generatebarcode.com.generatebarcode.activity;

import android.app.Activity;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.adapter.BarcodeListAdapter;
import generatebarcode.com.generatebarcode.utils.EventClicked;
import generatebarcode.com.generatebarcode.utils.EventClickedForImage;
import generatebarcode.com.generatebarcode.utils.EventClickedForInfo;
import generatebarcode.com.generatebarcode.utils.Helpers;
import generatebarcode.com.generatebarcode.utils.IBarcode;

public class MenuNavigationActivity extends AppCompatActivity implements View.OnClickListener {
    SlidingPaneLayout mSlidingPanel;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BarcodeListAdapter mAdapter;
    private AlertDialog dialog;
    private AlertDialog dialogInfo;
    AlertDialog dialogPreview;
    boolean isExit;
    Boolean boolCheck = false;
    public static Boolean boolFOrEvent = false;
    public static String footerDesc = "";
    public static String buttonTab = "";
    public String headerDesc = "";
    public static ProgressDialog mProgressDialog;
    RadioButton rb;
    int code_type;
    private Barcode barcodeResult;
    private FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menunavigation_layout);
        ButterKnife.bind(this);
        boolCheck = false;
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(100);
        mSlidingPanel.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));
        mFloatingButton = (FloatingActionButton) findViewById(R.id.floating_button);
        mFloatingButton.bringToFront();
      /*  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        initActionbar("Barcode Type List");
       /* ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Barcode Type List");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu_home);
        }*/
        Helpers.darkenStatusBar(MenuNavigationActivity.this, R.color.colorPrimary);

        // Barcode list
        List<Map> list = new ArrayList<>();
        list = Helpers.barcodeList();
        MenuNavigationActivity.footerDesc = "";
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BarcodeListAdapter(getApplicationContext(), list);
        mRecyclerView.setAdapter(mAdapter);
    }

    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener() {

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub

        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mSlidingPanel.isOpen()) {
                    mSlidingPanel.closePane();
                } else {
                    mSlidingPanel.openPane();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                setButtonSelected(view);
                Toast.makeText(this, "Button dashboard clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                setButtonSelected(view);
                Toast.makeText(this, "Button explore clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                setButtonSelected(view);
                Toast.makeText(this, "Button profile clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                setButtonSelected(view);
                Toast.makeText(this, "Button messages clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                setButtonSelected(view);
                Toast.makeText(this, "Button setting clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLogout:
                mSlidingPanel.closePane();
                Toast.makeText(this, "Button logout clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void setButtonSelected(final View v) {
        mSlidingPanel.closePane();
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.menuContainer);
        for (int index = 0; index < (viewGroup).getChildCount(); ++index) {
            View nextChild = (viewGroup).getChildAt(index);
            nextChild.setBackgroundColor(ContextCompat.getColor(MenuNavigationActivity.this, android.R.color.transparent));
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.setBackground(ContextCompat.getDrawable(MenuNavigationActivity.this, R.drawable.menunavigation_menu_selected_bg));
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void event_click_alert(EventClicked response) {
        String res[] = response.getPosition().split("split");
        code_type = Integer.parseInt(res[0]);
        final String code_des = res[1];
        final String code_name = res[2];
        final String code_info = res[3];
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_dialog, null, false);
        TextView codeName = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        final LinearLayout random_layout = (LinearLayout) dialogForm.findViewById(R.id.random_layout);
        final EditText random_quantity = (EditText) dialogForm.findViewById(R.id.random_quantity);
        final LinearLayout self_layout = (LinearLayout) dialogForm.findViewById(R.id.self_layout);
        final EditText input_data = (EditText) dialogForm.findViewById(R.id.input_data);
        final EditText mHeader = (EditText) dialogForm.findViewById(R.id.header);
        final EditText mFooter = (EditText) dialogForm.findViewById(R.id.footer);
        final RadioGroup radioGroup = (RadioGroup) dialogForm.findViewById(R.id.radioGroup);
        codeName.setText(code_name);
        input_data.setHint(code_des);
        validationCheck(code_type, input_data);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().contains("Random Generate")) {
                        boolCheck = false;
                        //random_layout.setVisibility(View.VISIBLE);
                        self_layout.setVisibility(View.GONE);
                        random_layout.setVisibility(View.VISIBLE);
                    } else {
                        boolCheck = true;
                        // random_layout.setVisibility(View.GONE);
                        self_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

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
        Button buttonPreview = (Button) dialogForm.findViewById(R.id.buttonPreview);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bool = "";
                if (boolCheck) {
                    if (!input_data.getText().toString().equals("")) {
                        if (!mHeader.getText().toString().equals("")) {
                            if (!mFooter.getText().toString().equals("")) {
                                if (!random_quantity.getText().toString().equals("")) {
                                    if (Integer.parseInt(random_quantity.getText().toString()) >= 1) {
                                        bool = submitFun(code_type, input_data);
                                        if (bool.equals("")) {
                                            Bitmap bitmap = Helpers.barcodeBitmap(code_type, input_data.getText().toString(), 500, 200);
                                            if (bitmap != null) {
                                                boolCheck = false;
                                                Intent intent = new Intent(MenuNavigationActivity.this, WebViewHtmlActivity.class);
                                                intent.putExtra("header", mHeader.getText().toString());
                                                intent.putExtra("footer", mFooter.getText().toString());
                                                intent.putExtra("quantity", random_quantity.getText().toString());
                                                intent.putExtra("input_data", input_data.getText().toString());
                                                intent.putExtra("code_type", "" + code_type);
                                                intent.putExtra("code_name", "" + code_name);
                                                startActivity(intent);
                                                dialog.dismiss();
                                            } else {
                                                input_data.setError("Can not generate barcode");
                                            }
                                        } else {
                                            input_data.setError(bool);
                                        }
                                    } else {
                                        random_quantity.setError("Quantity greater than or equal to 1!");
                                    }
                                } else {
                                    random_quantity.setError("Quantity greater than or equal to 1!");
                                }
                            } else {
                                mFooter.setError("Footer can't be empty!");
                            }
                        } else {
                            mHeader.setError("Header can't be empty!");
                        }
                    } else {
                        input_data.setError("Input data can't be empty!");
                    }
                } else {
                    if (!mHeader.getText().toString().equals("")) {
                        if (!mFooter.getText().toString().equals("")) {
                            if (!random_quantity.getText().toString().equals("")) {
                                if (Integer.parseInt(random_quantity.getText().toString()) >= 1) {
                                    boolCheck = false;
                                    hideKeyboard(MenuNavigationActivity.this);
                                    Intent intent = new Intent(MenuNavigationActivity.this, WebViewHtmlActivity.class);
                                    intent.putExtra("header", mHeader.getText().toString());
                                    intent.putExtra("footer", mFooter.getText().toString());
                                    intent.putExtra("quantity", random_quantity.getText().toString());
                                    intent.putExtra("code_type", "" + code_type);
                                    intent.putExtra("input_data", "");
                                    intent.putExtra("code_name", code_name);
                                    startActivity(intent);
                                    dialog.dismiss();
                                } else {
                                    random_quantity.setError("Quantity greater than or equal to 1!");
                                }
                            } else {
                                random_quantity.setError("Quantity greater than or equal to 1!");
                            }
                        } else {
                            mFooter.setError("Footer can't be empty!");
                        }
                    } else {
                        mHeader.setError("Header can't be empty!");
                    }
                }
            }
        });
        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bool;
                boolFOrEvent = false;
                Bitmap bitmap = null;
                if (boolCheck) {
                    if (!input_data.getText().toString().equals("")) {
                        if (!mHeader.getText().toString().equals("")) {
                            if (!mFooter.getText().toString().equals("")) {
                                bool = submitFun(code_type, input_data);
                                if (bool.equals("")) {
                                    bitmap = Helpers.barcodeBitmap(code_type, input_data.getText().toString(), 900, 700);
                                    if (bitmap != null) {
                                        buttonTab = code_name;
                                        headerDesc = mHeader.getText().toString();
                                        footerDesc = mFooter.getText().toString();
                                        headerDesc = headerDesc.replace("<br>", " ");
                                        EventBus.getDefault().post(new EventClickedForImage(bitmap));
                                    } else {
                                        input_data.setError("Can not generate barcode");
                                    }
                                } else {
                                    input_data.setError(bool);
                                }
                            } else {
                                mFooter.setError("Footer can't be empty!");
                            }
                        } else {
                            mHeader.setError("Header can't be empty!");
                        }
                    } else {
                        input_data.setError("Input data can't be empty!");
                    }
                } else {
                    if (!mHeader.getText().toString().equals("")) {
                        if (!mFooter.getText().toString().equals("")) {
                            String input = Helpers.barcodeRandomString(code_type);
                            bitmap = Helpers.barcodeBitmap(code_type, input, 900, 700);
                            if (bitmap != null) {
                                buttonTab = code_name;
                                headerDesc = mHeader.getText().toString();
                                footerDesc = mFooter.getText().toString();
                                headerDesc = headerDesc.replace("<br>", " ");
                                EventBus.getDefault().post(new EventClickedForImage(bitmap));
                            } else {
                                Toast.makeText(MenuNavigationActivity.this, "Can not generate barcode", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mFooter.setError("Footer can't be empty!");
                        }
                    } else {
                        mHeader.setError("Header can't be empty!");
                    }
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                boolCheck = false;
            }
        });

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
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(true);
    }


    void validationCheck(int code_type, EditText input_data) {
        if (code_type == IBarcode.CODE39) {
            input_data.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
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
        }
    }

    String submitFun(int code_type, EditText input_data) {
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
        }
        return bool;
    }

    @Subscribe
    public void eventClickAlertForInfo(EventClickedForInfo response) {
        String res[] = response.getPosition().split("split");
        final String code_name = res[2];
        final String code_info = res[3];
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_info_dialog, null, false);
        final TextView codeName = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        final TextView infoData = (TextView) dialogForm.findViewById(R.id.info_data);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        codeName.setText(code_name);
        infoData.setText(code_info);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialogInfo = builder.create();
        dialogInfo.show();
        dialogInfo.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo.dismiss();
            }
        });

    }

    @Subscribe
    public void eventClickAlertForInfo(EventClickedForImage response) {
        Bitmap bitmap = response.getPosition();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_image_view_dialog, null, false);
        ImageView mImageView = (ImageView) dialogForm.findViewById(R.id.image_view);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        TextView buttonTabTxt = (TextView) dialogForm.findViewById(R.id.buttonTab);
        TextView codeNameTxt = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        TextView description = (TextView) dialogForm.findViewById(R.id.description);
        if (code_type == IBarcode.DATA_MATRIX) {
            mImageView.getLayoutParams().width = 580;
            mImageView.getLayoutParams().height = 500;
        }
        buttonTabTxt.setText(buttonTab);
        mImageView.setImageBitmap(bitmap);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        if (boolFOrEvent) {
            codeNameTxt.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else {
            codeNameTxt.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            description.setText(headerDesc);
            if (bitmap != null) {
                codeNameTxt.setText(footerDesc);
            } else {
                codeNameTxt.setText("Image is empty!");
            }
            dialogPreview = builder.create();
            dialogPreview.show();
            dialogPreview.setCanceledOnTouchOutside(false);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolFOrEvent) {
                    dialog.dismiss();
                    buttonTab = "";
                } else {
                    dialogPreview.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        }
        isExit = true;
        Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        }, 2000);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    Boolean fun(int code_type) {
        Boolean bool = false;
        if (code_type == IBarcode.CODE93) {
            bool = true;
        } else if (code_type == IBarcode.UPCE) {
            bool = true;
        } else {
            bool = false;
        }
        if (bool) {
            return true;
        } else {
            return false;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void add(View v) {
        startScan();
    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(MenuNavigationActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        System.out.println(barcode.rawValue);
                        Toast.makeText(MenuNavigationActivity.this, "Barcode Type  " + Helpers.barcodeScan(barcode.format, barcode.rawValue), Toast.LENGTH_SHORT).show();
                        // result.setText(barcode.rawValue);
                        String input = barcode.rawValue;
                        int code_type = barcode.format;
                      /*  Bitmap bitmap = null;
                        if (code_type==IBarcode.EAN13){
                             bitmap = Helpers.barcodeBitmap(code_type, input.substring(0,input.length()-1), 900, 700);
                        }else {
                             bitmap = Helpers.barcodeBitmap(code_type, input, 900, 700);
                        }
                        showScanBarcode(bitmap, code_type, input);*/
                      //  showScanBarcode(bitmap, code_type, input);
                        Intent intent = new Intent(getApplicationContext(),ScanedBarcodeActivity.class);
                        intent.putExtra("input",input);
                        intent.putExtra("code_type",""+code_type);
                        startActivity(intent);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    void showScanBarcode(Bitmap bitmap, int code_type, String code_value) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_image_view_dialog, null, false);
        ImageView mImageView = (ImageView) dialogForm.findViewById(R.id.image_view);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        TextView buttonTab = (TextView) dialogForm.findViewById(R.id.buttonTab);
        TextView codeNameTxt = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        TextView description = (TextView) dialogForm.findViewById(R.id.description);
        buttonTab.setText("Scaned Barcode");
        if (code_type == IBarcode.DATA_MATRIX) {
            mImageView.getLayoutParams().width = 900;
            mImageView.getLayoutParams().height = 500;
        }

        mImageView.setImageBitmap(bitmap);
        String arr[] = Helpers.barcodeScan(code_type,code_value).split("=");
        description.setText(arr[0]);
        codeNameTxt.setText(code_value);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        codeNameTxt.setVisibility(View.VISIBLE);
        if (bitmap != null) {
            if (code_value.startsWith("http")){
                String value = "<html><a href=\""+code_value+"\">"+code_value+"</a></html>";
                codeNameTxt.setText(Html.fromHtml(value));
                codeNameTxt.setMovementMethod(LinkMovementMethod.getInstance());
            }else {
                codeNameTxt.setText(code_value);
            }
        } else {
            description.setText("Image is empty!");
        }
        dialogPreview = builder.create();
        dialogPreview.show();
        dialogPreview.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPreview.dismiss();
            }
        });
    }
}
