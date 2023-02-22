package hu.unideb.inf.devicelogon.fragments.fragmentspresenters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.OrderItemsActivityView;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IBarcodeFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IBarcodeFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IUserPassFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IUserPassFragmentView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class UserPassFragmentPresenter implements IUserPassFragmentPresenter, PresenterThreadCallback {

    private IUserPassFragmentView iUserPassFragmentView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private UserPassFragmentHandler userPassFragmentHandler;
    private WindowSizeClass[] wsc;

    public UserPassFragmentPresenter(IUserPassFragmentView iUserPassFragmentView, Context context, WindowSizeClass[] wsc) {
        this.iUserPassFragmentView = iUserPassFragmentView;
        this.context = context;
        this.wsc = wsc;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            userPassFragmentHandler = new UserPassFragmentHandler(Looper.myLooper(), this);
            mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance();
            mCustomThreadPoolManager.setPresenterCallback(this);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása befejeződött.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void addFragment() {
        Intent intent= new Intent(context.getApplicationContext(), OrderItemsActivityView.class);
        iUserPassFragmentView.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(userPassFragmentHandler == null) return;
        userPassFragmentHandler.sendMessage(message);
    }


    private static class UserPassFragmentHandler extends Handler {

        private WeakReference<IUserPassFragmentPresenter> iUserPassFragmentPresenterWeakReference;

        public UserPassFragmentHandler(Looper looper, IUserPassFragmentPresenter iUserPassFragmentPresenter) {
            super(looper);
            this.iUserPassFragmentPresenterWeakReference = new WeakReference<>(iUserPassFragmentPresenter);
        }

        // Ui-ra szánt üzenetet kezelejük itt
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

            }
        }
    }
}
