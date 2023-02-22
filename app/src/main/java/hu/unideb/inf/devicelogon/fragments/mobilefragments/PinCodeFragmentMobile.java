package hu.unideb.inf.devicelogon.fragments.mobilefragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPinCodeFragmentView;

public class PinCodeFragmentMobile extends BaseFragment implements IPinCodeFragmentView {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = inflater.inflate(R.layout.fragment_pincode_mobile_portrait, container, false);
        }
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            view = inflater.inflate(R.layout.fragment_pincode_mobile_landscape, container, false);
        }

        return view;
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}