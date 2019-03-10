package io.github.aguirresabino.deliverycaseiro.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabChefeFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabPedidoCustomizadoFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;

public class InitialFragmentPagerAdapter extends SmartFragmentStatePagerAdapter<BaseFragment> {

    private final int CHEFE_LIST = 0;
    private final int PEDIDO_CUSTOMIZADO = 1;
    private final int MEUS_PEDIDOS = 2;
    private final int NUM_TABS = 3;
    private String[] tabTitles;

    public InitialFragmentPagerAdapter(FragmentManager fragmentManager, String[] tabTitles) {
        super(fragmentManager);
        this.tabTitles = tabTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case CHEFE_LIST: return new TabChefeFragment();
            case PEDIDO_CUSTOMIZADO: return new TabPedidoCustomizadoFragment();
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
