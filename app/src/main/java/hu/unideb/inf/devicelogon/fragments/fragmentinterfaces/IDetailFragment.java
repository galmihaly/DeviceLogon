package hu.unideb.inf.devicelogon.fragments.fragmentinterfaces;

import android.content.Intent;

import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public interface IDetailFragment {
    void loadOtherActivityPages(Intent intent);

    public interface IOnItemClick{
        void sendFragmentToActivity(BaseFragment baseFragment);
    }
}
