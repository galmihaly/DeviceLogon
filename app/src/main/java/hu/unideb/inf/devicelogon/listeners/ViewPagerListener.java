package hu.unideb.inf.devicelogon.listeners;

import androidx.viewpager2.widget.ViewPager2;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;

public class ViewPagerListener extends ViewPager2.OnPageChangeCallback {

    private FragmentPagerAdapter fragmentPagerAdapter;

    public ViewPagerListener(FragmentPagerAdapter fragmentPagerAdapter) {
        this.fragmentPagerAdapter = fragmentPagerAdapter;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        this.fragmentPagerAdapter.setCurrentButtonColorByPosition(position);
    }
}
