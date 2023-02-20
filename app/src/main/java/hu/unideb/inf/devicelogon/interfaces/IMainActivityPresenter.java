package hu.unideb.inf.devicelogon.interfaces;

import android.os.Message;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public interface IMainActivityPresenter {
    void initTaskManager();
    void initButtonsByLoginModesNumber(int modesNumber);
    void sendButtonToPresenter(ImageButton imageButton);
    void addFragmentByEnum(FragmentTypes fragmentTypes);
    void sendButtonsListSize(int buttonsListSize);
}
