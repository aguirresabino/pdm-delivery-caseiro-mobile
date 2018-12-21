package io.github.aguirresabino.deliverycaseiro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;

public class TabChefeFragment extends BaseFragment {

    private final String TAG = getClass().getName() + " ESPECIFICA ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_chefe, container, false);
    }

}
