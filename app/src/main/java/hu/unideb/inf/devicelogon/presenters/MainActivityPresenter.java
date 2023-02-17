package hu.unideb.inf.devicelogon.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.fragments.mobilefragments.RFIDFragmentMobile;
import hu.unideb.inf.devicelogon.fragments.mobilefragments.UserPassFragmentMobile;
import hu.unideb.inf.devicelogon.fragments.pdafragments.UserPassFragmentPDA;
import hu.unideb.inf.devicelogon.interfaces.IFragmentNavigationPresenter;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityPresenter;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasks.CreateLoginButtons;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;
import hu.unideb.inf.devicelogon.utils.Util;

public class MainActivityPresenter implements IMainActivityPresenter, PresenterThreadCallback, IFragmentNavigationPresenter {

    private IMainActivityView iMainActivityView;
    private Context context;
    private CustomThreadPoolManager mCustomThreadPoolManager;
    private MainActivityHandler mMainActivityHandler;
    private WindowSizeClass[] windowSizeClasses;

    private List<Integer> typesOfLoginButton = new ArrayList<>();

    public MainActivityPresenter(IMainActivityView iMainActivityView, Context context, WindowSizeClass[] windowSizeClasses) {
        this.iMainActivityView = iMainActivityView;
        this.context = context;
        this.windowSizeClasses = windowSizeClasses;
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
    public void initButtonsByLoginModesNumber(int modesNumber) {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A bejelentkezési módok gombjainak létrehozása megkezdődött.");

            CreateLoginButtons callable = new CreateLoginButtons(context.getApplicationContext(), modesNumber);
            callable.setCustomThreadPoolManager(mCustomThreadPoolManager);
            mCustomThreadPoolManager.addCallableMethod(callable);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A bejelentkezési módok gombjainak létrehozása befejeződött.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void sendButtonToPresenter(ImageButton imageButton) {
        iMainActivityView.setButtonToLayout(imageButton);
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(mMainActivityHandler == null) return;
        mMainActivityHandler.sendMessage(message);
    }

    @Override
    public void addFragmentByEnum(FragmentTypes fragmentTypes) {

        switch (fragmentTypes){
            case USERPASSFRAGMENT:{
                if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
                    iMainActivityView.loadOtherActivityFragment(new UserPassFragmentMobile());
                }
                else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){
                    iMainActivityView.loadOtherActivityFragment(new UserPassFragmentPDA());
                }

                break;
            }
            case RFIDFRAGMENT:{
                if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
                    iMainActivityView.loadOtherActivityFragment(new RFIDFragmentMobile());
                }
                else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){
                    iMainActivityView.loadOtherActivityFragment(new UserPassFragmentPDA());
                }

                break;
            }
        }
    }

    @Override
    public void addFragment(BaseFragment baseFragment) {
        iMainActivityView.loadOtherActivityFragment(baseFragment);
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
                case Util.BUTTON_USER_PASS:{

                    if(msg.obj instanceof ImageButton){
                        ImageButton button = (ImageButton) msg.obj;
                        iMainActivityPresenterWeakReference.get().sendButtonToPresenter(button);
                        iMainActivityPresenterWeakReference.get().addFragmentByEnum(FragmentTypes.USERPASSFRAGMENT);
                    }

                    break;
                }
                case Util.BUTTON_BARCODE:{

                    if(msg.obj instanceof ImageButton){
                        ImageButton button = (ImageButton) msg.obj;
                        iMainActivityPresenterWeakReference.get().sendButtonToPresenter(button);
                        iMainActivityPresenterWeakReference.get().addFragmentByEnum(FragmentTypes.BARCODEFRAGMENT);
                    }

                    break;
                }
                case Util.BUTTON_PINCODE:{

                    if(msg.obj instanceof ImageButton){
                        ImageButton button = (ImageButton) msg.obj;
                        iMainActivityPresenterWeakReference.get().sendButtonToPresenter(button);
                        iMainActivityPresenterWeakReference.get().addFragmentByEnum(FragmentTypes.PINCODEFRAGMENT);
                    }

                    break;
                }
                case Util.BUTTON_RFID:{

                    if(msg.obj instanceof ImageButton){
                        ImageButton button = (ImageButton) msg.obj;
                        iMainActivityPresenterWeakReference.get().sendButtonToPresenter(button);
                        iMainActivityPresenterWeakReference.get().addFragmentByEnum(FragmentTypes.RFIDFRAGMENT);
                    }

                    break;
                }
            }
        }
    }
}
