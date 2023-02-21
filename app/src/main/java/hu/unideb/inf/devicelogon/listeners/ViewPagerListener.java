package hu.unideb.inf.devicelogon.listeners;

import androidx.viewpager2.widget.ViewPager2;

import hu.unideb.inf.devicelogon.adapters.FragmentPagerAdapter;

public class ViewPagerListener extends ViewPager2.OnPageChangeCallback {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private static ViewPagerListener mInstance;

    public static ViewPagerListener getInstance(){
        if(mInstance == null){
            mInstance = new ViewPagerListener();
        }
        return mInstance;
    }

    public void setFragmentPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        this.fragmentPagerAdapter = fragmentPagerAdapter;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        this.fragmentPagerAdapter.setCurrentButtonColorByPosition(position);
    }
}
