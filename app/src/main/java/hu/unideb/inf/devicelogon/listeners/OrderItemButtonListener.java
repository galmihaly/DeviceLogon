package hu.unideb.inf.devicelogon.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import hu.unideb.inf.devicelogon.WorkFlowActivityView;
import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;

public class OrderItemButtonListener implements View.OnClickListener {

    //később esetlegesen egy WORKFLOW type változó is szükséges lehet attól függően, hogy a kiválasztott Wokflow milyen típusúak és hogy azerint milyen felületet is kelljen megjeleníteni

    private Context context;
    private OrderItem orderItem;

    private static OrderItemButtonListener mInstance;

    public static OrderItemButtonListener getInstance(){
        if(mInstance == null){
            mInstance = new OrderItemButtonListener();
        }
        return mInstance;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(context.getApplicationContext(), WorkFlowActivityView.class);
        i.putExtra("orderItemType", orderItem.getType());
        i.putExtra("orderItemText1", orderItem.getText1());
        i.putExtra("orderItemText2", orderItem.getText2());
        context.startActivity(i);
    }
}
