package hu.unideb.inf.devicelogon.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hu.unideb.inf.devicelogon.R;
import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.enums.WindowSizeClass;
import hu.unideb.inf.devicelogon.fragments.fragmentinterfaces.IDetailFragment;

public class DetailFragment extends BaseFragment implements IDetailFragment {

    private View view;
    private WindowSizeClass[] wsc = new WindowSizeClass[2];
    private TextView textView;
    private String text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int orientation = getResources().getConfiguration().orientation;
        wsc[0]  = (WindowSizeClass) getArguments().getSerializable("windowHeightEnum");
        wsc[1] =  (WindowSizeClass) getArguments().getSerializable("windowWidthEnum");
        textView = (TextView) getArguments().getSerializable("fragmentType");
        text = getArguments().getString("eredmeny");

        view = inflater.inflate(R.layout.detail_fragment_tablet_landscape, container, false);
        textView = view.findViewById(R.id.detailHeaderText_tablet_landscape);

        if(wsc[0] == WindowSizeClass.MEDIUM && wsc[1] == WindowSizeClass.EXPANDED){
            if(orientation == Configuration.ORIENTATION_PORTRAIT) {
                view = inflater.inflate(R.layout.detail_fragment_tablet_landscape, container, false);
                textView = view.findViewById(R.id.detailHeaderText_tablet_landscape);
            }
        }

        textView.setText(text);
        return view;
    }

    @Override
    public void loadOtherActivityPages(Intent intent) {

    }
}
