package com.ice.edupatrol.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private boolean mIsUpdate = false;

    public CanvasView(Context context) {
        super(context);
        initView();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        mCanvas = new Canvas();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if(null == mBitmap) {
            mCanvas.setBitmap(mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888));
        }
        if(mIsUpdate) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
            mIsUpdate = false;
        }
    }

    public Canvas getCanvas() {
        return mCanvas;
    }

    public void update() {
        mIsUpdate = true;
        this.invalidate();
    }
}