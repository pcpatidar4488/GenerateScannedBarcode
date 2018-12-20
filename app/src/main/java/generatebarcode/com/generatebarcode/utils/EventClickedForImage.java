package generatebarcode.com.generatebarcode.utils;

import android.graphics.Bitmap;

public class EventClickedForImage {
    private final Bitmap position;

    public EventClickedForImage(Bitmap position) {
        this.position = position;
    }

    public Bitmap getPosition() {
        return position;
    }
}