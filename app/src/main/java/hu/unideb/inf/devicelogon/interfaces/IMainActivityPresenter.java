package hu.unideb.inf.devicelogon.interfaces;

import android.os.Message;
import android.widget.ImageButton;

public interface IMainActivityPresenter {
    void initTaskManager();
    void initButtonsByLoginModesNumber(int modesNumber);
    void sendButtonsListToPresenter(ImageButton imageButton);
}
