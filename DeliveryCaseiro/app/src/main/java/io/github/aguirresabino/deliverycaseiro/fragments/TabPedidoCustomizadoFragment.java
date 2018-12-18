package io.github.aguirresabino.deliverycaseiro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import io.github.aguirresabino.deliverycaseiro.R;

public class TabPedidoCustomizadoFragment extends Fragment {

    private final String TAG = getClass().getName() + " ESPECIFICA ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedido_customizado, container, false);
    }

}
