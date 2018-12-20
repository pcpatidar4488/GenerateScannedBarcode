package generatebarcode.com.generatebarcode.activity.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.adapter.BarcodeListAdapter;
import generatebarcode.com.generatebarcode.utils.EventClicked;
import generatebarcode.com.generatebarcode.utils.Helpers;
import generatebarcode.com.generatebarcode.utils.IBarcode;

public class BarcodeListActivity extends AppCompatActivity {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BarcodeListAdapter mAdapter;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_list);
        initActionbar();
        ButterKnife.bind(this);
        List<Map> list = new ArrayList<>();
        list = Helpers.barcodeList();

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BarcodeListAdapter(getApplicationContext(), list);
        mRecyclerView.setAdapter(mAdapter);
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
        actionbarTitle.setText("Barcode Type");
       // actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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
        final int code_name = Integer.parseInt(res[0]);
        final String code_des = res[1];
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_dialog, null, false);
        final LinearLayout main_layout = (LinearLayout) dialogForm.findViewById(R.id.main_layout);
        Button random_generate = (Button) dialogForm.findViewById(R.id.random_generate);
        Button self_generate = (Button) dialogForm.findViewById(R.id.self_generate);
        final EditText input_data = (EditText) dialogForm.findViewById(R.id.input_data);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        input_data.setHint(code_des);
        validationCheck(code_name,input_data);
        random_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_data.setVisibility(View.GONE);
            }
        });
        self_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_data.setVisibility(View.VISIBLE);
            }
        });

        Button buttonSubmit = (Button) dialogForm.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input_data.getText().toString().equals("")){
                    Boolean bool = submitFun(code_name,input_data);
                    if (bool){
                        Intent intent = new Intent(BarcodeListActivity.this,AndroidBarcodeActivity.class);
                        intent.putExtra("footer",input_data.getText().toString());
                        intent.putExtra("code_type",""+code_name);
                        startActivity(intent);
                    }
                   // Toast.makeText(BarcodeListActivity.this,""+code_name, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BarcodeListActivity.this,"Input data can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialog = builder.create();
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    void validationCheck(int code_name,EditText input_data){
        if (code_name == IBarcode.CODE128){
           // footer.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }else if (code_name == IBarcode.CODE39){
          if (input_data.getText().toString().contains("_")){
              Toast.makeText(this, "No special character!", Toast.LENGTH_SHORT).show();
          }
        }else if (code_name == IBarcode.CODE93){

        }else if (code_name == IBarcode.EAN13){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        }else if (code_name == IBarcode.EAN8){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[] { new InputFilter.LengthFilter(7) });
        }else if (code_name == IBarcode.CODABAR){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if (code_name == IBarcode.UPCE){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
        }else if (code_name == IBarcode.UPCA){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
        }else if (code_name == IBarcode.ITF){
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
            input_data.setFilters(new InputFilter[] { new InputFilter.LengthFilter(13) });
        }
    }

    Boolean submitFun(int code_name,EditText input_data){
        Boolean bool = false;
        if (code_name == IBarcode.CODE128){
            bool = true;
        }else if (code_name == IBarcode.CODE39){
            bool = true;
        }else if (code_name == IBarcode.CODE93){
            bool = true;
        }else if (code_name == IBarcode.EAN13){
            if (input_data.getText().toString().length()==12){
                bool = true;
            }else {
                Toast.makeText(this, "Input Data is 12 digit!", Toast.LENGTH_SHORT).show();
            }
        }else if (code_name == IBarcode.EAN8){
            if (input_data.getText().toString().length()==7){
                bool = true;
            }else {
                Toast.makeText(this, "Input Data is 7 digit!", Toast.LENGTH_SHORT).show();
            }
        }else if (code_name == IBarcode.CODABAR){
          bool = true;
        }else if (code_name == IBarcode.UPCE){
            if (input_data.getText().toString().length()==6){
                bool = true;
            }else {
                Toast.makeText(this, "Input Data is 6 digit!", Toast.LENGTH_SHORT).show();
            }
        }else if (code_name == IBarcode.UPCA){
            if (input_data.getText().toString().length()==11){
                bool = true;
            }else {
                Toast.makeText(this, "Input Data is 11 digit!", Toast.LENGTH_SHORT).show();
            }
        }else if (code_name == IBarcode.ITF){
            if (input_data.getText().toString().length()==13){
                bool = true;
            }else {
                Toast.makeText(this, "Input Data is 13 digit!", Toast.LENGTH_SHORT).show();
            }
        }

        return bool;
    }
}
