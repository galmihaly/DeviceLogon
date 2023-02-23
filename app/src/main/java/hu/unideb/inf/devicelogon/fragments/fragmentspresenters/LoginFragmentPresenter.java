package hu.unideb.inf.devicelogon.fragments.fragmentspresenters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.OrderItemsActivityView;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.ILoginFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.ILoginFragmentPresenter;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class LoginFragmentPresenter implements ILoginFragmentPresenter, PresenterThreadCallback {

    private ILoginFragment iLoginFragment;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private LoginFragmentHandler loginFragmentHandler;
    private WindowSizeClass[] wsc;

    public LoginFragmentPresenter(ILoginFragment iLoginFragment, Context context, WindowSizeClass[] wsc) {
        this.iLoginFragment = iLoginFragment;
        this.context = context;
        this.wsc = wsc;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            loginFragmentHandler = new LoginFragmentHandler(Looper.myLooper(), this);
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
        iLoginFragment.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(loginFragmentHandler == null) return;
        loginFragmentHandler.sendMessage(message);
    }

    private static class LoginFragmentHandler extends Handler {

        private WeakReference<ILoginFragmentPresenter> iLoginFragmentPresenterWeakReference;

        public LoginFragmentHandler(Looper looper, ILoginFragmentPresenter iLoginFragmentPresenter) {
            super(looper);
            this.iLoginFragmentPresenterWeakReference = new WeakReference<>(iLoginFragmentPresenter);
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
