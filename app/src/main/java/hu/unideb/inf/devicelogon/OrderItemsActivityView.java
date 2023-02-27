package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.interfaces.IOrderItemsActivityView;
import hu.unideb.inf.devicelogon.presenters.OrderItemsActivityPresenter;
import hu.unideb.inf.devicelogon.utils.Util;

public class OrderItemsActivityView extends AppCompatActivity implements IOrderItemsActivityView {

    private WindowSizeClass[] wsc;
    private RecyclerView recyclerView;
    private OrderItemsActivityPresenter orderItemsActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        orderItemsActivityPresenter = new OrderItemsActivityPresenter(this, getApplicationContext(), wsc);
    }

    @Override
    protected void onResume() {
        super.onResume();

        orderItemsActivityPresenter.initOrderItemsView();
        orderItemsActivityPresenter.refreshAdapter();
    }

    private void initView(){

        wsc = Util.computeWindowSizeClasses(this);
        int orientation = this.getResources().getConfiguration().orientation;

        if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_orderitems_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                recyclerView = findViewById(R.id.orderItemsList_mobile_portrait);
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_orderitems_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                recyclerView = findViewById(R.id.orderItemsList_mobile_landscape);
            }
        }
        else if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_orderitems_tablet_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                recyclerView = findViewById(R.id.orderItemsList_tablet_landscape);
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.activity_orderitems_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);
            recyclerView = findViewById(R.id.orderItemsList_pda_portrait);

            Util.hideNavigationBar(this);
            Util.hideActionBar(this);
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void bindAdapterToView(OrderItemsViewHolderAdapter adapter) {
        if(adapter == null) return;
        if(orderItemsActivityPresenter == null) return;

        if(recyclerView != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.refreshAdapter();
            recyclerView.setAdapter(adapter);
        }
    }
}