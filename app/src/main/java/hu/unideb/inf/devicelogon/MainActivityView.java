package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;
import hu.unideb.inf.devicelogon.listeners.LoginButtonListener;
import hu.unideb.inf.devicelogon.listeners.ViewPagerListener;
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
    private ConstraintLayout activityCL;

    private ViewPager2 loginModesCL_portrait;
    private ViewPager2 loginModesCL_landscape;

    private WindowSizeClass[] windowSizeClasses;

    private int modesNumber;
    private int buttonsListSize;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPagerListener viewPagerListener = ViewPagerListener.getInstance();

    private LoginButtonListener userPassLogButListener = new LoginButtonListener();
    private LoginButtonListener pinCodeLogButListener = new LoginButtonListener();
    private LoginButtonListener rfidLogButListener = new LoginButtonListener();
    private LoginButtonListener barcodeLogButListener = new LoginButtonListener();

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
            modesNumber = intent.getIntExtra("ModesNumber", -1);

            if(modesNumber != -1) mainActivityPresenter.initButtonsByLoginModesNumber(modesNumber);
        }
    }

    // a button listenereket szintén át lehet rakni a presenterbe
    @Override
    protected void onResume() {
        super.onResume();

        if(activityCL != null){
            activityCL.setOnClickListener(view -> {
                Util.hideNavigationBar(this);
                Util.hideKeyboard(this);
            });
        }

        if(loginModesCL_portrait != null) { loginModesCL_portrait.registerOnPageChangeCallback(viewPagerListener); }
        if(loginModesCL_landscape != null) { loginModesCL_landscape.registerOnPageChangeCallback(viewPagerListener); }

        if(loginAccAndPassButton != null){
            if(loginModesCL_portrait != null) userPassLogButListener.setViewPager(loginModesCL_portrait);
            if(loginModesCL_landscape != null) userPassLogButListener.setViewPager(loginModesCL_landscape);
            userPassLogButListener.setFragmentPagerAdapter(fragmentPagerAdapter);
            userPassLogButListener.setfT(FragmentTypes.USERPASSFRAGMENT);

            loginAccAndPassButton.setOnClickListener(userPassLogButListener);
        }
        if(loginPinButton != null){
            if(loginModesCL_portrait != null) pinCodeLogButListener.setViewPager(loginModesCL_portrait);
            if(loginModesCL_landscape != null) pinCodeLogButListener.setViewPager(loginModesCL_landscape);
            pinCodeLogButListener.setFragmentPagerAdapter(fragmentPagerAdapter);
            pinCodeLogButListener.setfT(FragmentTypes.PINCODEFRAGMENT);

            loginPinButton.setOnClickListener(pinCodeLogButListener);
        }
        if(loginRFIDButton != null){
            if(loginModesCL_portrait != null) rfidLogButListener.setViewPager(loginModesCL_portrait);
            if(loginModesCL_landscape != null) rfidLogButListener.setViewPager(loginModesCL_landscape);
            rfidLogButListener.setFragmentPagerAdapter(fragmentPagerAdapter);
            rfidLogButListener.setfT(FragmentTypes.RFIDFRAGMENT);

            loginRFIDButton.setOnClickListener(rfidLogButListener);
        }
        if(loginBarcodeButton != null){
            if(loginModesCL_portrait != null) barcodeLogButListener.setViewPager(loginModesCL_portrait);
            if(loginModesCL_landscape != null) barcodeLogButListener.setViewPager(loginModesCL_landscape);
            barcodeLogButListener.setFragmentPagerAdapter(fragmentPagerAdapter);
            barcodeLogButListener.setfT(FragmentTypes.BARCODEFRAGMENT);

            loginBarcodeButton.setOnClickListener(barcodeLogButListener);
        }
    }

    // át lehet rakni a presenterbe
    @Override
    public void loadOtherActivityFragment(FragmentTypes fragmentTypes, BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);
        Util.sendDisplaySizesToFragments(baseFragment, windowSizeClasses, fragmentTypes);

        if(fragmentPagerAdapter != null){
            fragmentPagerAdapter.addFragment(baseFragment);
            fragmentPagerAdapter.addItemToHashMap(fragmentTypes, baseFragment);

            if(fragmentPagerAdapter.getItemCount() == buttonsListSize){
                if(loginModesCL_portrait != null)  loginModesCL_portrait.setAdapter(fragmentPagerAdapter);
                if(loginModesCL_landscape != null)  loginModesCL_landscape.setAdapter(fragmentPagerAdapter);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }

    // ezt át lehet rakni a presenterbe
    @Override
    public void setButtonToLayout(ImageButton imageButton) {
        if(llay == null) return;
        if(imageButton == null) return;

        llay.addView(imageButton);
        llay.invalidate();

        switch (imageButton.getId()){
            case Util.BUTTON_USER_PASS:{
                loginAccAndPassButton = imageButton;
                fragmentPagerAdapter.addButtonToList(imageButton);
                break;
            }
            case Util.BUTTON_PINCODE:{
                loginPinButton = imageButton;
                fragmentPagerAdapter.addButtonToList(imageButton);
                break;
            }
            case Util.BUTTON_RFID:{
                loginRFIDButton = imageButton;
                fragmentPagerAdapter.addButtonToList(imageButton);
                break;
            }
            case Util.BUTTON_BARCODE:{
                loginBarcodeButton = imageButton;
                fragmentPagerAdapter.addButtonToList(imageButton);
                break;
            }
        }
        onResume();
    }

    @Override
    public void getButtonsListSize(int integer) { buttonsListSize = integer; }

    private void initView(){

        windowSizeClasses = Util.computeWindowSizeClasses(this);
        int orientation = this.getResources().getConfiguration().orientation;

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_main_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                llay = findViewById(R.id.logButtonsCL_mobile_portrait);
                loginModesCL_portrait = findViewById(R.id.loginModesCL1_mobile_portrait);
                activityCL = findViewById(R.id.activityCL_mobile_portrait);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.logButtonsCL_mobile_landscape);
                loginModesCL_landscape = findViewById(R.id.loginModesCL2_mobile_landscape);
                activityCL = findViewById(R.id.activityCL_mobile_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.EXPANDED && windowSizeClasses[1] == WindowSizeClass.MEDIUM){
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {

                setContentView(R.layout.activity_main_tablet_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                llay = findViewById(R.id.logButtonsCL_tablet_portrait);
                loginModesCL_landscape = findViewById(R.id.loginModesCL1_tablet_portrait);
                activityCL = findViewById(R.id.activityCL_tablet_portrait);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_tablet_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.logButtonsCL_tablet_landscape);
                loginModesCL_landscape = findViewById(R.id.loginModesCL2_tablet_landscape);
                activityCL = findViewById(R.id.activityCL_tablet_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.activity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            llay = findViewById(R.id.logButtonsCL_pda_portrait);
            loginModesCL_portrait = findViewById(R.id.loginModesCL1_pda_portrait);
            activityCL = findViewById(R.id.activityCL_pda_portrait);

            Util.hideNavigationBar(this);
            Util.hideActionBar(this);
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerListener.setFragmentPagerAdapter(fragmentPagerAdapter);
    }
}