package hu.unideb.inf.devicelogon.fragments.mobilefragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public class PinCodeFragmentMobile extends BaseFragment {

    private View view;

    public static PinCodeFragmentMobile newInstance(){

        PinCodeFragmentMobile rfidFragment = new PinCodeFragmentMobile();
        return rfidFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pin_code_mobile_portrait, container, false);

        return view;
    }
}