package hu.unideb.inf.devicelogon.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.ILoginFragment;
import hu.unideb.inf.devicelogon.fragments.fragmentspresenters.LoginFragmentPresenter;

public class LoginFragment extends BaseFragment implements ILoginFragment {

    private View view;
    private Button button;
    private LoginFragmentPresenter loginFragmentPresenter;
    private WindowSizeClass[] wsc = new WindowSizeClass[2];
    private FragmentTypes fragType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int orientation = getResources().getConfiguration().orientation;
        wsc[0]  = (WindowSizeClass) getArguments().getSerializable("windowHeightEnum");
        wsc[1] =  (WindowSizeClass) getArguments().getSerializable("windowWidthEnum");
        fragType = (FragmentTypes) getArguments().getSerializable("fragmentType");
        button = null;

        if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.COMPACT){
            if(orientation == Configuration.ORIENTATION_PORTRAIT){

                if(fragType == FragmentTypes.USERPASSFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_user_and_password_mobile_portrait, container, false);
                    button = view.findViewById(R.id.userpassLogBut_tablet_portrait);

                }
                else if(fragType == FragmentTypes.PINCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_pincode_mobile_portrait, container, false);
                    button = view.findViewById(R.id.pincodeLogBut_mobile_portrait);

                }
                else if(fragType == FragmentTypes.RFIDFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_rfid_mobile_portrait, container, false);
                    button = view.findViewById(R.id.rfidLogBut_mobile_portrait);

                }
                else if(fragType == FragmentTypes.BARCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_barcode_mobile_portrait, container, false);
                    button = view.findViewById(R.id.barcodeLogBut_mobile_portrait);

                }
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.EXPANDED){

            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                if(fragType == FragmentTypes.USERPASSFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_user_and_password_mobile_landscape, container, false);
                    button = view.findViewById(R.id.userpassLogBut_tablet_portrait);

                }
                else if(fragType == FragmentTypes.PINCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_pincode_mobile_landscape, container, false);
                    button = view.findViewById(R.id.loginButton3_mobile_landscape);

                }
                else if(fragType == FragmentTypes.RFIDFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_rfid_mobile_landscape, container, false);
                    button = view.findViewById(R.id.loginButton2_mobile_landscape);

                }
                else if(fragType == FragmentTypes.BARCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_barcode_mobile_landscape, container, false);
                    button = view.findViewById(R.id.loginButton4_mobile_landscape);

                }
            }
        }
        else if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {

                if(fragType == FragmentTypes.USERPASSFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_user_and_password_tablet_landscape, container, false);
                    button = view.findViewById(R.id.userpassLogBut_tablet_portrait);

                }
                else if(fragType == FragmentTypes.PINCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_pincode_tablet_landscape, container, false);
                    button = view.findViewById(R.id.loginButton3_tablet_landscape);

                }
                else if(fragType == FragmentTypes.RFIDFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_rfid_tablet_landscape, container, false);
                    button = view.findViewById(R.id.loginButton2_tablet_landscape);

                }
                else if(fragType == FragmentTypes.BARCODEFRAGMENT){

                    view = inflater.inflate(R.layout.fragment_barcode_tablet_landscape, container, false);
                    button = view.findViewById(R.id.loginButton4_tablet_landscape);

                }
            }
        }
        else if(wsc[0] == WindowSizeClass.COMPACT && wsc[1] == WindowSizeClass.COMPACT){

            if(fragType == FragmentTypes.USERPASSFRAGMENT){

                view = inflater.inflate(R.layout.fragment_user_and_password_pda_portrait, container, false);
                button = view.findViewById(R.id.loginButton1_pda_portrait);

            }
            else if(fragType == FragmentTypes.PINCODEFRAGMENT){

                view = inflater.inflate(R.layout.fragment_pincode_pda_portrait, container, false);
                button = view.findViewById(R.id.loginButton3_pda_portrait);

            }
            else if(fragType == FragmentTypes.RFIDFRAGMENT){

                view = inflater.inflate(R.layout.fragment_rfid_pda_portrait, container, false);
                button = view.findViewById(R.id.loginButton2_pda_portrait);

            }
            else if(fragType == FragmentTypes.BARCODEFRAGMENT){

                view = inflater.inflate(R.layout.fragment_barcode_pda_portrait, container, false);
                button = view.findViewById(R.id.loginButton4_pda_portrait);

            }
        }

        loginFragmentPresenter = new LoginFragmentPresenter(this, getContext().getApplicationContext(), wsc);



//        int orientation = getResources().getConfiguration().orientation;
//
//        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
//            view = inflater.inflate(R.layout.fragment_barcode_mobile_portrait, container, false);
//            button = view.findViewById(R.id.loginButton4_mobile_portrait);
//        }
//        else if(orientation == Configuration.ORIENTATION_LANDSCAPE){
//            view = inflater.inflate(R.layout.fragment_barcode_mobile_landscape, container, false);
//            button = view.findViewById(R.id.loginButton4_mobile_landscape);
//        }
//
//        wsc[0]  = (WindowSizeClass) getArguments().getSerializable("windowHeightEnum");
//        wsc[1] =  (WindowSizeClass) getArguments().getSerializable("windowWidthEnum");
//
//        Log.e("height", String.valueOf(wsc[0]));
//        Log.e("width", String.valueOf(wsc[1]));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(button != null){
            button.setOnClickListener(view -> {
                loginFragmentPresenter.addFragment();
            });
        }
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {
        startActivity(intent);
    }
}
