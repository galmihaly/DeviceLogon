package hu.unideb.inf.devicelogon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

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

    private ViewPager2 loginModesCL1;
    private ViewPager2 loginModesCL2;

    private WindowSizeClass[] windowSizeClasses;

    private int modesNumber;
    private int buttonsListSize;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPagerListener viewPagerListener = ViewPagerListener.getInstance();

    private LoginButtonListener loginButtonListener1 = new LoginButtonListener();
    private LoginButtonListener loginButtonListener2 = new LoginButtonListener();
    private LoginButtonListener loginButtonListener3 = new LoginButtonListener();
    private LoginButtonListener loginButtonListener4 = new LoginButtonListener();

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

        if(loginModesCL1 != null) { loginModesCL1.registerOnPageChangeCallback(viewPagerListener); }
        if(loginModesCL2 != null) { loginModesCL2.registerOnPageChangeCallback(viewPagerListener); }

        if(loginAccAndPassButton != null){
            if(loginModesCL1 != null) loginButtonListener1.setViewPager(loginModesCL1);
            if(loginModesCL2 != null) loginButtonListener1.setViewPager(loginModesCL2);
            loginButtonListener1.setFragmentPagerAdapter(fragmentPagerAdapter);
            loginButtonListener1.setfT(FragmentTypes.USERPASSFRAGMENT);

            loginAccAndPassButton.setOnClickListener(loginButtonListener1);
        }
        if(loginPinButton != null){
            if(loginModesCL1 != null) loginButtonListener2.setViewPager(loginModesCL1);
            if(loginModesCL2 != null) loginButtonListener2.setViewPager(loginModesCL2);
            loginButtonListener2.setFragmentPagerAdapter(fragmentPagerAdapter);
            loginButtonListener2.setfT(FragmentTypes.PINCODEFRAGMENT);

            loginPinButton.setOnClickListener(loginButtonListener2);
        }
        if(loginRFIDButton != null){
            if(loginModesCL1 != null) loginButtonListener3.setViewPager(loginModesCL1);
            if(loginModesCL2 != null) loginButtonListener3.setViewPager(loginModesCL2);
            loginButtonListener3.setFragmentPagerAdapter(fragmentPagerAdapter);
            loginButtonListener3.setfT(FragmentTypes.RFIDFRAGMENT);

            loginRFIDButton.setOnClickListener(loginButtonListener3);
        }
        if(loginBarcodeButton != null){
            if(loginModesCL1 != null) loginButtonListener4.setViewPager(loginModesCL1);
            if(loginModesCL2 != null) loginButtonListener4.setViewPager(loginModesCL2);
            loginButtonListener4.setFragmentPagerAdapter(fragmentPagerAdapter);
            loginButtonListener4.setfT(FragmentTypes.BARCODEFRAGMENT);

            loginBarcodeButton.setOnClickListener(loginButtonListener4);
        }
    }

    @Override
    public void loadOtherActivityFragment(FragmentTypes fragmentTypes, BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);
        Util.sendDisplaySizesToFragments(baseFragment, windowSizeClasses, fragmentTypes);

        if(fragmentPagerAdapter != null){
            fragmentPagerAdapter.addFragment(baseFragment);
            fragmentPagerAdapter.addItemToHashMap(fragmentTypes, baseFragment);

            if(fragmentPagerAdapter.getItemCount() == buttonsListSize){
               if(loginModesCL1 != null)  loginModesCL1.setAdapter(fragmentPagerAdapter);
               if(loginModesCL2 != null)  loginModesCL2.setAdapter(fragmentPagerAdapter);
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
                llay = findViewById(R.id.cl_mobile_portrait);
                loginModesCL1 = findViewById(R.id.loginModesCL1_mobile_portrait);
                activityCL = findViewById(R.id.activityCL_mobile_portrait);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.cl2_mobile_landscape);
                loginModesCL2 = findViewById(R.id.loginModesCL2_mobile_landscape);
                activityCL = findViewById(R.id.activityCL_mobile_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_main_tablet_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                llay = findViewById(R.id.cl2_tablet_landscape);
                loginModesCL2 = findViewById(R.id.loginModesCL2_tablet_landscape);
                activityCL = findViewById(R.id.activityCL_tablet_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.activity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            llay = findViewById(R.id.cl_pda_portrait);
            loginModesCL1 = findViewById(R.id.loginModesCL1_pda_portrait);
            activityCL = findViewById(R.id.activityCL_pda_portrait);

            Util.hideNavigationBar(this);
            Util.hideActionBar(this);
        }

        fragmentPagerAdapter = new FragmentPagerAdapter(this);
        viewPagerListener.setFragmentPagerAdapter(fragmentPagerAdapter);
    }
}