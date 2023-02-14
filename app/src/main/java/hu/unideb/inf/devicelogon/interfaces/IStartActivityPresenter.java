package hu.unideb.inf.devicelogon.interfaces;

import android.content.Intent;

public interface IStartActivityPresenter {
    void initTaskManager();
    void createLoginActivityIntentByModesNumber(int modesNumber);
}
