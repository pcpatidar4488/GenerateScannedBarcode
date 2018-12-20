package generatebarcode.com.generatebarcode.activity.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.activity.WebViewHtmlActivity;
import generatebarcode.com.generatebarcode.adapter.RandomGrideViewAdapter;
import generatebarcode.com.generatebarcode.model.RandomDataModel;
import generatebarcode.com.generatebarcode.utils.Helpers;

public class RandomGenerateActivity extends AppCompatActivity implements RandomGrideViewAdapter.ItemListener {
    RecyclerView recyclerView;
    public static ArrayList<RandomDataModel> arrayList;
    String input_data, header, footer, code_name;
    int code_type,quantity;
    private AlertDialog dialog;
    private Bitmap bitmap = null;
    RandomGrideViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Intent intent = getIntent();
        input_data = intent.getStringExtra("input_data");
        header = intent.getStringExtra("header");
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        quantity = Integer.parseInt(intent.getStringExtra("quantity"));
        footer = intent.getStringExtra("footer");
        code_name = intent.getStringExtra("code_name");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initActionbar(code_name);
        // arrayList = new ArrayList<>();
        // arrayList.add(new RandomDataModel(header, 1, code_type));
        adapter = new RandomGrideViewAdapter(RandomGenerateActivity.this, generateBarcode(input_data, header, footer, quantity, code_type), this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

       /* AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(RandomDataModel item) {

        // Toast.makeText(getApplicationContext(), item.header + " is clicked", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<RandomDataModel> generateBarcode(String input_data, String header, String footer, int quantity, int code_type) {
        arrayList = new ArrayList<>();
        if (input_data.equals("")) {
            for (int i = 0; i < quantity; i++) {
                String random = Helpers.barcodeRandomString(code_type);
                arrayList.add(new RandomDataModel(footer, Helpers.barcodeBitmap(code_type, random, 500, 250), header));
               /* if (code_type == IBarcode.UPCA) {
                    String random = Helpers.barcodeRandomString(code_type);
                    String input = Helpers.getUPCACheckSumString(random);
                    arrayList.add(new RandomDataModel(input, Helpers.barcodeBitmap(code_type, random, 500, 250), header));
                } else {
                    String random = Helpers.barcodeRandomString(code_type);
                    arrayList.add(new RandomDataModel(footer, Helpers.barcodeBitmap(code_type, random, 500, 250), header));
                }*/

            }
          //  MenuNavigationActivity.mProgressDialog.dismiss();
        } else {
            bitmap = Helpers.barcodeBitmap(code_type, input_data, 500, 250);
            arrayList.add(new RandomDataModel(footer, bitmap, header));
        }
        return arrayList;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.icon_id:
                fun();
                Intent intent = new Intent(RandomGenerateActivity.this,WebViewHtmlActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  if (!input_data.equals("")) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.plus_button_action, menu);
       // }
        return super.onCreateOptionsMenu(menu);
    }

    public void fun() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogForm = inflater.inflate(R.layout.add_barcode_dailog, null, false);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        TextView input = (TextView) dialogForm.findViewById(R.id.input_data);
        TextView codeName = (TextView) dialogForm.findViewById(R.id.code_name);
        final EditText mHeader = (EditText) dialogForm.findViewById(R.id.header);
        codeName.setText(code_name);
        input.setText(input_data);

        Button buttonSubmit = (Button) dialogForm.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHeader.getText().toString().equals("")) {
                        arrayList.add(new RandomDataModel(input_data, bitmap, mHeader.getText().toString()));
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                } else {
                    mHeader.setError("Description can't be empty!");
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
}
