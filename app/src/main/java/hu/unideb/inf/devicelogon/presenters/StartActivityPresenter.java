package hu.unideb.inf.devicelogon.presenters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.MainActivityView;
import hu.unideb.inf.devicelogon.interfaces.IStartActivityPresenter;
import hu.unideb.inf.devicelogon.interfaces.IStartActivityView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class StartActivityPresenter implements IStartActivityPresenter, PresenterThreadCallback {

    private IStartActivityView iStartActivityView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private StartActivityHandler mMainActivityHandler;

    public StartActivityPresenter(IStartActivityView iStartActivityView, Context context) {
        this.iStartActivityView = iStartActivityView;
        this.context = context;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            mMainActivityHandler = new StartActivityHandler(Looper.myLooper(), this);
            mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance();
            mCustomThreadPoolManager.setPresenterCallback(this);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása befejeződött.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void createLoginActivityIntentByModesNumber(int modesNumber) {
        Intent intent = new Intent(context.getApplicationContext(), MainActivityView.class);
        intent.putExtra("ModesNumber", modesNumber);
        iStartActivityView.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(mMainActivityHandler == null) return;
        mMainActivityHandler.sendMessage(message);
    }

    private static class StartActivityHandler extends Handler {


        private WeakReference<IStartActivityPresenter> iMainActivityPresenterWeakReference;

        public StartActivityHandler(Looper looper, IStartActivityPresenter iStartActivityPresenter) {
            super(looper);
            this.iMainActivityPresenterWeakReference = new WeakReference<>(iStartActivityPresenter);
        }

        // Ui-ra szánt üzenetet kezelejük itt
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
            }
        }
    }
}
