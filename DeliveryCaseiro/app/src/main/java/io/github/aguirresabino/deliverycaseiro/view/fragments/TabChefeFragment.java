package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Call<List<Chefe>> call = apiDeliveryCaseiroServiceI.buscarChefesPorCep(DeliveryApplication.usuarioLogado.getEndereco().getCep());
        call.enqueue(new Callback<List<Chefe>>() {
            @Override
            public void onResponse(Call<List<Chefe>> call, Response<List<Chefe>> response) {
                chefes = response.body();
                recyclerView.setAdapter(new ChefeRecyclerViewAdapter(chefes, onClickChefe()));
                MyLogger.logInfo(DeliveryApplication.MY_TAG, TabChefeFragment.class, chefes.toString());
            }

            @Override
            public void onFailure(Call<List<Chefe>> call, Throwable t) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, TabChefeFragment.class, "ERRORRRRR");
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private ChefeRecyclerViewAdapter.CardOnClickListener onClickChefe() {
        return new ChefeRecyclerViewAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                Intent intent = new Intent(getActivity(), ChefeActivity.class);
                intent.putExtra("chefe", chefes.get(idx));
                startActivity(intent);
            }
        };
    }

    // Implementação do RecyclerView.Adapter

    static class ChefeRecyclerViewAdapter extends RecyclerView.Adapter<ChefeRecyclerViewAdapter.ListCardViewHolder> {

        private List<Chefe> chefes;
        private ChefeRecyclerViewAdapter.CardOnClickListener cardOnClickListener;

        public ChefeRecyclerViewAdapter(List<Chefe> chefes, ChefeRecyclerViewAdapter.CardOnClickListener cardOnClickListener){
            this.chefes = chefes;
            this.cardOnClickListener = cardOnClickListener;
        }

        //Cria novas views
        @NonNull
        @Override
        public ListCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //criando nova view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_line, parent, false);

            ListCardViewHolder listCardViewHolder = new ListCardViewHolder(view);
            return listCardViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ChefeRecyclerViewAdapter.ListCardViewHolder holder, final int position) {
            Chefe chefe = chefes.get(position);

            holder.nome.setText(chefe.getNome());
            holder.descricao.setText("Telefone: " + chefe.getTelefone());
            //holder.image.setImageResource(Integer.parseInt(holder.itemView.getResources().getResourceName(R.mipmap.ic_launcher_round)));

            if(cardOnClickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        cardOnClickListener.onClickCard(holder.itemView, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return chefes != null ? chefes.size() : 0;
        }

        public static class ListCardViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.card_img)
            ImageView image;
            @BindView(R.id.card_nome)
            TextView nome;
            @BindView(R.id.card_descricao) TextView descricao;
            @BindView(R.id.card_view)
            CardView card;

            public ListCardViewHolder(View itemView) {
                super(itemView);
                //
                ButterKnife.bind(this, itemView);
            }
        }

        public interface CardOnClickListener {
            public void onClickCard(View view, int idx);
        }
    }
}
