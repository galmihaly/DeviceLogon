package hu.unideb.inf.devicelogon.utils;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import hu.unideb.inf.devicelogon.enums.WindowSizeClass;


public class Util {

    public static final String MESSAGE_BODY = "MESSAGE_BODY";

    //Buttons created enums
    public static final int BUTTONS_IS_CREATED = 1;
    public static final int BUTTONS_NOT_CREATED = 2;

    //Buttons IDs
    public static final int BUTTON_USER_PASS = 10;
    public static final int BUTTON_PINCODE = 11;
    public static final int BUTTON_RFID = 12;
    public static final int BUTTON_BARCODE = 13;

    public static Message createMessage(int id, String dataString) {
        Bundle bundle = new Bundle();
        bundle.putString(Util.MESSAGE_BODY, dataString);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);

        return message;
    }

    public static WindowSizeClass[] computeWindowSizeClasses(AppCompatActivity appCompatActivity) {
        WindowMetrics metrics = WindowMetricsCalculator.getOrCreate()
                .computeCurrentWindowMetrics(appCompatActivity);

        float widthDp = metrics.getBounds().width() / appCompatActivity.getResources().getDisplayMetrics().density;

        WindowSizeClass widthWindowSizeClass;

        if (widthDp < 600f) { widthWindowSizeClass = WindowSizeClass.COMPACT; }
        else if (widthDp < 840f) { widthWindowSizeClass = WindowSizeClass.MEDIUM; }
        else { widthWindowSizeClass = WindowSizeClass.EXPANDED; }

        Log.e("withdp", String.valueOf(widthDp));
        Log.e("metrics.getBounds().width()", String.valueOf(metrics.getBounds().width()));
        Log.e("getResources().getDisplayMetrics().density", String.valueOf(appCompatActivity.getResources().getDisplayMetrics().density));
        Log.e("", String.valueOf(widthWindowSizeClass));

        float heightDp = metrics.getBounds().height() / appCompatActivity.getResources().getDisplayMetrics().density;

        WindowSizeClass heightWindowSizeClass;

        if (heightDp < 600f) { heightWindowSizeClass = WindowSizeClass.COMPACT;}
        else if (heightDp < 900f) { heightWindowSizeClass = WindowSizeClass.MEDIUM; }
        else { heightWindowSizeClass = WindowSizeClass.EXPANDED; }

        Log.e("", String.valueOf(heightDp));
        Log.e("metrics.getBounds().height()", String.valueOf(metrics.getBounds().height()));
        Log.e("getResources().getDisplayMetrics().density", String.valueOf(appCompatActivity.getResources().getDisplayMetrics().density));
        Log.e("", String.valueOf(heightWindowSizeClass));

        WindowSizeClass[] result = new WindowSizeClass[]{
                heightWindowSizeClass,
                widthWindowSizeClass
        };

        return result;
    }
}
