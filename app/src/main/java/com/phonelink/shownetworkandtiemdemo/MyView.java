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
public class MyView extends View implements NetworkChangeUtil.NetworkChangeListener {


    private Paint paint;
    private NetworkChangeUtil mNetworkChangeUtil;

    public MyView(Context context) {
        super(context);
        mNetworkChangeUtil = new NetworkChangeUtil(context);
        mNetworkChangeUtil.RegisterReciverNetworkChanges();
        mNetworkChangeUtil.setNetworkChangeListener(this);

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


    @Override
    public void notifyNetChange(int level, String msg) {

    }


}
