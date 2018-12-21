package io.github.aguirresabino.deliverycaseiro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import io.github.aguirresabino.deliverycaseiro.R;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ListCardViewHolder> {

    private String[] dataSet;
    private CardOnClickListener cardOnClickListener;

    public ListCardAdapter(String[] dataSet, CardOnClickListener cardOnClickListener){
        this.dataSet = dataSet;
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
    public void onBindViewHolder(@NonNull final ListCardViewHolder holder, final int position) {
        String elem = dataSet[position];

        holder.nome.setText("Nome do chefe");
        holder.descricao.setText("Descrição");
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
        public ImageView image;
        public TextView nome;
        public TextView descricao;
        public CardView card;

        public ListCardViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.card_img);
            nome = itemView.findViewById(R.id.card_nome);
            descricao = itemView.findViewById(R.id.card_descricao);
            card = itemView.findViewById(R.id.card_view);
        }
    }

    public interface CardOnClickListener {
        public void onClickCard(View view, int idx);
    }
}