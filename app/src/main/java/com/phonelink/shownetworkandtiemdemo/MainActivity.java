package com.phonelink.shownetworkandtiemdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NetworkChangeUtil.NetworkChangeListener {
    private Context context;
    private TextView tv01;
    private TextView tv02;
    private NetworkChangeUtil mNetworkChangeUtil;
    private static final String TAG = "NetworkChangeUtil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        mNetworkChangeUtil = new NetworkChangeUtil(context);
        mNetworkChangeUtil.RegisterReciverNetworkChanges();
        mNetworkChangeUtil.setNetworkChangeListener(this);

        tv01 = (TextView) findViewById(R.id.tv01);
        tv02 = (TextView) findViewById(R.id.tv02);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkChangeUtil.receiver);
    }


    @Override
    public void notifyNetLevelChange(int level) {
        tv02.setText("格子：" + level);
    }

    @Override
    public void notifyNetTypeChange(String type) {
        tv01.setText(type);
        Log.i(TAG, "type=" + type);
    }

}
