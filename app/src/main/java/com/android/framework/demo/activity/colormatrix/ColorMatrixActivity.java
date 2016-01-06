package com.android.framework.demo.activity.colormatrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.framework.demo.R;

/**
 * Created by meikai on 16/1/6.
 */
public class ColorMatrixActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgvShader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);

        imgvShader = (ImageView) findViewById(R.id.imgv_shader);
    }

    private Bitmap renderByColorMatrix(ColorMatrix colorMatrix) {
        Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);

        Bitmap bitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
                sourceBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);

        return bitmap;
    }

    //灰色
    private Bitmap getGrayscaleBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        return renderByColorMatrix(colorMatrix);
    }
    //深褐色
    private Bitmap getSepiaBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrix colorScale = new ColorMatrix();
        colorScale.setScale(1, 1, 0.8f, 1);

        // Convert to grayscale, then apply brown color
        colorMatrix.postConcat(colorScale);

        return renderByColorMatrix(colorMatrix);
    }
    //黑白色
    private Bitmap getBinaryBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        float m = 255f;
        float t = -255*128f;
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });

        // Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);

        return renderByColorMatrix(colorMatrix);
    }
    //RGB反转
    private Bitmap getInvertBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[] {
                -1,  0,  0,  0, 255,
                0, -1,  0,  0, 255,
                0,  0, -1,  0, 255,
                0,  0,  0,  1,   0
        });

        return renderByColorMatrix(colorMatrix);
    }
    //
    private Bitmap getAlphaBlueBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[] {
                0,    0,    0, 0,   0,
                0.3f,    0,    0, 0,  50,
                0,    0,    0, 0, 255,
                0.2f, 0.4f, 0.4f, 0, -30
        });

        return renderByColorMatrix(colorMatrix);
    }
    //
    private Bitmap getAlphaPinkBitmap() {
        ColorMatrix colorMatrix = new ColorMatrix(new float[] {
                0,    0,    0, 0, 255,
                0,    0,    0, 0,   0,
                0.2f,    0,    0, 0,  50,
                0.2f, 0.2f, 0.2f, 0, -20
        });

        return renderByColorMatrix(colorMatrix);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_grayscale: {
                imgvShader.setImageBitmap(getGrayscaleBitmap());
            }
            break;
            case R.id.btn_sepia: {
                imgvShader.setImageBitmap(getSepiaBitmap());
            }
            break;
            case R.id.btn_binary: {
                imgvShader.setImageBitmap(getBinaryBitmap());
            }
            break;
            case R.id.btn_invert: {
                imgvShader.setImageBitmap(getInvertBitmap());
            }
            break;
            case R.id.btn_alpha_blue: {
                imgvShader.setImageBitmap(getAlphaBlueBitmap());
            }
            break;
            case R.id.btn_alpha_pink: {
                imgvShader.setImageBitmap(getAlphaPinkBitmap());
            }
            break;
        }
    }
}
