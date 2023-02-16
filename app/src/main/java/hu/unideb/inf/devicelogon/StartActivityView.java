package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import hu.unideb.inf.devicelogon.interfaces.IStartActivityView;
import hu.unideb.inf.devicelogon.presenters.StartActivityPresenter;

public class StartActivityView extends AppCompatActivity implements IStartActivityView {

    //Portrait
    private EditText modesNumberPr;
    private Button buttonPr;

    //Landscape
    private EditText modesNumberLs;
    private Button buttonLs;

    private StartActivityPresenter startActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        hideNavigationBar();
        initView();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.e("display", displayMetrics.heightPixels + "x" + displayMetrics.widthPixels);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        Log.d("debug","Screen inches : " + screenInches);
        screenInches=  (double)Math.round(screenInches * 10) / 10;
        Log.e("inches: {}", String.valueOf(screenInches));

        startActivityPresenter = new StartActivityPresenter(this, getApplicationContext());
        startActivityPresenter.initTaskManager();

        if(buttonPr != null) {
            buttonPr.setOnClickListener((view -> {

                try {
                    String inputString = modesNumberPr.getText().toString();
                    if(!inputString.equals("")){
                        startActivityPresenter.createLoginActivityIntentByModesNumber(Integer.parseInt(modesNumberPr.getText().toString()));
                    }
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }

            }));
        }
    }


    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }

    private void initView(){

        int orientation = this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            setContentView(R.layout.startactivity_main_mobile_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            modesNumberPr = findViewById(R.id.modesNumber_portrait);
            buttonPr = findViewById(R.id.startButton_portrait);
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.startactivity_main_mobile_landscape);
            setTheme(R.style.DeviceLogon_landscape);

            modesNumberLs = findViewById(R.id.modesNumber_landscape);
            buttonPr = findViewById(R.id.startButton_landscape);
        }

    }

    private void hideActionBar(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar() != null) getSupportActionBar().hide();
    }

    private void hideNavigationBar(){
        View decorView = this.getWindow().getDecorView();

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
}