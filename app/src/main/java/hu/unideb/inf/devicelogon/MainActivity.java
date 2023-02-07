package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import hu.unideb.inf.devicelogon.interfaces.IMainActivityView;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        startActivity(intent);
    }
}