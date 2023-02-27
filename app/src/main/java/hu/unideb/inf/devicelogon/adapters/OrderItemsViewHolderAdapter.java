package hu.unideb.inf.devicelogon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.listeners.OrderItemButtonListener;

public class OrderItemsViewHolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private WindowSizeClass[] windowSizeClasses;
    private List<OrderItem> orderItemList;
    private OrderItem viewOrderItem;

    private int selectedPosition = -1;

    public OrderItemsViewHolderAdapter(Context context, WindowSizeClass[] windowSizeClasses, List<OrderItem> orderItemList) {
        this.context = context;
        this.windowSizeClasses = windowSizeClasses;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;

        if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.COMPACT){
            switch (viewType){
                case OrderItem.TYPE1:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder(v);

                }
                case OrderItem.TYPE2:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout2, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder2(v);
                }
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            switch (viewType){
                case OrderItem.TYPE1:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder(v);

                }
                case OrderItem.TYPE2:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout2, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder2(v);
                }
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.MEDIUM && windowSizeClasses[1] == WindowSizeClass.EXPANDED){
            switch (viewType){
                case OrderItem.TYPE1:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder(v);

                }
                case OrderItem.TYPE2:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout2, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder2(v);
                }
            }
        }
        else if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            switch (viewType){
                case OrderItem.TYPE1:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder(v);
                }
                case OrderItem.TYPE2:{
                    v = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.orderitem_layout2, parent, false);

                    if(v == null) return null;
                    return new OrderItemsViewHolder2(v);
                }
            }
        }

        return null;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);

        if(position != RecyclerView.NO_POSITION){
            switch (orderItem.getType()){
                case OrderItem.TYPE1:{
                    ((OrderItemsViewHolder)holder).getTextView().setText(orderItem.getText1());

                    ((OrderItemsViewHolder)holder).cl.setOnClickListener(view -> {
                        if (position == selectedPosition) {
                            return;
                        }
                        int currentSelected = selectedPosition;
                        selectedPosition = position;

                        if (currentSelected != -1) {
                            orderItemList.get(currentSelected).setType(OrderItem.TYPE1);
                            notifyItemChanged(currentSelected);
                        }
                        orderItem.setType(OrderItem.TYPE2);
                        notifyItemChanged(selectedPosition);
                    });

                    break;
                }
                case OrderItem.TYPE2:{
                    ((OrderItemsViewHolder2)holder).getTextView().setText(orderItem.getText1());
                    ((OrderItemsViewHolder2)holder).getTextView2().setText(orderItem.getText2());
                    viewOrderItem = orderItem;
                    ((OrderItemsViewHolder2)holder).initButtonListener();
                    break;
                }
            }
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
                return OrderItem.TYPE1;
            }
            case 1:{
                return OrderItem.TYPE2;
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

    public class OrderItemsViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ConstraintLayout cl;

        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.orderItem1);
            cl = itemView.findViewById(R.id.cl);

        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    public class OrderItemsViewHolder2 extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView textView2;
        private ConstraintLayout cl2;

        private ImageButton orderItem2But;
        private OrderItemButtonListener orderItemButtonListener;

        public OrderItemsViewHolder2(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.orderItem1);
            this.textView2 = itemView.findViewById(R.id.orderItem2);
            cl2 = itemView.findViewById(R.id.cl3);
            orderItem2But = itemView.findViewById(R.id.orderItem2viewButton);
        }

        public void initButtonListener(){
            orderItemButtonListener = OrderItemButtonListener.getInstance();
            orderItemButtonListener.setOrderItem(getOrderItem());
            orderItemButtonListener.setContext(context.getApplicationContext());
            orderItem2But.setOnClickListener(orderItemButtonListener);
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTextView2() { return textView2; }
    }
}
