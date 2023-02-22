package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;
import hu.unideb.inf.devicelogon.adapters.OrderItemsViewHolderAdapter;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.utils.Util;

public class OrderItemsActivityView extends AppCompatActivity {

    private WindowSizeClass[] windowSizeClasses;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        orderItemArrayList.add(new OrderItem("kenyér", "héja"));

        OrderItemsViewHolderAdapter orderItemAdapter = new OrderItemsViewHolderAdapter(this, windowSizeClasses, orderItemArrayList);

        if(recyclerView != null){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderItemAdapter);
        }
    }

    private void initView(){

        windowSizeClasses = Util.computeWindowSizeClasses(this);
        int orientation = this.getResources().getConfiguration().orientation;

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_orderitems_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                recyclerView = findViewById(R.id.orderItemsList_mobile_portrait);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_orderitems_mobile_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                recyclerView = findViewById(R.id.orderItemsList_mobile_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_orderitems_tablet_landscape);
                setTheme(R.style.DeviceLogon_landscape);
                recyclerView = findViewById(R.id.orderItemsList_tablet_landscape);
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.activity_orderitems_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);
            recyclerView = findViewById(R.id.orderItemsList_pda_portrait);

            Util.hideNavigationBar(this);
            Util.hideActionBar(this);
        }
    }
}