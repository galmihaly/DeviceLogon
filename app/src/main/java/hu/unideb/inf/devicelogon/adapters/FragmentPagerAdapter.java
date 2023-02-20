package hu.unideb.inf.devicelogon.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.unideb.inf.devicelogon.enums.FragmentTypes;
import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public class FragmentPagerAdapter extends FragmentStateAdapter {

    private List<BaseFragment> baseFragmentList = new ArrayList<>();
    private HashMap<FragmentTypes, BaseFragment> baseFragmentHashMap = new HashMap<>();

    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return baseFragmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return baseFragmentList.size();
    }

    public void addFragment(BaseFragment baseFragment){
        this.baseFragmentList.add(baseFragment);
    }

    public int getCurrentFragment(FragmentTypes fragmentTypes){
        int position = -1;

        for (Map.Entry<FragmentTypes, BaseFragment> entry : baseFragmentHashMap.entrySet()){
            Log.e("name", String.valueOf(entry.getKey()));
            if(entry.getKey() == fragmentTypes){
                for (int i = 0; i < baseFragmentList.size(); i++) {
                    if(entry.getValue().equals(baseFragmentList.get(i))){
                        position = i;
                        break;
                    }
                }
                break;
            }
        }

        if(position != -1) return position;
        return position;
    }



    public void setItem(FragmentTypes fragmentTypes, BaseFragment baseFragment){
        baseFragmentHashMap.put(fragmentTypes, baseFragment);
    }
}
