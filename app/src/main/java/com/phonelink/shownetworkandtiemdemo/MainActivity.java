package com.phonelink.shownetworkandtiemdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NetworkChangeUtil.NetworkChangeListener {
    private Context context;
    private TextView tv01;
    private TextView tv02;
    private NetworkChangeUtil mNetworkChangeUtil;
    private static final String TAG = "Mianactivity";
    int[] gsm2gIcom = {R.mipmap.btn_2g_4, R.mipmap.btn_2g_3, R.mipmap.btn_2g_2, R.mipmap.btn_2g_1};
    int[] gsm3gIcom = {R.mipmap.btn_3g_4, R.mipmap.btn_3g_3, R.mipmap.btn_3g_2, R.mipmap.btn_3g_1};
    int[] gsm4gIcom = {R.mipmap.btn_4g_4, R.mipmap.btn_4g_3, R.mipmap.btn_4g_2, R.mipmap.btn_4g_1};
    int[] wifiIcom = {R.mipmap.ic_wifi4, R.mipmap.ic_wifi3, R.mipmap.ic_wifi2, R.mipmap.ic_wifi1};
    private ImageButton ib;

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
        ib = (ImageButton) findViewById(R.id.ib);
        ib.setImageResource(R.mipmap.ic_wifi5);


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
