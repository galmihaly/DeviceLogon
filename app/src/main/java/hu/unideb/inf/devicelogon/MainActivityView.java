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
import hu.unideb.inf.devicelogon.fragments.mobilefragments.RFIDFragmentPDA;
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

    private boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();
        hideActionBar();
        initView();

        WindowSizeClass[] windowSizeClasses = Util.computeWindowSizeClasses(this);

        mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext(), windowSizeClasses);
        mainActivityPresenter.initTaskManager();

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
                hideNavigationBar();
                hideKeyboard();
            });
        }

        if(loginAccAndPassButton != null){
            loginAccAndPassButton.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), "USERPASS", Toast.LENGTH_LONG).show();
            });
        }

        if(loginPinButton != null){
            loginPinButton.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), "PIN", Toast.LENGTH_LONG).show();
            });
        }

        if(loginRFIDButton != null){
            loginRFIDButton.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), "RFID", Toast.LENGTH_LONG).show();
                if(loginModesCL1 != null){

                    mainActivityPresenter.addFragment(new UserPassFragmentPDA());
                }
            });
        }

        if(loginBarcodeButton != null){
            loginBarcodeButton.setOnClickListener(view -> {
                Toast.makeText(getApplicationContext(), "BARCODE", Toast.LENGTH_LONG).show();
            });
        }

    }

    @Override
    public void loadOtherActivityFragment(BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);

//        if(isEmpty){
//            if(loginModesCL1 != null){
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.loginModesCL1_pda_portrait, baseFragment)
//                        .commit();
//            }
//            else if(loginModesCL2 != null){
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.loginModesCL2, baseFragment)
//                        .commit();
//            }
//        }

//        if(loginModesCL1 != null){
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.loginModesCL1_pda_portrait, baseFragment)
//                    .commit();
//        }
//        else if(loginModesCL2 != null){
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.loginModesCL2, baseFragment)
//                    .commit();
//        }

        //isEmpty = false;
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

        WindowSizeClass[] windowSizeClasses = Util.computeWindowSizeClasses(this);

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            int orientation = this.getResources().getConfiguration().orientation;
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_main_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                llay = findViewById(R.id.cl_mobile_portrait);
                loginModesCL1 = findViewById(R.id.loginModesCL1);

            }
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

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

    private void hideKeyboard(){

        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}