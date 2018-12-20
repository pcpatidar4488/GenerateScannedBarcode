package generatebarcode.com.generatebarcode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.model.RandomDataModel;


public class RandomGrideViewAdapter extends RecyclerView.Adapter<RandomGrideViewAdapter.ViewHolder> {

    ArrayList<RandomDataModel> mValues;
    Context mContext;
    protected ItemListener mListener;

    public RandomGrideViewAdapter(Context context, ArrayList<RandomDataModel> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;
        public TextView textView1;
        RandomDataModel item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            textView1 = (TextView) v.findViewById(R.id.description);

        }

        public void setData(RandomDataModel item) {
            this.item = item;

            textView.setText(item.footer);
            imageView.setImageBitmap(item.bitmap);
            textView1.setText(item.header);

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RandomGrideViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.gride_view_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(RandomDataModel item);
    }
}