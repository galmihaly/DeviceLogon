package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityView;
import hu.unideb.inf.devicelogon.presenters.MainActivityPresenter;

public class MainActivityView extends AppCompatActivity implements IMainActivityView {

    //Presenter
    private MainActivityPresenter mainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityPresenter = new MainActivityPresenter(this, getApplicationContext());
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void loadOtherActivityFragment(BaseFragment baseFragment) {
        baseFragment.atachPresenter(mainActivityPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.loginModesCL, baseFragment)
                .commit();
    }
}