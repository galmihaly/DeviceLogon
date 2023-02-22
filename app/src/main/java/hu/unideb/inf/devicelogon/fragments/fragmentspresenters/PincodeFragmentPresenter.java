package hu.unideb.inf.devicelogon.fragments.fragmentspresenters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.OrderItemsActivityView;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPincodeFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPinCodeFragmentView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class PincodeFragmentPresenter implements IPincodeFragmentPresenter, PresenterThreadCallback {

    private IPinCodeFragmentView iPinCodeFragmentView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private PincodeFragmentHandler pincodeFragmentHandler;
    private WindowSizeClass[] wsc;

    public PincodeFragmentPresenter(IPinCodeFragmentView iPinCodeFragmentView, Context context, WindowSizeClass[] wsc) {
        this.iPinCodeFragmentView = iPinCodeFragmentView;
        this.context = context;
        this.wsc = wsc;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            pincodeFragmentHandler = new PincodeFragmentHandler(Looper.myLooper(), this);
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
        iPinCodeFragmentView.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(pincodeFragmentHandler == null) return;
        pincodeFragmentHandler.sendMessage(message);
    }


    private static class PincodeFragmentHandler extends Handler {

        private WeakReference<IPincodeFragmentPresenter> iLoginFragmentsPresenterWeakReference;

        public PincodeFragmentHandler(Looper looper, IPincodeFragmentPresenter iPincodeFragmentPresenter) {
            super(looper);
            this.iLoginFragmentsPresenterWeakReference = new WeakReference<>(iPincodeFragmentPresenter);
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
