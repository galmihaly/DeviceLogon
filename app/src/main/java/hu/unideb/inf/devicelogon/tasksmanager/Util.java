package hu.unideb.inf.devicelogon.tasksmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;

public class Util {

    public static final int TASKS_CANCELLED = 1;

    public static final int ROOM_CREATE_FAIL = 2;
    public static final int ROOM_SEND_FAIL = 4;
    public static final int SQL_READ_FAIL = 5;
    public static final int PROGRAMSTART_FINISH_1 = 3;
    public static final int PROGRAMSTART_FINISH_2 = 6;
    public static final int PROGRAMSATRT_FINISH_3 = 7;

    //Login Barcode Enums
    public static final int LOGINBARCODE_FINISH = 8;
    public static final int LOGINBARCODE_NORIGHTS = 9;
    public static final int LOGINBARCODE_NORIGHT = 10;
    public static final int LOGINBARCODE_FAILED = 11;

    public static final int WIFI_FAILED = 12;

    public static final String MESSAGE_BODY = "MESSAGE_BODY";

    public static String getReadableTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    public static Message createMessage(int id, String dataString) {
        Bundle bundle = new Bundle();
        bundle.putString(Util.MESSAGE_BODY, dataString);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);

        return message;
    }

    public static boolean isInternetConnection(Context context){

        boolean isConnected;
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cm != null){
            Network connectedNetwork = cm.getActiveNetwork();
            if(connectedNetwork != null){
                NetworkCapabilities nc = cm.getNetworkCapabilities(connectedNetwork);
                if(nc != null && nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    isConnected = true;
                }
                else {
                    ApplicationLogger.logging(LogLevel.WARNING, "Van elérhető hálózat, de annak típusa nem WIFI!");
                    isConnected = false;
                }
            }
            else {
                ApplicationLogger.logging(LogLevel.WARNING, "Nincs elérhető WIFI hálózat az applikáció számára!");
                isConnected = false;
            }
        }
        else {
            ApplicationLogger.logging(LogLevel.WARNING, "Nem elérhető az applikáció számára az Internet csatlakozási szolgáltaltás!");
            isConnected = false;
        }
        return isConnected;
    }
}
