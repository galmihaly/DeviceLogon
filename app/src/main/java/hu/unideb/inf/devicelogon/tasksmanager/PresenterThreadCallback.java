package hu.unideb.inf.devicelogon.tasksmanager;

import android.os.Message;

public interface PresenterThreadCallback {
    void sendResultToPresenter(Message message);
}
