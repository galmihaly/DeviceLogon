package hu.unideb.inf.devicelogon.tasks;

import android.content.Context;
import android.os.Message;

import org.intellij.lang.annotations.Identifier;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IDetailFragment;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.utils.Util;

public class CreateOrderItemList implements Runnable {

    private WeakReference<CustomThreadPoolManager> ctpmw;

    private Context context;
    private WindowSizeClass[] wsc;
    private IDetailFragment.IOnItemClick iOnItemClick;

    private ArrayList<OrderItem> orderItemArrayList;
    private OrderItemsViewHolderAdapter orderItemAdapter;
    private Message message;

    public CreateOrderItemList(Context context, WindowSizeClass[] wsc, IDetailFragment.IOnItemClick iOnItemClick) {
        this.context = context;
        this.wsc = wsc;
        this.iOnItemClick = iOnItemClick;
    }

    public void setCustomThreadPoolManager(CustomThreadPoolManager customThreadPoolManager) {
        this.ctpmw = new WeakReference<>(customThreadPoolManager);
    }

    @Override
    public void run() {

        try{
            orderItemArrayList = new ArrayList<>();
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE, "cím1", "alcím1"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím2", "alcím2"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím3", "alcím3"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím4", "alcím4"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím5", "alcím5"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím6", "alcím6"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím7", "alcím7"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím8", "alcím8"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím9", "alcím9"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím10", "alcím10"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím11", "alcím11"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím12", "alcím12"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím13", "alcím13"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím14", "alcím14"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím15", "alcím15"));
            orderItemArrayList.add(new OrderItem(OrderItem.ORDERITEM_BASE,"cím16", "alcím16"));

            orderItemAdapter = new OrderItemsViewHolderAdapter(context, wsc, orderItemArrayList, iOnItemClick);

            message = Util.createMessage(Util.ADAPTER_CREATED_SUCCES, "Az Adapterhez szükséges elemek elkészültek!");
            message.obj = orderItemAdapter;

            if(ctpmw != null && ctpmw.get() != null && message != null) {
                ctpmw.get().sendResultToPresenter(message);
            }

        }catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }
}
