package hu.unideb.inf.devicelogon.utils;

import android.os.Bundle;
import android.os.Message;
import android.view.WindowManager;

import hu.unideb.inf.devicelogon.StartActivityView;

public class Util {

    public static final String MESSAGE_BODY = "MESSAGE_BODY";

    //Buttons created enums
    public static final int BUTTONS_IS_CREATED = 1;
    public static final int BUTTONS_NOT_CREATED = 2;

    //Buttons IDs
    public static final int BUTTON_USER_PASS = 10;
    public static final int BUTTON_PINCODE = 11;
    public static final int BUTTON_RFID = 12;
    public static final int BUTTON_BARCODE = 13;

    public static Message createMessage(int id, String dataString) {
        Bundle bundle = new Bundle();
        bundle.putString(Util.MESSAGE_BODY, dataString);
        Message message = new Message();
        message.what = id;
        message.setData(bundle);

        return message;
    }


}
