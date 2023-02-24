package hu.unideb.inf.devicelogon.tasks;

import android.content.Context;
import android.os.Message;

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
        this.context = context;
        this.wsc = wsc;
    }

    public void setCustomThreadPoolManager(CustomThreadPoolManager customThreadPoolManager) {
        this.ctpmw = new WeakReference<>(customThreadPoolManager);
    }

    @Override
    public void run() {

        try{
            orderItemArrayList = new ArrayList<>();
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1, "szöveg1", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg2", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg3", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg4", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg5", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg6", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg7", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg8", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg9", "héja"));
            orderItemArrayList.add(new OrderItem(OrderItem.TYPE1,"szöveg10", "héja"));

            orderItemAdapter = new OrderItemsViewHolderAdapter(context.getApplicationContext(), wsc, orderItemArrayList);

            message = Util.createMessage(Util.ADAPTER_CREATED_SUCCES, "Az OrderItemsList adapter elkészült!");
            message.obj = orderItemAdapter;

            if(ctpmw != null && ctpmw.get() != null && message != null) {
                ctpmw.get().sendResultToPresenter(message);
            }

        }catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }
    }
}
