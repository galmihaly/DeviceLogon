package hu.unideb.inf.devicelogon.tasks;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.CreateOrderItemsResponseEnums;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.utils.Util;

public class CreateOrderItemList implements Runnable {

    private WeakReference<CustomThreadPoolManager> ctpmw;
    private Context context;
    private WindowSizeClass[] wsc;

    private ArrayList<OrderItem> orderItemArrayList;
    private OrderItemsViewHolderAdapter orderItemAdapter;
    private Message message;

    public CreateOrderItemList(Context context, WindowSizeClass[] wsc) {
        this.context = context.getApplicationContext();
        this.wsc = wsc;
    }

    public void setCustomThreadPoolManager(CustomThreadPoolManager customThreadPoolManager) {
        this.ctpmw = new WeakReference<>(customThreadPoolManager);
    }

    @Override
    public void run() {

        try{
            orderItemArrayList = new ArrayList<>();
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1, "cím1", "alcím1"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím2", "alcím2"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím3", "alcím3"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím4", "alcím4"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím5", "alcím5"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím6", "alcím6"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím7", "alcím7"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím8", "alcím8"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím9", "alcím9"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím10", "alcím10"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím11", "alcím11"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím12", "alcím12"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím13", "alcím13"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím14", "alcím14"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím15", "alcím15"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"cím16", "alcím16"));

            orderItemAdapter = new OrderItemsViewHolderAdapter(context, wsc, orderItemArrayList);

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
