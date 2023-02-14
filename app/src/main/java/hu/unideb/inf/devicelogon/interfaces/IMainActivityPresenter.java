package hu.unideb.inf.devicelogon.interfaces;

import android.os.Message;
import android.widget.ImageButton;

import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public interface IMainActivityPresenter {
    void initTaskManager();
    void initButtonsByLoginModesNumber(int modesNumber);
    void sendButtonToPresenter(ImageButton imageButton);
    void addFragment(BaseFragment baseFragment);
}
