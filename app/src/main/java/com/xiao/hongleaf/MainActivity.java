package com.xiao.hongleaf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private Uri uri_image;
    private Button bt_camera;
    private ImageView_Draw iv_image;
    private Button bt_calculate;
    private Button bt_bjd;
    private Button bt_sbw;
    private TextView tv_area;
    private int[][] imag;
    private int iLeaf = 0, irect = 0;
    private Bitmap bm;
    private float startX = 0, startY = 0;
    private int img_TiQu[][];
    private TextView tv_leaf_value;
    private TextView tv_rect_value;
    int imageX = 0, imageY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_camera = (Button) findViewById(R.id.main_choose);
        bt_camera.setOnClickListener(this);
        iv_image = (ImageView_Draw) findViewById(R.id.main_image);
        iv_image.setOnTouchListener(this);
        bt_calculate = (Button) findViewById(R.id.main_calculate);
        bt_calculate.setOnClickListener(this);
        bt_bjd = (Button) findViewById(R.id.main_bjd);
        bt_sbw = (Button) findViewById(R.id.main_sbw);
        bt_bjd.setOnClickListener(this);
        bt_sbw.setOnClickListener(this);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_rect_value = (TextView) findViewById(R.id.tv_rect_value);
        tv_leaf_value = (TextView) findViewById(R.id.tv_leaf_value);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.main_choose:
                takePhotoForImage();
                break;
            case R.id.main_calculate:
                showArea();
                break;
            case R.id.main_bjd:
                if (irect == 0) {
                    Toast.makeText(this, "您还没有确定标记点", Toast.LENGTH_SHORT).show();
                } else {
                    bt_sbw.setEnabled(true);
                    bt_sbw.setBackgroundResource(R.drawable.circle_shenlv);
                    bt_bjd.setEnabled(false);
                    bt_bjd.setBackgroundResource(R.drawable.circle_danlv);
                }
                break;
            case R.id.main_sbw:
                if (iLeaf == 0) {
                    Toast.makeText(this, "您还没有确定识别物", Toast.LENGTH_SHORT).show();
                } else {
                    bt_sbw.setEnabled(false);
                    bt_sbw.setBackgroundResource(R.drawable.circle_danlv);
                    bt_calculate.setEnabled(true);
                    bt_calculate.setBackgroundResource(R.drawable.circle_shenlv);
                }
                break;
        }
    }

    private void showArea() {

        //  tv_area.setText((int)Math.random()*10+"");
        double dbArea = (iLeaf * 1.0) / (irect * 1.0);
        tv_area.setText(dbArea + "");
        imag = null;//读入图像数组
        img_TiQu = null;//提取出来的数组(选区内的)
        iLeaf = 0;
        irect = 0;
        startY = 0;
        startX = 0;
        bt_calculate.setEnabled(false);
        bt_calculate.setBackgroundResource(R.drawable.circle_danlv);
        bt_camera.setEnabled(true);
        bt_camera.setBackgroundResource(R.drawable.circle_shenlv);
    }

    private void takePhotoForImage() {

        String path = Environment.getExternalStorageDirectory().toString() + "/xiaohong";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, System.currentTimeMillis() + ".jpeg");
        uri_image = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_image);
        startActivityForResult(intent, 1);
        tv_leaf_value.setText("0");
        tv_rect_value.setText("0");
        tv_area.setText("0");

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {


            } else {
                Log.e("image--------uri--", uri_image + "");
                Bitmap bitmap = null;
                try {
                    bitmap = ImageToArray.getBitmapFormUri(this, uri_image);
                    iv_image.setImageBitmap(bitmap);
                    int[] xy = new int[2];
                    iv_image.getLocationInWindow(xy);
                    Log.e("image--------x---5", xy[0] + "");
                    Log.e("image--------y---5", xy[1] + "");
                    imageX = xy[0];
                    imageY = xy[1];

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.aaa);
//                iv_image.setImageBitmap(bitmap1);
                if (bitmap!=null) {
                    imag = ImageToArray.convertImageToArray(bitmap);
                    Log.e("xxxooo-------", imag.length + "");
                    bt_bjd.setEnabled(true);
                    bt_bjd.setBackgroundResource(R.drawable.circle_shenlv);
                    bt_camera.setBackgroundResource(R.drawable.circle_danlv);
                    bt_camera.setEnabled(false);
                    Log.e("获取到了URI-------", "uri");
                }
            }
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.e("Activity------------", "x-" + startX);
                Log.e("Activity-------------", "y-" + startY);
                break;
            case MotionEvent.ACTION_UP:
                float X = event.getX();
                float Y = event.getY();
                Log.e("Activity2--------------", "x-" + X);
                Log.e("Activity2-------------", "y-" + Y);
                int picWidth = Math.abs((int) (X - startX));
                int picHeight = Math.abs((int) (Y - startY));
                Log.e("A3--------------", "width-" + picWidth);
                Log.e("A3-------------", "height-" + picHeight);
                img_TiQu = new int[picHeight][picWidth];

                if (bt_bjd.isEnabled() && picWidth != 0 && picHeight != 0) {

                    for (int i = 0; i < picHeight; i++) {
                        for (int j = 0; j < picWidth; j++) {
                            img_TiQu[i][j] = imag[(int) startY + i][(int) startX + j];
                        }
                    }
                    //-------------------------------------
                    c_OtsuYuZhi Pro_fig = new c_OtsuYuZhi();//otsu 阈值分割
                    int T = 0;//阈值
                    T = Pro_fig.otsuThresh(img_TiQu, picWidth, picHeight);
                    int pic[][] = new int[picHeight][picWidth];
                    pic = Pro_fig.thSegment(img_TiQu, picWidth, picHeight, T);
                    c_GetArea getArea = new c_GetArea();
                    irect = getArea.CountPix(pic, picWidth, picHeight);
                    Log.e("A3-------------", "1次阈值-" + irect);
                    Log.e("A3-------------", "矩形的像素点" + irect);
                    tv_rect_value.setText("" + irect);
                    startX = 0;
                }
                if (startX == 0) {
                    Toast.makeText(this, "您还没有确定识别物", Toast.LENGTH_SHORT).show();
                } else {

                    if (bt_sbw.isEnabled() && picWidth != 0 && picHeight != 0) {
                        img_TiQu = new int[picHeight][picWidth];
                        for (int i = 0; i < picHeight; i++) {
                            for (int j = 0; j < picWidth; j++) {
                                img_TiQu[i][j] = imag[(int) startY + i][(int) startX + j];
                            }
                        }
                        int T = 0;
                        c_OtsuYuZhi Pro_fig = new c_OtsuYuZhi();//otsu 阈值分割
                        T = Pro_fig.otsuThresh(img_TiQu, picWidth, picHeight);
                        int pic[][] = new int[picHeight][picWidth];
                        pic = Pro_fig.thSegment(img_TiQu, picWidth, picHeight, T);
                        Log.e("A3-------------", "2次阈值-" + irect);
                        c_GetArea getArea = new c_GetArea();
                        iLeaf = getArea.CountPix(pic, picWidth, picHeight);
                        Log.e("A3-------------", "叶子的像素点：" + iLeaf);
                        tv_leaf_value.setText(iLeaf + "");
                    }
                }

                break;
        }
        return false;
    }
}
