package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
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
    private ConstraintLayout activityCL;

    private ConstraintLayout loginModesCL1;
    private ConstraintLayout loginModesCL2;

    private WindowSizeClass[] windowSizeClasses;

    private String clname;

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

        if(activityCL != null){
            activityCL.setOnClickListener(view -> {
                Util.hideNavigationBar(this);
                Util.hideKeyboard(this);
            });
        }

        // ki kell szervezni a presenterbe az ezután lévő részt

        if(loginModesCL1 != null || loginModesCL2 != null){

            if(loginAccAndPassButton != null){
                loginAccAndPassButton.setOnClickListener(view -> {

                    mainActivityPresenter.addFragmentByEnum(FragmentTypes.USERPASSFRAGMENT);
                });
            }
            if(loginPinButton != null){
                loginPinButton.setOnClickListener(view -> {

                    mainActivityPresenter.addFragmentByEnum(FragmentTypes.PINCODEFRAGMENT);
                });
            }
            if(loginRFIDButton != null){
                loginRFIDButton.setOnClickListener(view -> {

                    mainActivityPresenter.addFragmentByEnum(FragmentTypes.RFIDFRAGMENT);
                });
            }
            if(loginBarcodeButton != null){
                loginBarcodeButton.setOnClickListener(view -> {

                    mainActivityPresenter.addFragmentByEnum(FragmentTypes.BARCODEFRAGMENT);
                });
            }
        }
    }

    @Override
    public void loadOtherActivityFragment(BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);

        if(loginModesCL1 != null) {

            if(clname.equals("loginModesCL1_mobile_portrait")) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.loginModesCL1_mobile_portrait, baseFragment)
                        .commit();
            }
            else if(clname.equals("loginModesCL1_pda_portrait")) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.loginModesCL1_pda_portrait, baseFragment)
                        .commit();
            }
        }
        else if(loginModesCL2 != null){

            if(clname.equals("loginModesCL2_mobile_landscape")) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.loginModesCL2_mobile_landscape, baseFragment)
                        .commit();
            }
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
        int orientation = this.getResources().getConfiguration().orientation;

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_main_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                llay = findViewById(R.id.cl_mobile_portrait);
                loginModesCL1 = findViewById(R.id.loginModesCL1_mobile_portrait);
                activityCL = findViewById(R.id.activityCL_mobile_portrait);

                clname = getResources().getResourceEntryName(loginModesCL1.getId());

            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.cl2_mobile_landscape);
                loginModesCL2 = findViewById(R.id.loginModesCL2_mobile_landscape);
                activityCL = findViewById(R.id.activityCL_mobile_landscape);

                clname = getResources().getResourceEntryName(loginModesCL2.getId());
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            setContentView(R.layout.activity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            llay = findViewById(R.id.cl_pda_portrait);
            loginModesCL1 = findViewById(R.id.loginModesCL1_pda_portrait);
            activityCL = findViewById(R.id.activityCL_pda_portrait);

            clname = getResources().getResourceEntryName(loginModesCL1.getId());
        }

        //Util.hideNavigationBar(this);
        //Util.hideActionBar(this);
    }
}