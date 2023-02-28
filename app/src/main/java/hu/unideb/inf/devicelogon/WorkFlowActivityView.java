package hu.unideb.inf.devicelogon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.OrderItemViewType;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.utils.Util;

public class WorkFlowActivityView extends AppCompatActivity {

    private TextView orderItemTV;
    private WindowSizeClass[] wsc;

    private OrderItem orderItem;
    private int orderItemType;
    private String orderItemText1;
    private String orderItemText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        Intent intent = getIntent();
        orderItemType = intent.getIntExtra("orderItemType", -1);
        orderItemText1 = intent.getStringExtra("orderItemText1");
        orderItemText2 = intent.getStringExtra("orderItemText2");

        orderItem = new OrderItem(orderItemType, orderItemText1, orderItemText2);

        if(orderItemTV != null) orderItemTV.setText(orderItem.getText1());
    }

    private void initView(){

        wsc = Util.computeWindowSizeClasses(this);
        int orientation = this.getResources().getConfiguration().orientation;

        if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                setContentView(R.layout.activity_workflow_mobile_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                orderItemTV = findViewById(R.id.orderItemTextView_mobile_portrait);
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_workflow_mobile_landscape);
                setTheme(R.style.DeviceLogon_portrait);
                orderItemTV = findViewById(R.id.orderItemTextView_mobile_landscape);
            }
        }
        else if(wsc[0] == WindowSizeClass.EXPANDED && wsc[1] == WindowSizeClass.MEDIUM){
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {

                setContentView(R.layout.activity_workflow_tablet_portrait);
                setTheme(R.style.DeviceLogon_portrait);
                orderItemTV = findViewById(R.id.orderItemTextView_tablet_portrait);
            }
        }
        else if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                setContentView(R.layout.activity_workflow_tablet_landscape);
                setTheme(R.style.DeviceLogon_portrait);
                orderItemTV = findViewById(R.id.orderItemTextView_tablet_landscape);
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.COMPACT){

            setContentView(R.layout.activity_workflow_pda_portrait);
            setTheme(R.style.DeviceLogon_portrait);
            orderItemTV = findViewById(R.id.orderItemTextView_pda_portrait);

            Util.hideNavigationBar(this);
            Util.hideActionBar(this);
        }
    }
}