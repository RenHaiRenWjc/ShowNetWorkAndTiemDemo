package com.phonelink.shownetworkandtiemdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * ClassName:com.phonelink.shownetworkandtiemdemo
 * Description:
 * author:wjc on 2017/5/3 17:45
 */

public class NetworkChangeUtil {
    public Context context;
    public NetworkReceiver receiver;

    public NetworkChangeUtil(Context context) {
        this.context = context;
        TelephonyManager telManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(myLis, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }


    // 注册网络变化广播
    public void RegisterReciverNetworkChanges() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        context.registerReceiver(receiver, filter);
    }

    //接收网络变化广播
    public class NetworkReceiver extends BroadcastReceiver {
        private ConnectivityManager mConnectivityManager;

        @Override
        public void onReceive(Context context, Intent intent) {
            String flag = intent.getAction();
            if (flag.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                netWorkChange(context);
            }
        }

        //获取当前网络信息
        private void netWorkChange(Context context) {
            mConnectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                getNetworkType(info);
            } else {
                netChangeListener.notifyNetTypeChange("网络没连接");
                netChangeListener.notifyNetLevelChange(404);
            }
        }
    }

    private void getNetworkType(NetworkInfo info) {
        String netName = info.getTypeName();
        if (netName.equalsIgnoreCase("WIFI")) {
            getWifiLevel();
            netChangeListener.notifyNetLevelChange(getWifiLevel());
            netChangeListener.notifyNetTypeChange("WIFI");
        } else if (netName.equalsIgnoreCase("MOBILE")) {
            int subType = info.getSubtype();
            netChangeListener.notifyNetTypeChange(getNetworkMobileType(subType));
        }
    }


    //wifi信号强度
    private int getWifiLevel() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getBSSID() != null) {
            int signalLevel = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
            return signalLevel;
        }
        return -1;
    }

    //获取网络信号
    private String getNetworkMobileType(int mobileType) {
        String mobileName = "";
        switch (mobileType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:

            case TelephonyManager.NETWORK_TYPE_EDGE:

            case TelephonyManager.NETWORK_TYPE_CDMA:

            case TelephonyManager.NETWORK_TYPE_1xRTT:

            case TelephonyManager.NETWORK_TYPE_IDEN:
                mobileName = "2g";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:

            case TelephonyManager.NETWORK_TYPE_EVDO_0:

            case TelephonyManager.NETWORK_TYPE_EVDO_A:

            case TelephonyManager.NETWORK_TYPE_HSDPA:

            case TelephonyManager.NETWORK_TYPE_HSUPA:

            case TelephonyManager.NETWORK_TYPE_HSPA:

            case TelephonyManager.NETWORK_TYPE_EVDO_B:

            case TelephonyManager.NETWORK_TYPE_EHRPD:

            case TelephonyManager.NETWORK_TYPE_HSPAP:

                mobileName = "3g";
                break;

            case TelephonyManager.NETWORK_TYPE_LTE:
                mobileName = "4g";
                break;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                mobileName = "no";
                break;
        }
        Log.i(TAG, "getNetworkMobileType:" + mobileName);
        return mobileName;
    }

    private static final String TAG = "NetworkChangeUtil";

    PhoneStateListener myLis = new PhoneStateListener() {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            Log.i(TAG, "myLis");
            if (!signalStrength.isGsm()) {
                return;
            }
            //  Get the GSM Signal Strength, valid values are (0-31, 99) as defined in TS
            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
            int signalLevel;
            if (gsmSignalStrength < 2 || gsmSignalStrength == 99) {//-109
                signalLevel = 0;
            } else if (gsmSignalStrength >= 15) {//-83
                signalLevel = 4;
            } else if (gsmSignalStrength >= 9) {//-95
                signalLevel = 3;
            } else if (gsmSignalStrength >= 6) {//-101
                signalLevel = 2;
            } else {//2---5
                signalLevel = 1;
            }
            Log.i(TAG, "onSignalStrengthsChanged=" + signalLevel + ",gsm=" + gsmSignalStrength);
            netChangeListener.notifyNetLevelChange(signalLevel);
        }
    };


    NetworkChangeListener netChangeListener;

    public void setNetworkChangeListener(NetworkChangeListener listener) {
        netChangeListener = listener;
    }

    public interface NetworkChangeListener {
        void notifyNetLevelChange(int level);

        void notifyNetTypeChange(String type);
    }


}
