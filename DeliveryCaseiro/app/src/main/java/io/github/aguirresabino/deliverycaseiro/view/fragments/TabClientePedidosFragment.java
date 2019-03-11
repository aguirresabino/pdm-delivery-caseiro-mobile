package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.activity.ClientePerfilActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.PedidoDetailActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class TabClientePedidosFragment extends BaseFragment {

    @BindView(R.id.fragmentClientePedidosRecyclerView) RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando o fragment e salvando na variavel view
        View view = inflater.inflate(R.layout.fragment_tab_cliente_pedidos, container, false);
        // ButterKnife
        ButterKnife.bind(this, view);
        // O fragment define o menu da toolbar
        setHasOptionsMenu(true);
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListCardAdapter(getResources().getStringArray(R.array.testeClientePedidosFragment), this.onClickPedido()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_tab_cliente_pedidos_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_perfil:
                getActivity().startActivity(new Intent(getContext(), ClientePerfilActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private ListCardAdapter.CardOnClickListener onClickPedido() {
        return new ListCardAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                String item = getResources().getStringArray(R.array.testeClientePedidosFragment)[idx];
                Intent intent = new Intent(getActivity(), PedidoDetailActivity.class);
                startActivity(intent);
            }
        };
    }

    static class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {

        private String[] dataSet;
        private ListCardAdapter.CardOnClickListener cardOnClickListener;

        public ListCardAdapter(String[] dataSet, ListCardAdapter.CardOnClickListener cardOnClickListener){
            this.dataSet = dataSet;
            this.cardOnClickListener = cardOnClickListener;
        }

        //Cria novas views
        @NonNull
        @Override
        public ListCardAdapter.ListCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //criando nova view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_line, parent, false);

            ListCardAdapter.ListCardViewHolder listCardViewHolder = new ListCardAdapter.ListCardViewHolder(view);
            return listCardViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ListCardAdapter.ListCardViewHolder holder, final int position) {
            String elem = dataSet[position];

            holder.nome.setText(elem);
            holder.descricao.setText("Descrição " + elem);
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
            return dataSet != null ? dataSet.length : 0;
        }

        public static class ListCardViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.card_img) ImageView image;
            @BindView(R.id.card_nome) TextView nome;
            @BindView(R.id.card_descricao) TextView descricao;
            @BindView(R.id.card_view) CardView card;

            public ListCardViewHolder(View itemView) {
                super(itemView);
                // ButterKnife
                ButterKnife.bind(this, itemView);
            }
        }

        public interface CardOnClickListener {
            public void onClickCard(View view, int idx);
        }
    }
}
