package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.model.entities.ChefePedidoCustomizado;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import io.github.aguirresabino.deliverycaseiro.view.transform.CircleTransform;

public class ChefesPedidoCustomizadoAceitoActivity extends BaseActivity {

    @BindView(R.id.activityChefesPedidoCustomizadoAceitoRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefes_pedido_customizado_aceito);
        // ButterKnife
        ButterKnife.bind(this);
        //
        pedido = getIntent().getParcelableExtra("pedido");
        //
        toolbar.setTitle(R.string.pedido_customizado);
        setUpToolbar(toolbar);
        //utilizando botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //configurando recyclerview
        setUpRecyclerView();

        // Verificando se algum chefe aceitou fazer o pedido
        if(pedido.getChefesDoPedidoCustomizado() == null || pedido.getChefesDoPedidoCustomizado().isEmpty())  ToastHelper.toastShort(ChefesPedidoCustomizadoAceitoActivity.this, "Nenhum chefe aceitou fazer este pedido ainda!");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //se clicar no botão voltar, a activity atual é finalizada
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter(this.pedido.getChefesDoPedidoCustomizado(), this.onClickChefe()));
    }

    private ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.CardOnClickListener onClickChefe(){
        return new ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                Intent intent = new Intent(getBaseContext(), AceitarPedidoCustomizadoActivity.class);
                intent.putExtra("chefe.selecionado", idx);
                intent.putExtra("pedido", pedido);
                startActivity(intent);
            }
        };
    }

    static class ChefeListCardAdapter extends RecyclerView.Adapter<ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.ListCardViewHolder> {

        private List<ChefePedidoCustomizado> chefes;
        private ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.CardOnClickListener cardOnClickListener;

        public ChefeListCardAdapter(List<ChefePedidoCustomizado> chefes, ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.CardOnClickListener cardOnClickListener){
            this.chefes = chefes;
            this.cardOnClickListener = cardOnClickListener;
        }

        //Cria novas views
        @NonNull
        @Override
        public ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.ListCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //criando nova view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_line, parent, false);

            ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.ListCardViewHolder listCardViewHolder = new ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.ListCardViewHolder(view);
            return listCardViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ChefesPedidoCustomizadoAceitoActivity.ChefeListCardAdapter.ListCardViewHolder holder, final int position) {
            ChefePedidoCustomizado chefe = this.chefes.get(position);

            holder.nome.setText(chefe.getNome());
            holder.descricao.setText("Valor cobrado: R$ " + chefe.getValor());
            Picasso.get()
                    .load(chefe.getImagem())
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
            return chefes != null || !chefes.isEmpty() ? chefes.size() : 0;
        }

        public static class ListCardViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.card_img) AppCompatImageView image;
            @BindView(R.id.card_nome) TextView nome;
            @BindView(R.id.card_descricao) TextView descricao;
            @BindView(R.id.card_view) CardView card;

            public ListCardViewHolder(View itemView) {
                super(itemView);
                //
                ButterKnife.bind(this, itemView);
            }
        }

        public interface CardOnClickListener {
            void onClickCard(View view, int idx);
        }
    }
}
