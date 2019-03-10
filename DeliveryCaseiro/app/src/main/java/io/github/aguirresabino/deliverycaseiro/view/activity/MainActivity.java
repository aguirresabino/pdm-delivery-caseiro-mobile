package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.adapter.MainActivityPagerAdapter;

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

        MyLogger.logInfo(DeliveryApplication.MY_TAG, MainActivity.class, DeliveryApplication.usuarioLogado.toString());
    }
}
