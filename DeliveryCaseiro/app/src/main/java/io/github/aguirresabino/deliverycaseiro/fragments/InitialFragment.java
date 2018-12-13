package io.github.aguirresabino.deliverycaseiro.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.R;

/**
 * Este Fragment implementa a tela inicial da aplicação após o Login.
 * A tela inicial é composta por duas abas (tabs), cada uma representando um outro fragment filho. Ou seja, este fragment
 * possui dois outros fragments para exibição, que são controlados por um ViewPager + FragmentPagerAdapter.
 */
public class InitialFragment extends BaseFragment {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName();
    //private AppCompatActivity activityContext = null;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        this.activityContext = (AppCompatActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //retorna a view com as modificações feitas
        return view;
    }
}
