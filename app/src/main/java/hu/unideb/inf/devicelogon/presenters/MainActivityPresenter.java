package hu.unideb.inf.devicelogon.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.interfaces.IMainActivityPresenter;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;
import hu.unideb.inf.devicelogon.tasksmanager.Util;

public class MainActivityPresenter implements IMainActivityPresenter, PresenterThreadCallback {

    private IMainActivityPresenter iMainActivityPresenter;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private MainActivityHandler mMainActivityHandler;

    public MainActivityPresenter(IMainActivityPresenter iMainActivityPresenter, Context context) {
        this.iMainActivityPresenter = iMainActivityPresenter;
        this.context = context;
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása megkezdődött.");

            mMainActivityHandler = new MainActivityHandler(Looper.myLooper(), this);
            mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance();
            mCustomThreadPoolManager.setPresenterCallback(this);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezelő létrehozása befejeződött.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(mMainActivityHandler == null) return;
        mMainActivityHandler.sendMessage(message);
    }

    private static class MainActivityHandler extends Handler {


        private WeakReference<IMainActivityPresenter> iMainActivityPresenterWeakReference;

        public MainActivityHandler(Looper looper, IMainActivityPresenter iMainActivityPresenter) {
            super(looper);
            this.iMainActivityPresenterWeakReference = new WeakReference<>(iMainActivityPresenter);
        }

        // Ui-ra szánt üzenetet kezelejük itt
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Util.PROGRAMSTART_FINISH_1:{

                    break;
                }
                case Util.PROGRAMSTART_FINISH_2:{

                    break;
                }
                case Util.PROGRAMSATRT_FINISH_3:{

                    break;
                }
                case Util.ROOM_SEND_FAIL:{
                    break;
                }
                case Util.ROOM_CREATE_FAIL:{
                    break;
                }
                default:
                    break;
            }
        }
    }
}
