package hu.unideb.inf.devicelogon.fragments.mobilefragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;


public class RFIDFragmentPDA extends BaseFragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rfid_pda_portrait, container, false);

        return view;
    }
}
