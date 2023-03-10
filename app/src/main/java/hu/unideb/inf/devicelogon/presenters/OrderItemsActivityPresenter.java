package hu.unideb.inf.devicelogon.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;
import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IDetailFragment;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityPresenter;
import hu.unideb.inf.devicelogon.interfaces.IMainActivityView;
import hu.unideb.inf.devicelogon.interfaces.IOrderItemsActivityPresenter;
import hu.unideb.inf.devicelogon.interfaces.IOrderItemsActivityView;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasks.CreateLoginButtons;
import hu.unideb.inf.devicelogon.tasks.CreateOrderItemList;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.tasksmanager.PresenterThreadCallback;
import hu.unideb.inf.devicelogon.utils.Util;

public class OrderItemsActivityPresenter implements IOrderItemsActivityPresenter, PresenterThreadCallback {

    private IOrderItemsActivityView iOrderItemsActivityView;
    private Context context;
    private WindowSizeClass[] wsc;
    private IDetailFragment.IOnItemClick iOnItemClick;

    private CustomThreadPoolManager mCustomThreadPoolManager;
    private OrderItemsActivityHandler mOrderItemsActivityHandler;


    private OrderItemsViewHolderAdapter orderItemsViewHolderAdapter;

    public OrderItemsActivityPresenter(IOrderItemsActivityView iOrderItemsActivityView, Context context, WindowSizeClass[] wsc, IDetailFragment.IOnItemClick iOnItemClick) {
        this.iOrderItemsActivityView = iOrderItemsActivityView;
        this.context = context;
        this.wsc = wsc;
        this.iOnItemClick = iOnItemClick;
    }

    @Override
    public void initOrderItemsView() {
        initTaskManager();
        createOrderItemsViewAdapter();
    }

    @Override
    public void initTaskManager() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezel?? l??trehoz??sa megkezd??d??tt.");

            mOrderItemsActivityHandler = new OrderItemsActivityHandler(Looper.myLooper(), this);
            mCustomThreadPoolManager = CustomThreadPoolManager.getsInstance();
            mCustomThreadPoolManager.setPresenterCallback(this);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A feladatkezel?? l??trehoz??sa befejez??d??tt.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void createOrderItemsViewAdapter() {
        try {
            ApplicationLogger.logging(LogLevel.INFORMATION, "A rendel??si lista adatait tartalmaz?? t??rol?? l??trehoz??sa megkezd??d??tt.");

            CreateOrderItemList runnable = new CreateOrderItemList(context, wsc, iOnItemClick);
            runnable.setCustomThreadPoolManager(mCustomThreadPoolManager);
            mCustomThreadPoolManager.addRunnableMethod(runnable);

            ApplicationLogger.logging(LogLevel.INFORMATION, "A rendel??si lista adatait tartalmaz?? t??rol?? l??trehoz??sa befejez??d??tt.");
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }

    @Override
    public void sendAdapterToView(OrderItemsViewHolderAdapter adapter) {
        orderItemsViewHolderAdapter = adapter;
        iOrderItemsActivityView.bindAdapterToView(adapter);
    }

    @Override
    public void refreshAdapter() {
        if(orderItemsViewHolderAdapter == null) return;
        orderItemsViewHolderAdapter.refreshAdapter();
    }

    @Override
    public void sendResultToPresenter(Message message) {
        if(mOrderItemsActivityHandler == null) return;
        mOrderItemsActivityHandler.sendMessage(message);
    }

    private static class OrderItemsActivityHandler extends Handler {

        private WeakReference<IOrderItemsActivityPresenter> iOrderItemsActivityPresenterWeakReference;

        public OrderItemsActivityHandler(Looper looper, IOrderItemsActivityPresenter iOrderItemsActivityPresenter) {
            super(looper);
            this.iOrderItemsActivityPresenterWeakReference = new WeakReference<>(iOrderItemsActivityPresenter);
        }

        // Ui-ra sz??nt ??zenetet kezelej??k itt
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Util.ADAPTER_CREATED_SUCCES:{
                    if(msg.obj instanceof OrderItemsViewHolderAdapter) {
                        iOrderItemsActivityPresenterWeakReference.get().sendAdapterToView((OrderItemsViewHolderAdapter) msg.obj);
                    }
                    break;
                }
                case Util.ADAPTER_CREATED_FAILED:{
                    Log.e("", "Nem siker??lt!!!");
                    break;
                }
            }
        }
    }
}
