package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;

public class ClientePerfilFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando o fragment e salvando na variavel view
        View view = inflater.inflate(R.layout.fragment_cliente_perfil, container, false);
        //
        ButterKnife.bind(this, view);
        //
        toolbar.setTitle(R.string.perfil);
        //Adicionando o toolbar a activity do contexto
        //A activity do contexto é recuperada e depois é utilizado o método setUpToolbar implementado em BaseActivity
        //A variável activityContext está definida em BaseFragment como protected. Ela é inicializada em onAttach, pois
        //neste momento do ciclo de vida do fragment, já podemos ter uma referência para a activity pai
        activityContext.setUpToolbar(toolbar);
        //retorna a view com as modificações feitas
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ////Atualizando a toolbar na implementação do menu lateral
        activityContext.updateToolbarInDrawer(toolbar);
    }
}