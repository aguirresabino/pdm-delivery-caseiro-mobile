package io.github.aguirresabino.deliverycaseiro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.adapter.InitialFragmentPagerAdapter;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;

/**
 * Este Fragment implementa a tela inicial da aplicação após o Login.
 * A tela inicial é composta por duas abas (tabs), cada uma representando um outro fragment filho. Ou seja, este fragment
 * possui dois outros fragments para exibição, que são controlados por um ViewPager + FragmentPagerAdapter.
 */
public class InitialFragment extends BaseFragment {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName();

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
        //Buscando o toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        //Adicionando o toolbar a activity do contexto por meio do método da classe BaseFragment
        setUpToolbar(toolbar, this.getResources().getString(R.string.app_name));

        //buscando a referencia para o viewpager no layout
        ViewPager viewPager = view.findViewById(R.id.fragmentInitialViewPager);
        //buscando a referencia para o tablayout no layout
        TabLayout tabLayout = view.findViewById(R.id.fragmentInitialTabLayout);
        //definindo o adapter do viewpager utilizado neste fragment
        viewPager.setAdapter(initialFragmentPagerAdapter);
        //inicializando o tablayout utilizando um viewpager
        tabLayout.setupWithViewPager(viewPager);

        //retorna a view com as modificações feitas
        return view;
    }
}