package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by User on 15/03/2017.
 * This class is used to check whether there is internet connection available on the device.
 * To do so, it uses a "NetworkInfo" object. If this object is equal to null, that implies
 * that there is no internet connection available and therefore false is returned. If the
 * object is not equal to null, true is returned.
 */

public class CheckInternet {

    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                return true;
            }
            else
            {
                return true;
            }

        }
    }
}