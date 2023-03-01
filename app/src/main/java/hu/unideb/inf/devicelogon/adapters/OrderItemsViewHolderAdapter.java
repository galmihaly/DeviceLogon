package hu.unideb.inf.devicelogon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.DetailFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IDetailFragment;
import hu.unideb.inf.devicelogon.listeners.OrderItemButtonListener;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;

public class OrderItemsViewHolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private WindowSizeClass[] wsc;
    private List<OrderItem> orderItemList;
    private OrderItem viewOrderItem;

    private int selectedPosition = -1;

    private IDetailFragment.IOnItemClick iOnItemClick;

    public OrderItemsViewHolderAdapter(Context context, WindowSizeClass[] wsc, List<OrderItem> orderItemList, IDetailFragment.IOnItemClick iOnItemClick) {
        this.context = context;
        this.wsc = wsc;
        this.orderItemList = orderItemList;
        this.iOnItemClick = iOnItemClick;
    }

    public Context getContext() {
        return context.getApplicationContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;

        if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.COMPACT){
            switch (viewType){
                case OrderItem.ORDERITEM_BASE:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_base, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderBase(v);

                }
                case OrderItem.ORDERITEM_SELECTED:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_selected, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderSelected(v);
                }
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.EXPANDED){
            switch (viewType){
                case OrderItem.ORDERITEM_BASE:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_base, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderBase(v);

                }
                case OrderItem.ORDERITEM_SELECTED:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_selected_tablet_landscape, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderSelected(v);
                }
            }
        }
        else if(wsc[0] == WindowSizeClass.EXPANDED && wsc[1] == WindowSizeClass.MEDIUM){
            switch (viewType){
                case OrderItem.ORDERITEM_BASE:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_base, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderBase(v);

                }
                case OrderItem.ORDERITEM_SELECTED:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_selected, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderSelected(v);
                }
            }
        }
        else if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
            switch (viewType){
                case OrderItem.ORDERITEM_BASE:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_base, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderBase(v);

                }
                case OrderItem.ORDERITEM_SELECTED_TABLAND:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_selected_tablet_landscape, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderTabletSelected(v);
                }
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.COMPACT){

            switch (viewType){
                case OrderItem.ORDERITEM_BASE:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_base, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderBase(v);
                }
                case OrderItem.ORDERITEM_SELECTED:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout_selected, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolderSelected(v);
                }
            }
        }

        return null;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            OrderItem orderItem = orderItemList.get(position);

            if(position != RecyclerView.NO_POSITION){
                switch (orderItem.getType()){
                    case OrderItem.ORDERITEM_BASE:{
                        ((OrderItemsViewHolderBase)holder).getTextView().setText(orderItem.getText1());

                        ((OrderItemsViewHolderBase)holder).cl.setOnClickListener(view -> {
                            if (position == selectedPosition) {
                                return;
                            }
                            int currentSelected = selectedPosition;
                            selectedPosition = position;

                            if (currentSelected != -1) {
                                orderItemList.get(currentSelected).setType(OrderItem.ORDERITEM_BASE);
                                notifyItemChanged(currentSelected);
                            }

                            if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
                                orderItem.setType(OrderItem.ORDERITEM_SELECTED_TABLAND);
                            }
                            else{
                                orderItem.setType(OrderItem.ORDERITEM_SELECTED);
                            }

                            notifyItemChanged(selectedPosition);
                        });

                        break;
                    }
                    case OrderItem.ORDERITEM_SELECTED:{
                        ((OrderItemsViewHolderSelected)holder).getTextView().setText(orderItem.getText1());
                        ((OrderItemsViewHolderSelected)holder).getTextView2().setText(orderItem.getText2());
                        viewOrderItem = orderItem;

                        ((OrderItemsViewHolderSelected)holder).initButtonListener();
                        break;
                    }
                    case OrderItem.ORDERITEM_SELECTED_TABLAND:{
                        ((OrderItemsViewHolderTabletSelected)holder).getTextView().setText(orderItem.getText1());
                        viewOrderItem = orderItem;

                        Bundle bundle = new Bundle();
                        bundle.putString("eredmeny", ((OrderItemsViewHolderTabletSelected)holder).getTextView().getText().toString());
                        bundle.putSerializable("windowHeightEnum", wsc[0]);
                        bundle.putSerializable("windowWidthEnum", wsc[1]);

                        DetailFragment detailFragment = new DetailFragment();
                        detailFragment.setArguments(bundle);

                        iOnItemClick.sendFragmentToActivity(detailFragment);
                        break;
                    }
                }
            }
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.ERROR, e.getMessage());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshAdapter(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        switch (orderItemList.get(position).getType()){
            case 0:{
                return OrderItem.ORDERITEM_BASE;
            }
            case 1:{
                return OrderItem.ORDERITEM_SELECTED;
            }
            case 2:{
                return OrderItem.ORDERITEM_SELECTED_TABLAND;
            }
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public OrderItem getOrderItem(){
        return viewOrderItem;
    }

    public class OrderItemsViewHolderBase extends RecyclerView.ViewHolder{

        private TextView textView;
        private ConstraintLayout cl;

        public OrderItemsViewHolderBase(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.orderItem1_base);
            this.cl = itemView.findViewById(R.id.cl_base);

        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    public class OrderItemsViewHolderSelected extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView textView2;
        private ConstraintLayout cl2;
        private Context con;

        private ImageButton orderItem2But;
        private OrderItemButtonListener orderItemButtonListener;

        public OrderItemsViewHolderSelected(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.orderItem1_selected);
            textView2 = itemView.findViewById(R.id.orderItem2_selected);
            cl2 = itemView.findViewById(R.id.cl3);
            orderItem2But = itemView.findViewById(R.id.orderItem2viewButton_selected);
        }

        public void initButtonListener(){

            con = getContext();

            orderItemButtonListener = OrderItemButtonListener.getInstance();
            orderItemButtonListener.setOrderItem(getOrderItem());
            orderItemButtonListener.setContext(con);

            if(orderItem2But != null){
                orderItem2But.setOnClickListener(orderItemButtonListener);
            }
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextView2() { return textView2; }
    }

    public class OrderItemsViewHolderTabletSelected extends RecyclerView.ViewHolder{

        private TextView textView;
        private ConstraintLayout cl2;
        private ConstraintLayout detailCL;

        public OrderItemsViewHolderTabletSelected(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.orderItem1_selected_tablet_landscape);
            cl2 = itemView.findViewById(R.id.cl3_selected_tablet_landscape);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
