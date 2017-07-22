package com.ice.edupatrol.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.ice.edupatrol.R;
import com.ice.edupatrol.util.DensityUtil;

public class GestureLockView extends FrameLayout {
    private int mRowCount = 3;
    private int mColumnCount = 3;
    private float mMargin = 16;
    private Context mContext;
    private GridLayout mLayoutButton;
    private int mLastButtonIndex = -1;
    private CanvasView mLayoutLine;
    private CanvasView mLayoutTouch;
    private String mGestureCode = "";
    private Handler mCleanGestureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            cleanLayout(mLayoutLine);
            cleanButton();
        }
    };
    private GestureListener mGestureListener;

    public GestureLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    public GestureLockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    public void setGestureListener(GestureListener mGestureListener) {
        this.mGestureListener = mGestureListener;
    }

    private void initView(Context context) {
        mContext = context;
        addView(mLayoutButton = (GridLayout) getButtonLayout());
        addView(mLayoutLine = (CanvasView) getLineLayout());
        addView(mLayoutTouch = (CanvasView) getTouchLayout());
    }

    private View getButtonLayout() {
        GridLayout gridLayout = new GridLayout(mContext);
        gridLayout.setRowCount(mRowCount);
        gridLayout.setColumnCount(mColumnCount);
        int margin = DensityUtil.dip2px(mContext, mMargin);
        for (int y = 0, index = 0; y < mRowCount; ++y) {
            for (int x = 0; x < mColumnCount; ++x, ++index) {
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.setMargins(margin, margin, margin, margin);
                final ImageView imageView = new ImageView(mContext);
                setButtonImage(imageView, false);
                imageView.setLayoutParams(layoutParams);
                gridLayout.addView(imageView);
            }
        }
        return gridLayout;
    }

    private View getLineLayout() {
        CanvasView innerDrawView = new CanvasView(mContext);
        return innerDrawView;
    }

    private View getTouchLayout() {
        CanvasView innerDrawView = new CanvasView(mContext);
        innerDrawView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int index = -1;
                if ((0 <= event.getX() && event.getX() < getMeasuredWidth()) && (0 <= event.getY() && event.getY() < getMeasuredHeight())) {
                    int xIndex = (int) (event.getX() * mColumnCount / getMeasuredWidth());
                    int yIndex = (int) (event.getY() * mRowCount / getMeasuredHeight());
                    View buttonView = mLayoutButton.getChildAt(index = xIndex + yIndex * mRowCount);
                    int tX = (int) (event.getX() - buttonView.getMeasuredWidth() / 2 - buttonView.getX());
                    int tY = (int) (event.getY() - buttonView.getMeasuredHeight() / 2 - buttonView.getY());
                    if ((tX * tX + tY * tY) * 4 > buttonView.getMeasuredWidth() * buttonView.getMeasuredHeight()) {
                        index = -1;
                    }
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cleanLayout(mLayoutLine);
                        cleanLayout(mLayoutTouch);
                        cleanButton();
                        mCleanGestureHandler.removeMessages(0);
                    case MotionEvent.ACTION_MOVE:
                        if (index != -1) selectButtonByIndex(index);
                        if (mLastButtonIndex != -1) {
                            View prevView = mLayoutButton.getChildAt(mLastButtonIndex);
                            cleanLayout(mLayoutTouch);
                            drawLine(mLayoutTouch, prevView.getX() + prevView.getMeasuredWidth() / 2, prevView.getY() + prevView.getMeasuredHeight() / 2,
                                    event.getX(), event.getY());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        finishGesture();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        return innerDrawView;
    }

    private void setButtonImage(ImageView imageView, boolean isSelected) {
        imageView.setImageResource(isSelected ? R.drawable.gesture_lock_point_selected : R.drawable.gesture_lock_point_unselected);
    }

    private void finishGesture() {
        mLastButtonIndex = -1;
        cleanLayout(mLayoutTouch);
        cleanLayout(mLayoutLine);
        if (mGestureListener != null && !mGestureListener.getGesture(mGestureCode)) {
            drawErrorLine();
            mCleanGestureHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            cleanButton();
        }
        mGestureCode = "";
    }

    private void drawErrorLine() {
        int lastIndex = -1;
        for (int i = 0; i < mGestureCode.length(); ++i) {
            int index = mGestureCode.charAt(i) - '0';
            if (lastIndex != -1 && 0 <= index && index < mLayoutButton.getChildCount()) {
                View prevView = mLayoutButton.getChildAt(lastIndex);
                View buttonView = mLayoutButton.getChildAt(index);
                drawErrorLine(mLayoutLine, prevView.getX() + prevView.getMeasuredWidth() / 2, prevView.getY() + prevView.getMeasuredHeight() / 2,
                        buttonView.getX() + buttonView.getMeasuredWidth() / 2, buttonView.getY() + buttonView.getMeasuredHeight() / 2);
            }
            lastIndex = index;
        }
    }

    private void selectButtonByIndex(int index) {
        View buttonView = mLayoutButton.getChildAt(index);
        if (null == buttonView.getTag()) {
            buttonView.setTag(true);
            setButtonImage((ImageView) buttonView, true);
            if (mLastButtonIndex != -1) {
                View prevView = mLayoutButton.getChildAt(mLastButtonIndex);
                drawLine(mLayoutLine, prevView.getX() + prevView.getMeasuredWidth() / 2, prevView.getY() + prevView.getMeasuredHeight() / 2,
                        buttonView.getX() + buttonView.getMeasuredWidth() / 2, buttonView.getY() + buttonView.getMeasuredHeight() / 2);
            }
            mGestureCode += (mLastButtonIndex = index);
        }
    }

    private void drawLine(CanvasView innerDrawView, float x1, float y1, float x2, float y2) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.gesture_lock_selected_dark));
        paint.setStrokeWidth(5);
        innerDrawView.getCanvas().drawLine(x1, y1, x2, y2, paint);
        innerDrawView.update();
    }

    private void drawErrorLine(CanvasView innerDrawView, float x1, float y1, float x2, float y2) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        innerDrawView.getCanvas().drawLine(x1, y1, x2, y2, paint);
        innerDrawView.update();
    }

    private void cleanButton() {
        for (int i = 0; i < mLayoutButton.getChildCount(); ++i) {
            View buttonView = mLayoutButton.getChildAt(i);
            if (null != buttonView.getTag()) {
                buttonView.setTag(null);
                setButtonImage((ImageView) buttonView, false);
            }
        }
    }

    private void cleanLayout(CanvasView innerDrawView) {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        innerDrawView.getCanvas().drawPaint(paint);
        innerDrawView.update();
    }

    public interface GestureListener {
        boolean getGesture(String gestureCode);
    }
}
