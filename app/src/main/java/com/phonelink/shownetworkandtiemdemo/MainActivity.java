package com.phonelink.shownetworkandtiemdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import static android.telephony.PhoneStateListener.LISTEN_SIGNAL_STRENGTHS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN;


public class MainActivity extends AppCompatActivity {
    private Context context;
    private NetworkReceiver receiver;
    private TextView tv01;
    private TextView tv02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();


        tv01 = (TextView) findViewById(R.id.tv01);
        tv02 = (TextView) findViewById(R.id.tv02);


    }

    private void notifyTextChange(String msg) {
        tv01.setText(msg);
    }


    //接收网络变化广播
    public class NetworkReceiver extends BroadcastReceiver {
        private ConnectivityManager mConnectivityManager;
        private static final String TAG = "NetworkReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String flag = intent.getAction();
            if (flag.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                netWorkChange(context);
            }
        }

        private void netWorkChange(Context context) {
            //获取当前网络信息
            mConnectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                getNetworkType(info);
            } else {
                notifyTextChange("没有连接网络");
            }
        }
    }


    private void getNetworkType(NetworkInfo info) {
        String netName = info.getTypeName();
        tv01.setText(netName);
        if (netName.equalsIgnoreCase("WIFI")) {
            getWifiLevel();
            notifyTextChange("wifi强弱：" + getWifiLevel());
        } else if (netName.equalsIgnoreCase("MOBILE")) {
            int subType = info.getSubtype();
            notifyTextChange("网络类型：" + getNetworkMobileType(subType));
        }
    }

    //wifi信号强度
    private int getWifiLevel() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getBSSID() != null) {
            int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 4);
            return signalLevel;
        }
        return -1;
    }

    //获取网络信号
    private String getNetworkMobileType(int mobileType) {
        String mobileName = "";
        switch (mobileType) {
            case NETWORK_TYPE_GPRS:

            case NETWORK_TYPE_EDGE:

            case NETWORK_TYPE_CDMA:

            case NETWORK_TYPE_1xRTT:

            case NETWORK_TYPE_IDEN:
                mobileName = "2g";
                break;
            case NETWORK_TYPE_UMTS:

            case NETWORK_TYPE_EVDO_0:

            case NETWORK_TYPE_EVDO_A:

            case NETWORK_TYPE_HSDPA:

            case NETWORK_TYPE_HSUPA:

            case NETWORK_TYPE_HSPA:

            case NETWORK_TYPE_EVDO_B:

            case NETWORK_TYPE_EHRPD:

            case NETWORK_TYPE_HSPAP:

                mobileName = "3g";
                break;

            case NETWORK_TYPE_LTE:
                mobileName = "4g";
                break;
            case NETWORK_TYPE_UNKNOWN:
                mobileName = "no";
                break;
        }
        return mobileName;
    }


    // 注册网络变化广播
    private void RegisterReciverNetworkChanges() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        context.registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegisterReciverNetworkChanges();
        TelephonyManager telManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneGsmStatuListener myListener = new MyPhoneGsmStatuListener();
        telManager.listen(myListener, LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    //手机 GSM 信号强弱
    private class MyPhoneGsmStatuListener extends PhoneStateListener {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            // Get the GSM Signal Strength, valid values are (0-31, 99)
            if (!signalStrength.isGsm()) {
                return;
            }
            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
            String signalText = "";
            if (gsmSignalStrength < 2 || gsmSignalStrength == 99) {
                signalText = "no";
            } else if (gsmSignalStrength >= 15) {
                signalText = "gread";
            } else if (gsmSignalStrength >= 9) {
                signalText = "good";
            } else if (gsmSignalStrength >= 6) {
                signalText = "so so";
            } else {
                signalText = "so bad";
            }
            tv02.append(signalText);
        }

    }


}
