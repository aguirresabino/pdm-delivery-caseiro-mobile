package io.github.aguirresabino.deliverycaseiro.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;

public class ClientePerfilFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliente_perfil, container, false);
    }

}
