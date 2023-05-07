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
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

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

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(mColorText);
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.my_image_view);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSomething(v);
            }
        });
    }

    private void drawSomething(View v) {
        int vWidth = v.getWidth();
        int vHeight = v.getHeight();

        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if (mOffset == OFFSET){
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(mColorBackground);

            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        }else{
            if (mOffset < halfWidth && mOffset < halfHeight){
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                mRect.set(mOffset, mOffset, vWidth-mOffset, vHeight-mOffset);
                mCanvas.drawRect(mRect, mPaint);
                mOffset += OFFSET;
            }else{

                mPaint.setColor(mColorColorCircle - MULTIPLIER*mOffset);
                mCanvas.drawCircle(halfWidth,halfHeight,halfWidth/3,mPaint);

                String text = getString(R.string.done);
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
                mOffset += OFFSET;

                mPaint.setColor(mColorBackground - MULTIPLIER*mOffset);
                Point a = new Point(halfWidth-50, halfHeight-50);
                Point b = new Point(halfWidth+50, halfHeight-50);
                Point c = new Point(halfWidth, halfHeight+250);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.lineTo(a.x, a.y);
                path.lineTo(b.x, b.y);
                path.lineTo(c.x, c.y);
                path.lineTo(a.x, a.y);
                path.close();

                mCanvas.drawPath(path, mPaint);
                mOffset += OFFSET;

                // Define the start and end points of the gradient
                float startX = halfWidth/2;
                float startY = halfHeight;
                float endX = halfWidth+halfWidth/2;
                float endY = halfHeight;

                Shader gradient = new LinearGradient(startX, startY, endX, endY, circle_1_color, null, Shader.TileMode.CLAMP);
                mPaint.setShader(gradient);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth/2, mPaint);
            }
        }
        v.invalidate();
    }
}