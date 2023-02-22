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
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPinCodeFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.BarcodeFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.PincodeFragmentPresenter;

public class PinCodeFragmentPDA extends BaseFragment implements IPinCodeFragmentView {

    private View view;
    private Button button;
    private PincodeFragmentPresenter pincodeFragmentPresenter;
    private WindowSizeClass[] wsc = new WindowSizeClass[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pincode_pda_portrait, container, false);

        button = view.findViewById(R.id.loginButton3_pda_portrait);
        pincodeFragmentPresenter = new PincodeFragmentPresenter(this, getContext(), wsc);

        button.setOnClickListener(view -> {
            pincodeFragmentPresenter.addFragment();
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(button != null){
            button.setOnClickListener(view -> {
                pincodeFragmentPresenter.addFragment();
            });
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}
