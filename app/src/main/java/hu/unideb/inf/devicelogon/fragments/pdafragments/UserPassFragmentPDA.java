package hu.unideb.inf.devicelogon.fragments.pdafragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IUserPassFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.BarcodeFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.UserPassFragmentPresenter;

public class UserPassFragmentPDA extends BaseFragment implements IUserPassFragmentView {

    private View view;
    private Button button;
    private UserPassFragmentPresenter userPassFragmentPresenter;
    private WindowSizeClass[] wsc = new WindowSizeClass[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_and_password_pda_portrait, container, false);

        button = view.findViewById(R.id.loginButton1_pda_portrait);
        userPassFragmentPresenter = new UserPassFragmentPresenter(this, getContext(), wsc);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(button != null){
            button.setOnClickListener(view -> {
                userPassFragmentPresenter.addFragment();
            });
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        if(intent == null) return;
        startActivity(intent);
    }
}