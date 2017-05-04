package com.phonelink.shownetworkandtiemdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View {


    private Paint paint;
    private NetworkStrengthChangeUtil mNetworkStrengthChangeUtil;

    public MyView(Context context) {
        super(context);
        mNetworkStrengthChangeUtil = new NetworkStrengthChangeUtil(context);
        mNetworkStrengthChangeUtil.RegisterReciverNetworkChanges();
     //   mNetworkStrengthChangeUtil.setNetworkChangeListener(this);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.RED);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect r = new Rect(100, 100, 200, 200);
        canvas.drawRect(r, paint);

    }





}
