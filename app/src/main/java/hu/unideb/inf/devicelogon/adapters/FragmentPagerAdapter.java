package hu.unideb.inf.devicelogon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;
import hu.unideb.inf.devicelogon.utils.Util;

public class FragmentPagerAdapter extends FragmentStateAdapter {

    private List<BaseFragment> baseFragmentList = new ArrayList<>();
    private HashMap<FragmentTypes, BaseFragment> baseFragmentHashMap = new HashMap<>();
    private List<ImageButton> imageButtonList = new ArrayList<>();

    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return baseFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return baseFragmentList.size();
    }

    public void addFragment(BaseFragment baseFragment){
        this.baseFragmentList.add(baseFragment);
    }

    public int setCurrentButtonColorByEnum(FragmentTypes fragmentTypes){

        for (Map.Entry<FragmentTypes, BaseFragment> entry : baseFragmentHashMap.entrySet()){

            if(entry.getKey() == fragmentTypes){
                for (int i = 0; i < baseFragmentList.size(); i++) {
                    if(entry.getValue().equals(baseFragmentList.get(i))){
                        Util.changeButtonColor(imageButtonList, i);
                        return i;
                    }
                }
                break;
            }
        }

        return -1;
    }

    public void setCurrentButtonColorByPosition(int position){
        Util.changeButtonColor(imageButtonList, position);
    }

    public void addItemToHashMap(FragmentTypes fragmentTypes, BaseFragment baseFragment){
        baseFragmentHashMap.put(fragmentTypes, baseFragment);
    }

    public void addButtonToList(ImageButton imageButtons){
        imageButtonList.add(imageButtons);
    }
}
