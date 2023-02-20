package hu.unideb.inf.devicelogon.interfaces;

import android.content.Intent;
import android.widget.ImageButton;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public interface IMainActivityView {
    void loadOtherActivityPages(Intent intent);
    void loadOtherActivityFragment(FragmentTypes fragmentTypes, BaseFragment baseFragment);
    void setButtonToLayout(ImageButton imageButton);
    void getButtonsListSize(int integer);
}
