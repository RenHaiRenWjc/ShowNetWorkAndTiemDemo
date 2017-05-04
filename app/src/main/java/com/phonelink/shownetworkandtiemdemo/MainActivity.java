package com.phonelink.shownetworkandtiemdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NetworkStrengthChangeUtil.NetworkChangeListener {
    private Context context;
    private TextView tv01;
    private TextView tv02;
    private NetworkStrengthChangeUtil mNetworkStrengthChangeUtil;
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


        mNetworkStrengthChangeUtil = new NetworkStrengthChangeUtil(context);
        mNetworkStrengthChangeUtil.RegisterReciverNetworkChanges();
        mNetworkStrengthChangeUtil.setNetworkChangeListener(this);

        tv01 = (TextView) findViewById(R.id.tv01);
        tv02 = (TextView) findViewById(R.id.tv02);
        ib = (ImageButton) findViewById(R.id.ib);
        ib.setImageResource(R.mipmap.ic_wifi5);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkStrengthChangeUtil.receiver);
    }


    @Override
    public void notifyNetChange(int level, String type) {
        //wifi 返回来的等级是 1,2,3,4

        tv01.setText("类型：" + type);
        tv02.setText("格子：" + level);
        Log.i(TAG, "level=" + level);

        if (type == null) {
            return;
        }
        if (type.equalsIgnoreCase("wifi")) {
            ib.setImageResource(wifiIcom[level]);
        } else if (type.equalsIgnoreCase("2g")) {
            ib.setImageResource(gsm2gIcom[level]);
        } else if (type.equalsIgnoreCase("3g")) {
            ib.setImageResource(gsm3gIcom[level]);
        } else if (type.equalsIgnoreCase("4g")) {
            ib.setImageResource(gsm4gIcom[level]);
        } else {
            ib.setImageResource(0);
        }
    }


   /* public void changeNewIcom() {
        Log.i(TAG, "netype=" + (type);
        if (netType == null) {
            return;
        }
        if (netType.equalsIgnoreCase("wifi")) {
            Log.i(TAG, "netype2=" + netType);
            ib.setImageResource(wifiIcom[level]);
        } else if (netType.equalsIgnoreCase("2g")) {
            ib.setImageResource(gsm2gIcom[netLevel]);
        } else if (netType.equalsIgnoreCase("3g")) {
            ib.setImageResource(gsm3gIcom[netLevel]);
        } else if (netType.equalsIgnoreCase("4g")) {
            ib.setImageResource(gsm4gIcom[netLevel]);
        }
    }*/


}
