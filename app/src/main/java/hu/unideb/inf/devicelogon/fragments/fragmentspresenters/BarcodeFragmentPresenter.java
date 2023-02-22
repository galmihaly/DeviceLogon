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
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPinCodeFragmentView;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IPincodeFragmentPresenter;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class BarcodeFragmentPresenter implements IBarcodeFragmentPresenter, PresenterThreadCallback {

    private IBarcodeFragmentView iBarcodeFragmentView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private BarcodeFragmentHandler barcodeFragmentHandler;
    private WindowSizeClass[] wsc;

    public BarcodeFragmentPresenter(IBarcodeFragmentView iBarcodeFragmentView, Context context, WindowSizeClass[] wsc) {
        this.iBarcodeFragmentView = iBarcodeFragmentView;
        this.context = context;
        this.wsc = wsc;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            barcodeFragmentHandler = new BarcodeFragmentHandler(Looper.myLooper(), this);
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
        iBarcodeFragmentView.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(barcodeFragmentHandler == null) return;
        barcodeFragmentHandler.sendMessage(message);
    }


    private static class BarcodeFragmentHandler extends Handler {

        private WeakReference<IBarcodeFragmentPresenter> iBarcodeFragmentPresenterWeakReference;

        public BarcodeFragmentHandler(Looper looper, IBarcodeFragmentPresenter iBarcodeFragmentPresenter) {
            super(looper);
            this.iBarcodeFragmentPresenterWeakReference = new WeakReference<>(iBarcodeFragmentPresenter);
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
