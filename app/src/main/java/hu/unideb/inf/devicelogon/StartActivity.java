package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import hu.unideb.inf.devicelogon.interfaces.IStartActivityView;

public class StartActivity extends AppCompatActivity implements IStartActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivity_main);
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        startActivity(intent);
    }
}