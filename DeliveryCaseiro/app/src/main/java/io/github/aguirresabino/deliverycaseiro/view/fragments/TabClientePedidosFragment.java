package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroPedido;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.services.PedidoService;
import io.github.aguirresabino.deliverycaseiro.view.activity.UsuarioPerfilActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.PedidoDetailActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import io.github.aguirresabino.deliverycaseiro.view.transform.CircleTransform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabClientePedidosFragment extends BaseFragment {

    @BindView(R.id.fragmentClientePedidosRecyclerView) RecyclerView recyclerView;

    private PedidoService pedidoService;
    private LocalBroadcastReceiver localBroadcastReceiver;
    private List<Pedido> pedidos;

    @Override
    public void onAttach(@NonNull Context context) {
        pedidoService = new PedidoService(getActivity());
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_TAB_CLIENTE_PEDIDOS_FRAGMENT));
        // Carregando os pedidos do usuário
        this.pedidoService.readByUsuario(DeliveryApplication.usuarioLogado.getId());
        super.onViewCreated(view, savedInstanceState);
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
                getActivity().startActivity(new Intent(getContext(), UsuarioPerfilActivity.class));
                break;
            case R.id.action_sair:
                DeliveryApplication.usuarioLogado = null;
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(localBroadcastReceiver);
    }

    private ListCardAdapter.CardOnClickListener onClickPedido() {
        return new ListCardAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                Pedido pedido = pedidos.get(idx);
                Intent intent = new Intent(getActivity(), PedidoDetailActivity.class);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
            }
        };
    }

    // Adapter RecyclerView

    static class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {

        private List<Pedido> pedidos;
        private ListCardAdapter.CardOnClickListener cardOnClickListener;

        public ListCardAdapter(List<Pedido> pedidos, ListCardAdapter.CardOnClickListener cardOnClickListener){
            this.pedidos = pedidos;
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
            Pedido pedido = pedidos.get(position);

            holder.nome.setText(pedido.getItens().get(0).getNome());
            holder.descricao.setText("Preço: " + pedido.getValor() + " | " + "Quantidade: " + pedido.getItens().get(0).getQuantidade());
            Picasso.get()
                    .load(pedido.getImagem())
                    .transform(new CircleTransform())
                    .into(holder.image);

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
            return pedidos != null ? pedidos.size() : 0;
        }

        public static class ListCardViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.card_img) AppCompatImageView image;
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

    public class LocalBroadcastReceiver extends BroadcastReceiver {

        public static final String LOCAL_BROADCAST_TAB_CLIENTE_PEDIDOS_FRAGMENT = "local.broadcast.tab.cliente.pedidos.fragment";

        @Override
        public void onReceive(Context context, Intent intent) {
            pedidos = intent.getParcelableArrayListExtra("pedido.service.readByUsuario");
            if(pedidos != null || !pedidos.isEmpty()) {
                //TODO Como informar ao recyclerview que a view deve ser atualizada
                recyclerView.setAdapter(new ListCardAdapter(pedidos, onClickPedido()));
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), TabClientePedidosFragment.class, pedidos.toString());
            } else
                ToastHelper.toastShort(getActivity(), "Nenhum pedido foi encontrado!");
        }
    }
}
