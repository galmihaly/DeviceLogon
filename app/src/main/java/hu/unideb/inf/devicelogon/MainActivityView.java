package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    private ConstraintLayout loginModesCL1;
    private ConstraintLayout loginModesCL2;

    private boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        int orientation = this.getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            setContentView(R.layout.activity_main_mobile_portrait);
            setTheme(R.style.DeviceLogon_portrait);
            llay = findViewById(R.id.cl);
            loginModesCL1 = findViewById(R.id.loginModesCL1);
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.activity_main_mobile_landscape);
            setTheme(R.style.DeviceLogon_landscape);
            llay = findViewById(R.id.cl2);
            loginModesCL2 = findViewById(R.id.loginModesCL2);
        }



        mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        mainActivityPresenter.initTaskManager();

        Intent intent = getIntent();
        int modesNumber = intent.getIntExtra("ModesNumber", -1);
        if(modesNumber != -1) mainActivityPresenter.initButtonsByLoginModesNumber(modesNumber);
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }

    @Override
    public void loadOtherActivityFragment(BaseFragment baseFragment) {
        if(baseFragment == null) return;

        baseFragment.atachPresenter(mainActivityPresenter);

        if(isEmpty){
            if(loginModesCL1 != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.loginModesCL1, baseFragment)
                        .commit();
            }
            else if(loginModesCL2 != null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.loginModesCL2, baseFragment)
                        .commit();
            }
        }
        isEmpty = false;
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

                loginAccAndPassButton.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "USERPASS", Toast.LENGTH_LONG).show();
                });

                break;
            }
            case Util.BUTTON_PINCODE:{
                loginPinButton = imageButton;

                loginPinButton.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "PIN", Toast.LENGTH_LONG).show();
                });

                break;
            }
            case Util.BUTTON_RFID:{
                loginRFIDButton = imageButton;

                loginRFIDButton.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "RFID", Toast.LENGTH_LONG).show();
                });

                break;
            }
            case Util.BUTTON_BARCODE:{
                loginBarcodeButton = imageButton;

                loginBarcodeButton.setOnClickListener(view -> {
                    Toast.makeText(getApplicationContext(), "BARCODE", Toast.LENGTH_LONG).show();
                });

                break;
            }
        }
    }
}