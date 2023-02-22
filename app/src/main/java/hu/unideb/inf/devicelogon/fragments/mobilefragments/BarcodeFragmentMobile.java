package hu.unideb.inf.devicelogon.fragments.mobilefragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IBarcodeFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.BarcodeFragmentPresenter;
import hu.unideb.inf.devicelogon.utils.Util;


public class BarcodeFragmentMobile extends BaseFragment implements IBarcodeFragmentView {

    private View view;
    private Button button;
    private BarcodeFragmentPresenter barcodeFragmentPresenter;
    private WindowSizeClass[] wsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.fragment_barcode_mobile_portrait, container, false);
            button = view.findViewById(R.id.loginButton4_mobile_portrait);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            view = inflater.inflate(R.layout.fragment_barcode_mobile_landscape, container, false);
            button = view.findViewById(R.id.loginButton4_mobile_landscape);
        }


        barcodeFragmentPresenter = new BarcodeFragmentPresenter(this, getContext(), );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(button != null){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}