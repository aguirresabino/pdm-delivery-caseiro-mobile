package io.github.aguirresabino.deliverycaseiro.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.ChefeActivity;
import io.github.aguirresabino.deliverycaseiro.adapter.ListCardAdapter;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;

public class TabChefeFragment extends BaseFragment {

    private final String TAG = getClass().getName() + " ESPECIFICA ";

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_chefe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.fragmentTabChefeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListCardAdapter(getResources().getStringArray(R.array.teste), this.onClickChefe()));

        super.onViewCreated(view, savedInstanceState);
    }

    private ListCardAdapter.CardOnClickListener onClickChefe() {
        return new ListCardAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                String item = getResources().getStringArray(R.array.teste)[idx];
                Intent intent = new Intent(getActivity(), ChefeActivity.class);
                startActivity(intent);
            }
        };
    }
}
