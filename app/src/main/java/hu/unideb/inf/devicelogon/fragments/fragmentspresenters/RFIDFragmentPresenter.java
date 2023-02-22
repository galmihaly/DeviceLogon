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
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IRFIDFragmentPresenter;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IRFIDFragmentView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;

public class RFIDFragmentPresenter implements IRFIDFragmentPresenter, PresenterThreadCallback {

    private IRFIDFragmentView irfidFragmentView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private RFIDFragmentHandler rfidFragmentHandler;
    private WindowSizeClass[] wsc;

    public RFIDFragmentPresenter(IRFIDFragmentView irfidFragmentView, Context context, WindowSizeClass[] wsc) {
        this.irfidFragmentView = irfidFragmentView;
        this.context = context;
        this.wsc = wsc;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            rfidFragmentHandler = new RFIDFragmentHandler(Looper.myLooper(), this);
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
        irfidFragmentView.loadOtherActivityPages(intent);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(rfidFragmentHandler == null) return;
        rfidFragmentHandler.sendMessage(message);
    }


    private static class RFIDFragmentHandler extends Handler {

        private WeakReference<IRFIDFragmentPresenter> irfidFragmentPresenterWeakReference;

        public RFIDFragmentHandler(Looper looper, IRFIDFragmentPresenter irfidFragmentPresenter) {
            super(looper);
            this.irfidFragmentPresenterWeakReference = new WeakReference<>(irfidFragmentPresenter);
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
