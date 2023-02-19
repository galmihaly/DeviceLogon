package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.pdafragments.BarcodeFragmentPDA;
import hu.unideb.inf.devicelogon.fragments.pdafragments.PinCodeFragmentPDA;
import hu.unideb.inf.devicelogon.fragments.pdafragments.RFIDFragmentPDA;
import hu.unideb.inf.devicelogon.fragments.pdafragments.UserPassFragmentPDA;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityView;
import hu.unideb.inf.devicelogon.presenters.MainActivityPresenter;
import hu.unideb.inf.devicelogon.utils.Util;

public class MainActivityView extends AppCompatActivity implements IMainActivityView {

    //Presenter
    private MainActivityPresenter mainActivityPresenter;
    private LinearLayout llay;
    private ImageButton loginAccAndPassButton;
    private ImageButton loginPinButton;
    private ImageButton loginRFIDButton;
    private ImageButton loginBarcodeButton;
    private ConstraintLayout activityCL_pda_portrait;

    private ConstraintLayout loginModesCL1;
    private ConstraintLayout loginModesCL2;

    private WindowSizeClass[] windowSizeClasses;

    private boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        if(windowSizeClasses != null) {
            mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext(), windowSizeClasses);
            mainActivityPresenter.initTaskManager();
        }

        if(loginBarcodeButton == null || loginRFIDButton == null || loginPinButton == null || loginAccAndPassButton == null){
            Intent intent = getIntent();
            int modesNumber = intent.getIntExtra("ModesNumber", -1);
            if(modesNumber != -1) mainActivityPresenter.initButtonsByLoginModesNumber(modesNumber);
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(activityCL_pda_portrait != null){
            activityCL_pda_portrait.setOnClickListener(view -> {
                Util.hideNavigationBar(this);
                Util.hideKeyboard(this);
            });
        }

        if(loginAccAndPassButton != null){
            loginAccAndPassButton.setOnClickListener(view -> {

                if(loginModesCL1 != null){
                    mainActivityPresenter.addFragment(new UserPassFragmentPDA());
                }
            });
        }

        if(loginPinButton != null){
            loginPinButton.setOnClickListener(view -> {

                if(loginModesCL1 != null){
                    mainActivityPresenter.addFragment(new PinCodeFragmentPDA());
                }
            });
        }

        if(loginRFIDButton != null){
            loginRFIDButton.setOnClickListener(view -> {

                if(loginModesCL1 != null){
                    mainActivityPresenter.addFragment(new RFIDFragmentPDA());
                }
            });
        }

        if(loginBarcodeButton != null){
            loginBarcodeButton.setOnClickListener(view -> {

                if(loginModesCL1 != null){
                    mainActivityPresenter.addFragment(new BarcodeFragmentPDA());
                }
            });
        }
    }

    @Override
    public void loadOtherActivityFragment(BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);

        if(loginModesCL1 != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginModesCL1_pda_portrait, baseFragment)
                    .commit();
        }
        else if(loginModesCL2 != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginModesCL2, baseFragment)
                    .commit();
        }
    }

    @Override
    public void setButtonToLayout(ImageButton imageButton) {
        if(llay == null) return;
        if(imageButton == null) return;

        llay.addView(imageButton);
        llay.invalidate();

        switch (imageButton.getId()){
            case Util.BUTTON_USER_PASS:{
                Log.e("", "asdasda");
                loginAccAndPassButton = imageButton;
                break;
            }
            case Util.BUTTON_PINCODE:{
                loginPinButton = imageButton;
                break;
            }
            case Util.BUTTON_RFID:{
                loginRFIDButton = imageButton;
                break;
            }
            case Util.BUTTON_BARCODE:{
                loginBarcodeButton = imageButton;
                break;
            }
        }
        onResume();
    }

    private void initView(){

        windowSizeClasses = Util.computeWindowSizeClasses(this);

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            int orientation = this.getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_main_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                llay = findViewById(R.id.cl_mobile_portrait);
                loginModesCL1 = findViewById(R.id.loginModesCL1);

            }
            else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.cl2);
                loginModesCL2 = findViewById(R.id.loginModesCL2);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            setContentView(R.layout.activity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            llay = findViewById(R.id.cl_pda_portrait);
            loginModesCL1 = findViewById(R.id.loginModesCL1_pda_portrait);
            activityCL_pda_portrait = findViewById(R.id.activityCL_pda_portrait);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.loginModesCL1_pda_portrait, new RFIDFragmentPDA())
                    .commit();

        }

        Util.hideNavigationBar(this);
        Util.hideActionBar(this);
    }
}