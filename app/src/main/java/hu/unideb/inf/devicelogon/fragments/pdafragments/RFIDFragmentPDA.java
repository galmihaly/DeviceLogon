package hu.unideb.inf.devicelogon.fragments.pdafragments;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IRFIDFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.BarcodeFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.RFIDFragmentPresenter;


public class RFIDFragmentPDA extends BaseFragment implements IRFIDFragmentView {

    private View view;
    private Button button;
    private RFIDFragmentPresenter rfidFragmentPresenter;
    private WindowSizeClass[] wsc = new WindowSizeClass[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rfid_pda_portrait, container, false);

        button = view.findViewById(R.id.loginButton2_pda_portrait);
        rfidFragmentPresenter = new RFIDFragmentPresenter(this, getContext(), wsc);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(button != null){
            button.setOnClickListener(view -> {
                rfidFragmentPresenter.addFragment();
            });
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}
