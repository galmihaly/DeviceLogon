package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import hu.unideb.inf.devicelogon.interfaces.IStartActivityView;
import hu.unideb.inf.devicelogon.presenters.StartActivityPresenter;

public class StartActivityView extends AppCompatActivity implements IStartActivityView {

    private EditText modesNumber;
    private Button button;
    private StartActivityPresenter startActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(getSupportActionBar() != null) getSupportActionBar().hide();

        setContentView(R.layout.startactivity_main);
        modesNumber = findViewById(R.id.modesNumber);

        button = findViewById(R.id.startButton);
        startActivityPresenter = new StartActivityPresenter(this, getApplicationContext());
        startActivityPresenter.initTaskManager();

        button.setOnClickListener((view -> {
            startActivityPresenter.createLoginActivityIntentByModesNumber(Integer.parseInt(modesNumber.getText().toString()));
        }));
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}