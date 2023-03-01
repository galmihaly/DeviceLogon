package hu.unideb.inf.devicelogon.adapters.models;

import java.io.Serializable;

import hu.unideb.inf.devicelogon.enums.OrderItemViewType;

public class OrderItem implements Serializable {

    public static final int ORDERITEM_BASE = 0;
    public static final int ORDERITEM_SELECTED = 1;
    public static final int ORDERITEM_SELECTED_TABLAND = 2;

    private int type;
    private String text1;
    private String text2;

    public OrderItem(int type, String text1, String text2) {
        this.type = type;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
