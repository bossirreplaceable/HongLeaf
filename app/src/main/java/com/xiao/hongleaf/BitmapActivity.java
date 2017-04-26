package com.xiao.hongleaf;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yoBo on 2017/4/25.
 */

public class BitmapActivity  extends Activity {
    private ImageView image1;
    private ImageView image2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_activity);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image1.setImageResource(R.drawable.aaa);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.aaa);
        Bitmap bitmap2 = gray(bitmap,1);
        image2.setImageBitmap(bitmap2);

    }
    public Bitmap gray(Bitmap bitmap, int schema)
    {
        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), bitmap.getConfig());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int px=bitmap.getPixel(width-10,height-10);
        int px1=bitmap.getPixel(width-100,height-100);
        Log.e("Voss---------1",px+"");
        Log.e("Voss---------2",px1+"");
        int[] offset=new int[width*height];
        Log.e("ooo","skokoko");

        for(int row=0; row<height; row++){
            for(int col=0; col<width; col++){
                int pixel = bitmap.getPixel(col, row);// ARGB
                int red = Color.red(pixel); // same as (pixel >> 16) &0xff
                int green = Color.green(pixel); // same as (pixel >> 8) &0xff
                int blue = Color.blue(pixel); // same as (pixel & 0xff)
                int alpha = Color.alpha(pixel); // same as (pixel >>> 24)
                int gray = 0;
                if(schema == 0)
                {
                    gray = (Math.max(blue, Math.max(red, green)) +
                            Math.min(blue, Math.min(red, green))) / 2;
                }
                else if(schema == 1)
                {
                    gray = (red + green + blue) / 3;
                }
                else if(schema == 2)
                {
                    gray = (int)(0.3 * red + 0.59 * green + 0.11 * blue);
                }
                bm.setPixel(col, row, Color.argb(alpha, gray, gray, gray));
            }
        }
        return bm;
    }
}

