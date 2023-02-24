package hu.unideb.inf.devicelogon.interfaces;

import android.content.Intent;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;

public interface IOrderItemsActivityView {
    void loadOtherActivityPages(Intent intent);
    void bindAdapterToView(OrderItemsViewHolderAdapter adapter);
}
