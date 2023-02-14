package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.UserAndPasswordFr;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llay = findViewById(R.id.cl);
        mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
        mainActivityPresenter.initTaskManager();

        mainActivityPresenter.addFragment(new UserAndPasswordFr());

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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.loginModesCL, baseFragment)
                .commit();
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