package hu.unideb.inf.devicelogon.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import hu.unideb.inf.devicelogon.fragments.BaseFragment;

public class FragmentPagerAdapter extends FragmentStateAdapter {

    List<BaseFragment> baseFragmentList;

    public FragmentPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<BaseFragment> baseFragmentList) {
        super(fragmentActivity);
        this.baseFragmentList = baseFragmentList;
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
}
