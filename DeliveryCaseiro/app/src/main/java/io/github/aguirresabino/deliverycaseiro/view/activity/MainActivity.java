package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.adapter.SmartFragmentStatePagerAdapter;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabChefeFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabClientePedidosFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabPedidoCustomizadoFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) @Nullable Toolbar toolbar;
    @BindView(R.id.activityMainViewPager) ViewPager viewPager;
    @BindView(R.id.activityMainInitialTabLayout) TabLayout tabLayout;

    private MainActivityPagerAdapter mainActivityPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //
        setUpToolbar(toolbar);
        toolbar.setTitle(R.string.app_name);
        //Inicia uma nova instancia de mainActivityPagerAdapter e passo o getChildFragmentManger,
        //pois o estou trabalhando com nested fragments (fragmento dentro de fragmento)
        mainActivityPagerAdapter = new MainActivityPagerAdapter(
                getSupportFragmentManager(), this.getResources().getStringArray(R.array.mainActivityTabTitles));
        //definindo o adapter do viewpager utilizado nesta activity
        viewPager.setAdapter(mainActivityPagerAdapter);
        //inicializando o tablayout utilizando um viewpager
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MainActivityPagerAdapter extends SmartFragmentStatePagerAdapter<BaseFragment> {

        private final int CHEFE_LIST = 0;
        private final int PEDIDO_CUSTOMIZADO = 1;
        private final int MEUS_PEDIDOS = 2;
        private final int NUM_TABS = 3;
        private String[] tabTitles;

        public MainActivityPagerAdapter(FragmentManager fragmentManager, String[] tabTitles) {
            super(fragmentManager);
            this.tabTitles = tabTitles;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case CHEFE_LIST: return new TabChefeFragment();
                case PEDIDO_CUSTOMIZADO: return new TabPedidoCustomizadoFragment();
                case  MEUS_PEDIDOS: return new TabClientePedidosFragment();
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
}
