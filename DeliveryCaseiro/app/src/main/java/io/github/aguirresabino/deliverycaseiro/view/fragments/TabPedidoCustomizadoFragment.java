package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class TabPedidoCustomizadoFragment extends BaseFragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_pedido_customizado, container, false);
        // ButterKnife
        ButterKnife.bind(this, view);
        // O fragment define o menu da toolbar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_tab_pedido_customizado, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_perfil:
                ToastHelper.toastShort(getContext(), "Clicou em Perfil");
        }
        return super.onOptionsItemSelected(item);
    }
}
