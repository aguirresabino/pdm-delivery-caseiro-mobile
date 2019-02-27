package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.adapter.InitialFragmentPagerAdapter;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;

/**
 * Este Fragment implementa a tela inicial da aplicação após o Login.
 * A tela inicial é composta por duas abas (tabs), cada uma representando um outro fragment filho. Ou seja, este fragment
 * possui dois outros fragments para exibição, que são controlados por um ViewPager + FragmentPagerAdapter.
 */
public class InitialFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fragmentInitialViewPager) ViewPager viewPager;
    @BindView(R.id.fragmentInitialTabLayout) TabLayout tabLayout;

    private InitialFragmentPagerAdapter initialFragmentPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicia uma nova instancia de initialFragmentPagerAdapter e passo o getChildFragmentManger,
        //pois o estou trabalhando com nested fragments (fragmento dentro de fragmento)
        initialFragmentPagerAdapter = new InitialFragmentPagerAdapter(
                this.getChildFragmentManager(),
                this.getResources().getStringArray(R.array.initialFragmentTabTitles));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflando o fragment e salvando na variavel view
        View view = inflater.inflate(R.layout.fragment_initial, container, false);
        //
        ButterKnife.bind(this, view);
        //
        toolbar.setTitle(R.string.app_name);
        //Adicionando o toolbar a activity do contexto
        //A activity do contexto é recuperada e depois é utilizado o método setUpToolbar implementado em BaseActivity
        //A variável activityContext está definida em BaseFragment como protected. Ela é inicializada em onAttach, pois
        //neste momento do ciclo de vida do fragment, já podemos ter uma referência para a activity pai
        activityContext.setUpToolbar(toolbar);
        //definindo o adapter do viewpager utilizado neste fragment
        viewPager.setAdapter(initialFragmentPagerAdapter);
        //inicializando o tablayout utilizando um viewpager
        tabLayout.setupWithViewPager(viewPager);

        //retorna a view com as modificações feitas
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ////Atualizando a toolbar na implementação do menu lateral
//        activityContext.updateToolbarInDrawer(toolbar);
//    }


    @Override
    public void onResume() {
        super.onResume();
        //Atualizando a toolbar na implementação do menu lateral
        activityContext.updateToolbarInDrawer(toolbar);
    }
}
