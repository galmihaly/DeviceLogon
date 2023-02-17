package hu.unideb.inf.devicelogon.fragments.mobilefragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public class RFIDFragmentMobile extends BaseFragment {

    private View view;

    public static RFIDFragmentMobile newInstance(){

        RFIDFragmentMobile rfidFragmentMobile = new RFIDFragmentMobile();
        return rfidFragmentMobile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rfid_mobile_portrait, container, false);

        return view;
    }
}