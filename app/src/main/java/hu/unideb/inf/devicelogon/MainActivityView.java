package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;
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

    private final List<BaseFragment> baseFragmentList = new ArrayList<>();

    private int modesNumber;
    private int buttonsListSize;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private int position;
    private List<ImageButton> imageButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        if(windowSizeClasses != null) {
            mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext(), windowSizeClasses);
            mainActivityPresenter.initTaskManager();
            fragmentPagerAdapter = new FragmentPagerAdapter(this);
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

        if(loginAccAndPassButton != null){
            loginAccAndPassButton.setOnClickListener(view -> {

                position = fragmentPagerAdapter.getCurrentFragment(FragmentTypes.USERPASSFRAGMENT);
                Util.changeButtonColor(imageButtons, position);

                if(loginModesCL1 != null) { loginModesCL1.setCurrentItem(position); }
                else if(loginModesCL2 != null){ loginModesCL2.setCurrentItem(position); }

            });
        }
        if(loginPinButton != null){
            loginPinButton.setOnClickListener(view -> {

                position = fragmentPagerAdapter.getCurrentFragment(FragmentTypes.PINCODEFRAGMENT);
                Util.changeButtonColor(imageButtons, position);

                if(loginModesCL1 != null) { loginModesCL1.setCurrentItem(position); }
                else if(loginModesCL2 != null){ loginModesCL2.setCurrentItem(position); }
            });
        }
        if(loginRFIDButton != null){
            loginRFIDButton.setOnClickListener(view -> {

                position = fragmentPagerAdapter.getCurrentFragment(FragmentTypes.RFIDFRAGMENT);
                Util.changeButtonColor(imageButtons, position);

                if(loginModesCL1 != null) { loginModesCL1.setCurrentItem(position); }
                else if(loginModesCL2 != null){ loginModesCL2.setCurrentItem(position); }

            });
        }
        if(loginBarcodeButton != null){
            loginBarcodeButton.setOnClickListener(view -> {

                position = fragmentPagerAdapter.getCurrentFragment(FragmentTypes.BARCODEFRAGMENT);
                Util.changeButtonColor(imageButtons, position);

                if(loginModesCL1 != null) { loginModesCL1.setCurrentItem(position); }
                else if(loginModesCL2 != null){ loginModesCL2.setCurrentItem(position); }
            });
        }
    }

    @Override
    public void loadOtherActivityFragment(FragmentTypes fragmentTypes, BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);

        if(loginModesCL1 != null && fragmentPagerAdapter != null) {

            fragmentPagerAdapter.addFragment(baseFragment);
            fragmentPagerAdapter.setItem(fragmentTypes, baseFragment);

            if(fragmentPagerAdapter.getItemCount() == buttonsListSize){
                loginModesCL1.setAdapter(fragmentPagerAdapter);
            }
        }
        else if(loginModesCL2 != null && fragmentPagerAdapter != null){

            fragmentPagerAdapter.addFragment(baseFragment);
            fragmentPagerAdapter.setItem(fragmentTypes, baseFragment);

            if(fragmentPagerAdapter.getItemCount() == buttonsListSize){
                loginModesCL2.setAdapter(fragmentPagerAdapter);
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
                imageButtons.add(imageButton);
                break;
            }
            case Util.BUTTON_PINCODE:{
                loginPinButton = imageButton;
                imageButtons.add(imageButton);
                break;
            }
            case Util.BUTTON_RFID:{
                loginRFIDButton = imageButton;
                imageButtons.add(imageButton);
                break;
            }
            case Util.BUTTON_BARCODE:{
                loginBarcodeButton = imageButton;
                imageButtons.add(imageButton);
                break;
            }
        }

        if(imageButtons.size() != 0){
            Util.changeButtonColor(imageButtons, 0);
        }

        onResume();
    }

    @Override
    public void getButtonsListSize(int integer) {
        buttonsListSize = integer;
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
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            setContentView(R.layout.activity_main_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);

            llay = findViewById(R.id.cl_pda_portrait);
            loginModesCL1 = findViewById(R.id.loginModesCL1_pda_portrait);
            activityCL = findViewById(R.id.activityCL_pda_portrait);

        }

        Util.hideNavigationBar(this);
        Util.hideActionBar(this);

        imageButtons = new ArrayList<>();
    }
}