package hu.unideb.inf.devicelogon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.adapters.models.OrderItem;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.utils.Util;

public class OrderItemsViewHolderAdapter extends RecyclerView.Adapter {

    private Context context;
    private WindowSizeClass[] windowSizeClasses;
    private List<OrderItem> orderItemList;

    public OrderItemsViewHolderAdapter(Context context, WindowSizeClass[] windowSizeClasses, List<OrderItem> orderItemList) {
        this.context = context;
        this.windowSizeClasses = windowSizeClasses;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem_layout, parent, false);

        return new OrderItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);

        if(windowSizeClasses[0] == WindowSizeClass.COMPACT && windowSizeClasses[1] == WindowSizeClass.COMPACT){

            ((OrderItemsViewHolder) holder).textView.setText(orderItem.getText1());
        }
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    private static class OrderItemsViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textView = itemView.findViewById(R.id.orderItem);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
