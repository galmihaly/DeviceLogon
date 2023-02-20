package hu.unideb.inf.devicelogon.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator;

import java.util.List;

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

    // Size of Buttons List
    public static final int BUTTONS_LIST_SIZE = 100;

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

        float heightDp = metrics.getBounds().height() / appCompatActivity.getResources().getDisplayMetrics().density;

        WindowSizeClass heightWindowSizeClass;

        if (heightDp < 600f) { heightWindowSizeClass = WindowSizeClass.COMPACT;}
        else if (heightDp < 900f) { heightWindowSizeClass = WindowSizeClass.MEDIUM; }
        else { heightWindowSizeClass = WindowSizeClass.EXPANDED; }

        WindowSizeClass[] result = new WindowSizeClass[]{
                heightWindowSizeClass,
                widthWindowSizeClass
        };

        return result;
    }

    public static void hideActionBar(AppCompatActivity appCompatActivity){
        appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(appCompatActivity.getSupportActionBar() != null) appCompatActivity.getSupportActionBar().hide();
    }

    public static void hideNavigationBar(AppCompatActivity appCompatActivity){
        View decorView = appCompatActivity.getWindow().getDecorView();

        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(flags);

        decorView.setOnSystemUiVisibilityChangeListener(i -> {
            if((i & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) != 0){
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            }
        });
    }

    public static void hideKeyboard(AppCompatActivity appCompatActivity){

        View view = appCompatActivity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void changeButtonColor(List<ImageButton> imageButtonList, int isActive){

        for (int i = 0; i < imageButtonList.size(); i++) {
            if(i != isActive){
                imageButtonList.get(i).getBackground().setAlpha(128);
                imageButtonList.get(i).setImageAlpha(128);
            }
            else {
                imageButtonList.get(i).getBackground().setAlpha(255);
                imageButtonList.get(i).setImageAlpha(255);
            }
        }
    }
}
