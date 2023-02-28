package hu.unideb.inf.devicelogon.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.logger.ApplicationLogger;
import hu.unideb.inf.devicelogon.logger.LogLevel;
import hu.unideb.inf.devicelogon.tasksmanager.CustomThreadPoolManager;
import hu.unideb.inf.devicelogon.utils.Util;

public class CreateLoginButtons implements Callable {

    private WeakReference<CustomThreadPoolManager> ctpmw;
    private int modesNumber;
    private Context context;
    private List<Integer> typesOfLoginButton = new ArrayList<>();
    private WindowSizeClass[] wsc;

    //LoginButtons
    private ImageButton loginAccAndPassButton = null;
    private ImageButton loginPinButton = null;
    private ImageButton loginRFIDButton = null;
    private ImageButton loginBarcodeButton = null;

    //StyleParameters
    private int height;
    private int width;
    private int[] margins;
    private int imageUserPassId;
    private int imageRFIDId;
    private int imageBarcodeId;
    private int imagePincodeId;

    //Message
    private Message message;

    public CreateLoginButtons(Context context, int modeNumber, WindowSizeClass[] wsc) {
        this.modesNumber = modeNumber;
        this.context = context;
        this.wsc = wsc;
    }

    public void setCustomThreadPoolManager(CustomThreadPoolManager customThreadPoolManager) {
        this.ctpmw = new WeakReference<>(customThreadPoolManager);
    }

    @Override
    public Object call() {

        try{

            if(modesNumber == 1 || modesNumber == 2 || modesNumber == 4 || modesNumber == 8){
                typesOfLoginButton.add(modesNumber);
            }
            else if(modesNumber == 3){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(2);
            }
            else if(modesNumber == 5){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(4);
            }
            else if(modesNumber == 6){
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(4);
            }
            else if(modesNumber == 7){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(4);
            }
            else if(modesNumber == 9){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 10){
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 11){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 12){
                typesOfLoginButton.add(4);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 13){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(4);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 14){
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(4);
                typesOfLoginButton.add(8);
            }
            else if(modesNumber == 15){
                typesOfLoginButton.add(1);
                typesOfLoginButton.add(2);
                typesOfLoginButton.add(4);
                typesOfLoginButton.add(8);
            }

            message = Util.createMessage(Util.BUTTONS_LIST_SIZE, "A létrehozandó gombok száma!");
            message.obj = typesOfLoginButton.size();

            if(ctpmw != null && ctpmw.get() != null && message != null) {
                ctpmw.get().sendResultToPresenter(message);
            }

            // mobile - portrait
            if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.COMPACT){
                margins = new int[] {10, 10, 10, 10};
                width = 110;
                height = 110;
                imagePincodeId = R.drawable.ic_keyboard;
                imageRFIDId = R.drawable.ic_rfid;
                imageUserPassId = R.drawable.ic_personalcard;
                imageBarcodeId = R.drawable.ic_barcode;
            }
            // mobile - landscape
            else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.EXPANDED){
                margins = new int[] {15, 10, 15, 10};
                width = 110;
                height = 110;
                imagePincodeId = R.drawable.ic_keyboard;
                imageRFIDId = R.drawable.ic_rfid;
                imageUserPassId = R.drawable.ic_personalcard;
                imageBarcodeId = R.drawable.ic_barcode;
            }
            // tablet - portrait
            else if(wsc[0] == WindowSizeClass.EXPANDED && wsc[1] == WindowSizeClass.MEDIUM){
                margins = new int[] {30, 10, 30, 20};
                width = 120;
                height = 120;
                imagePincodeId = R.drawable.ic_keyboard;
                imageRFIDId = R.drawable.ic_rfid;
                imageUserPassId = R.drawable.ic_personalcard;
                imageBarcodeId = R.drawable.ic_barcode;
            }
            // tablet - landscape
            else if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
                margins = new int[] {30, 10, 30, 10};
                width = 120;
                height = 120;
                imagePincodeId = R.drawable.ic_keyboard;
                imageRFIDId = R.drawable.ic_rfid;
                imageUserPassId = R.drawable.ic_personalcard;
                imageBarcodeId = R.drawable.ic_barcode;
            }
            // pda - portrait
            else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.COMPACT){
                margins = new int[] {10, 10, 10, 10};
                width = 90;
                height = 90;
                imagePincodeId = R.drawable.mini_keyboard;
                imageRFIDId = R.drawable.mini_rfid;
                imageUserPassId = R.drawable.mini_personalcard;
                imageBarcodeId = R.drawable.mini_barcode;
            }

            for (int i = 0; i < typesOfLoginButton.size(); i++) {
                switch (typesOfLoginButton.get(i)){
                    case 0: {
                        ApplicationLogger.logging(LogLevel.ERROR, "Nem lehet ilyen módon bejelentkezni!");
                        break;
                    }
                    case 1:{
                        ApplicationLogger.logging(LogLevel.INFORMATION, "Felhasználónév és jelszó gomb létrehozásának elindítása!");

                        if(loginAccAndPassButton == null) loginAccAndPassButton = new ImageButton(context.getApplicationContext());

                        setStyleForButton(
                                loginAccAndPassButton,
                                R.drawable.circle_background,
                                imageUserPassId,
                                margins,
                                width,
                                height
                        );

                        ApplicationLogger.logging(LogLevel.INFORMATION, "Felhasználónév és jelszó gomb létrehozásának befejezése!");
                        message = Util.createMessage(Util.BUTTON_USER_PASS, "A UserPassword gomb elkészült!");
                        loginAccAndPassButton.setId(Util.BUTTON_USER_PASS);
                        message.obj = loginAccAndPassButton;

                        break;
                    }
                    case 2:{
                        ApplicationLogger.logging(LogLevel.INFORMATION, "Pinkód gomb létrehozásának elindítása!");

                        if(loginPinButton == null) loginPinButton = new ImageButton(context.getApplicationContext());

                        setStyleForButton(
                                loginPinButton,
                                R.drawable.circle_background,
                                imagePincodeId,
                                margins,
                                width,
                                height
                        );

                        ApplicationLogger.logging(LogLevel.INFORMATION, "Pinkód gomb létrehozásának befejezése!");
                        message = Util.createMessage(Util.BUTTON_PINCODE, "A Pinkód gomb elkészült!");
                        loginPinButton.setId(Util.BUTTON_PINCODE);
                        message.obj = loginPinButton;


                        break;
                    }
                    case 4:{
                        ApplicationLogger.logging(LogLevel.INFORMATION, "RFID gomb létrehozásának elindítása!");

                        if(loginRFIDButton == null) loginRFIDButton = new ImageButton(context.getApplicationContext());

                        setStyleForButton(
                                loginRFIDButton,
                                R.drawable.circle_background,
                                imageRFIDId,
                                margins,
                                width,
                                height
                        );

                        ApplicationLogger.logging(LogLevel.INFORMATION, "RFID gomb létrehozásának befejezése!");
                        message = Util.createMessage(Util.BUTTON_RFID, "Az RFID gomb elkészült!");
                        loginRFIDButton.setId(Util.BUTTON_RFID);
                        message.obj = loginRFIDButton;


                        break;
                    }
                    case 8:{
                        ApplicationLogger.logging(LogLevel.INFORMATION, "Barcode gomb létrehozásának elindítása!");

                        if(loginBarcodeButton == null) loginBarcodeButton = new ImageButton(context.getApplicationContext());

                        setStyleForButton(
                                loginBarcodeButton,
                                R.drawable.circle_background,
                                imageBarcodeId,
                                margins,
                                width,
                                height
                        );

                        ApplicationLogger.logging(LogLevel.INFORMATION, "Barcode gomb létrehozásának befejezése!");
                        message = Util.createMessage(Util.BUTTON_BARCODE, "A Barcode gomb elkészült!");
                        loginBarcodeButton.setId(Util.BUTTON_BARCODE);
                        message.obj = loginBarcodeButton;

                        break;
                    }
                    case 16:{
                        ApplicationLogger.logging(LogLevel.INFORMATION, "Felhasználónév és jelszó gomb létrehozásának elindítása!");
                        ApplicationLogger.logging(LogLevel.INFORMATION, "Felhasználónév és jelszó gomb létrehozásának befejezése!");
                    }
                }

                if(ctpmw != null && ctpmw.get() != null && message != null) {
                    ctpmw.get().sendResultToPresenter(message);
                }
            }

            typesOfLoginButton.clear();
        }
        catch (Exception e){
            ApplicationLogger.logging(LogLevel.FATAL, e.getMessage());
        }

        return null;
    }

    private void setStyleForButton(ImageButton button, int backgroundId, int imageId, int[] margins, int width, int height) {
        if(button == null) return;

        button.setImageResource(imageId);
        button.setBackground(ContextCompat.getDrawable(context.getApplicationContext(), backgroundId));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(margins[0],margins[1], margins[2], margins[3]);

        button.setLayoutParams(lp);
    }
}
