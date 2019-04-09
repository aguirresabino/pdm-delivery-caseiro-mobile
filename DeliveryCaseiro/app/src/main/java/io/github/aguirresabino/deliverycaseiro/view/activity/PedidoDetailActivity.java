package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;

public class PedidoDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityPedidoDetailDescricao) TextView descricao;
    @BindView(R.id.activityPedidoDetailValor) TextView valor;
    @BindView(R.id.appCompatImageView) AppCompatImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detail);
        // ButterKnife
        ButterKnife.bind(this);
        //
        toolbar.setTitle(R.string.detalhe_do_pedido);
        setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Pedido pedido = getIntent().getParcelableExtra("pedido");
        descricao.setText(pedido.getItens().get(0).getDescricao() + "\n Quantidade: " + pedido.getItens().get(0).getQuantidade());
        valor.setText("R$ " + pedido.getValor());

        Picasso.get().load(pedido.getImagem()).into(imagem);
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
}
