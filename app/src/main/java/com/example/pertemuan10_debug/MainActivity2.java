package com.example.pertemuan10_debug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {
    private Canvas mCanvas;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Bitmap mBitmap;
    private ImageView mImageView;

    private Rect mRect = new Rect();
    private Rect mBounds = new Rect();
    private static final int OFFSET = 120;
    private int mOffset = OFFSET;
    private static final int MULTIPLIER = 100;

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorColorCircle;
    private int mColorText;
    private int[] circle_1_color;
    private int[] circle_2_color;
    private int[] circle_3_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Basic Colors
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorColorCircle = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        mColorText = ResourcesCompat.getColor(getResources(), R.color.black, null);

        // Gradient Circle Colors
        circle_1_color = new int[]{ResourcesCompat.getColor(getResources(), R.color.circle_1_start, null), ResourcesCompat.getColor(getResources(), R.color.circle_1_end, null)};
        circle_2_color = new int[]{ResourcesCompat.getColor(getResources(), R.color.circle_2_start, null), ResourcesCompat.getColor(getResources(), R.color.circle_2_end, null)};
        circle_3_color = new int[]{ResourcesCompat.getColor(getResources(), R.color.circle_3_start, null), ResourcesCompat.getColor(getResources(), R.color.circle_3_end, null)};

        mImageView = findViewById(R.id.my_image_view);
        mPaint = new Paint();
        mImageView = findViewById(R.id.my_image_view);

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                drawSomething(mImageView);
            }
        });
    }

    private void drawSomething(ImageView mImageView) {
        int width = this.mImageView.getWidth();
        int height = this.mImageView.getHeight();
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        int vWidth = this.mImageView.getWidth();
        int vHeight = this.mImageView.getHeight();

        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        //Sinewave Background
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(0, height / 2f);

        float sineHeight = 12f;
        float frequency = 2f;
        float x, y;

        for (int j = 0; j < height; j += 100) {
            path.moveTo(0, j);

            for (int i = 0; i < width; i += 10) {
                x = i;
                y = (float) (j + height / (2 * sineHeight) * Math.sin(2 * Math.PI * frequency * i / (float) width));
                path.lineTo(x, y);
            }

            path.lineTo(width, j);
        }

        mCanvas.drawPath(path, mPaint);

        //reset mPaint to FILL style
        mPaint.setStyle(Paint.Style.FILL);

        // Center Circle
        float startX = halfWidth/2;
        float startY = halfHeight;
        float endX = halfWidth+halfWidth/2;
        float endY = halfHeight;

        Shader gradient = new LinearGradient(startX, startY, endX, endY, circle_1_color, null, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/2, mPaint);

        //Upper Left Circle
        startX = 0;
        startY = 0;
        endX = halfWidth;
        endY = 0;

        gradient = new LinearGradient(startX, startY, endX, endY, circle_2_color, null, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mCanvas.drawCircle(0, 0, halfWidth/2+100, mPaint);

        //Bottom Right Circle
        startX = width;
        startY = 0;
        endX = halfWidth;
        endY = 0;

        gradient = new LinearGradient(startX, startY, endX, endY, circle_3_color, null, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mCanvas.drawCircle(width, height, halfWidth/2+100, mPaint);

        this.mImageView.setImageBitmap(mBitmap);
    }
}