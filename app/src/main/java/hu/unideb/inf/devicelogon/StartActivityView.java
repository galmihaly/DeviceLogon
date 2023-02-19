package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.interfaces.IStartActivityView;
import hu.unideb.inf.devicelogon.presenters.StartActivityPresenter;
import hu.unideb.inf.devicelogon.utils.Util;

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
        Util.hideActionBar(this);
        Util.hideNavigationBar(this);
        initView();

        startActivityPresenter = new StartActivityPresenter(this, getApplicationContext());
        startActivityPresenter.initTaskManager();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(buttonPr != null) {
            buttonPr.setOnClickListener((view -> {
                try {
                    String inputString = modesNumberPr.getText().toString();
                    if(!inputString.equals("")){
                        startActivityPresenter.createLoginActivityIntentByModesNumber(Integer.parseInt(inputString));
                    }
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }));
        }

        if(buttonLs != null) {
            buttonLs.setOnClickListener((view -> {
                try {
                    String inputString = modesNumberLs.getText().toString();
                    if(!inputString.equals("")){
                        startActivityPresenter.createLoginActivityIntentByModesNumber(Integer.parseInt(inputString));
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

        WindowSizeClass[] windowSizeClasses = Util.computeWindowSizeClasses(this);
        int orientation = this.getResources().getConfiguration().orientation;

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.startactivity_main_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);

                modesNumberPr = findViewById(R.id.modesNumber_mobile_portrait);
                buttonPr = findViewById(R.id.startButton_mobile_portrait);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.startactivity_main_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);

                modesNumberLs = findViewById(R.id.modesNumber_landscape);
                buttonLs = findViewById(R.id.startButton_landscape);
            }
        }

        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.startactivity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            modesNumberPr = findViewById(R.id.modesNumber_pda_portrait);
            buttonPr = findViewById(R.id.startButton_pda_portrait);
        }
    }
}