package hu.unideb.inf.devicelogon.listeners;

import android.view.View;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;
import hu.unideb.inf.devicelogon.enums.FragmentTypes;

public class LoginButtonListener implements View.OnClickListener {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager2 viewPager;
    private FragmentTypes fT;

    public void setFragmentPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter) { this.fragmentPagerAdapter = fragmentPagerAdapter; }

    public void setfT(FragmentTypes fT) { this.fT = fT; }

    public void setViewPager(ViewPager2 viewPager) { this.viewPager = viewPager; }

    @Override
    public void onClick(View view) {

        int position = fragmentPagerAdapter.setCurrentButtonColorByEnum(fT);
        viewPager.setCurrentItem(position, true);
    }
}
