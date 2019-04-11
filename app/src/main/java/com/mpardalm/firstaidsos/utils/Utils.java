package com.mpardalm.firstaidsos.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    private static Utils utils;
    private static Context myContext;

    private Utils(Context context){
        myContext = context;
    }

    public static Utils init(Context context){
        if(utils == null)
            utils = new Utils(context);
        return utils;
    }

    public boolean checkInternetConnecction(){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }
}
