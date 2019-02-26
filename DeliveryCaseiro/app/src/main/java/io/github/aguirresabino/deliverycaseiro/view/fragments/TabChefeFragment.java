package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIClientDeliveryCaserio;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroServiceI;
import io.github.aguirresabino.deliverycaseiro.view.activity.ChefeActivity;
import io.github.aguirresabino.deliverycaseiro.view.adapter.ListCardAdapter;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabChefeFragment extends BaseFragment {

    @BindView(R.id.fragmentTabChefeRecyclerView) RecyclerView recyclerView;

    private APIDeliveryCaseiroServiceI apiDeliveryCaseiroServiceI;
    private List<Chefe> chefes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_chefe, container, false);
        ButterKnife.bind(this, view);

        apiDeliveryCaseiroServiceI = APIClientDeliveryCaserio.getClient().create(APIDeliveryCaseiroServiceI.class);
        Call<List<Chefe>> call = apiDeliveryCaseiroServiceI.buscarChefesPorCep(DeliveryApplication.usuarioLogado.getEndereco().getCep());

        call.enqueue(new Callback<List<Chefe>>() {
            @Override
            public void onResponse(Call<List<Chefe>> call, Response<List<Chefe>> response) {
                chefes = response.body();
                MyLogger.logInfo(DeliveryApplication.MY_TAG, TabChefeFragment.class, chefes.toString());
            }

            @Override
            public void onFailure(Call<List<Chefe>> call, Throwable t) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, TabChefeFragment.class, "ERRORRRRR");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(new ListCardAdapter(chefes, this.onClickChefe()));

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
