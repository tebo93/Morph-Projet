package com.tunaprojects.morph.Controller.AsyncCalls;

/**
 * Created by Esteban Puello on 28/11/2016.
 * This class allows user to load an image from internet into a ImageView object
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.widget.ImageView;
import com.tunaprojects.morph.Controller.Utility.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView iv;
    private Context c;
    private MenuItem mi;

    /**
     * Using this constructor the image will be loaded on the image view
     * @param iv ImageView instance
     */
    public LoadImageTask(ImageView iv) {
        this.iv = iv;
    }

    /**
     * Using this constructor the image will be loaded on a menuitem
     * @param c Activity in which the class is used
     * @param mi MenuItem in which the image will be loaded
     */
    public LoadImageTask(Context c, MenuItem mi) {
        this.c = c;
        this.mi = mi;
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        try {
            InputStream is = (InputStream) new URL(args[0]).getContent();
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (iv != null) {
                Bitmap newBitmap = Utils.getFinalImage(bitmap, 700);
                iv.setImageBitmap(newBitmap);
            }
            if (c != null) {
                Bitmap newBitmap = Utils.getFinalImage(bitmap, 300);
                Drawable d = new BitmapDrawable(c.getResources(), newBitmap);
                if (mi != null) {
                    mi.setIcon(d);
                }
            }
        } else {
            //ajouter d'une image dans le cas où il n'y a pas d'image à charger....
        }
    }
}
