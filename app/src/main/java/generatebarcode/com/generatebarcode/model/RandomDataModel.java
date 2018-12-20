package generatebarcode.com.generatebarcode.model;

import android.graphics.Bitmap;

/**
 * Created by anupamchugh on 11/02/17.
 */

public class RandomDataModel {


    public String header;
    public String footer;
    public Bitmap bitmap;


    public RandomDataModel(String t, Bitmap d, String c)
    {
        footer = t;
        bitmap = d;
        header = c;
    }
}
