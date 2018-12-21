package io.github.aguirresabino.deliverycaseiro.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.github.aguirresabino.deliverycaseiro.fragments.TabPedidoCustomizadoFragment;
import io.github.aguirresabino.deliverycaseiro.fragments.TabChefeFragment;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;

public class InitialFragmentPagerAdapter extends SmartFragmentStatePagerAdapter<BaseFragment> {

    private final int TAB_0 = 0;
    private final int TAB_1 = 1;
    private final int NUM_TABS = 2;
    private String[] tabTitles;

    public InitialFragmentPagerAdapter(FragmentManager fragmentManager, String[] tabTitles) {
        super(fragmentManager);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case TAB_0: return new TabChefeFragment();
            case TAB_1: return new TabPedidoCustomizadoFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }
}
