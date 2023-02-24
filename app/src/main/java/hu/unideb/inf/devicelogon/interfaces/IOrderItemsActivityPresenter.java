package hu.unideb.inf.devicelogon.interfaces;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;

public interface IOrderItemsActivityPresenter {
    void initTaskManager();
    void initOrderItemsView();
    void createOrderItemsViewAdapter();
    void sendAdapterToView(OrderItemsViewHolderAdapter adapter);
}
