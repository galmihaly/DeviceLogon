package hu.unideb.inf.devicelogon.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.interfaces.IStartActivityPresenter;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;
import hu.unideb.inf.devicelogon.tasksmanager.Util;

public class StartActivityPresenter implements IStartActivityPresenter, PresenterThreadCallback {

    private IStartActivityPresenter iStartActivityPresenter;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private StartActivityHandler mMainActivityHandler;

    public StartActivityPresenter(IStartActivityPresenter iStartActivityPresenter, Context context) {
        this.iStartActivityPresenter = iStartActivityPresenter;
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
