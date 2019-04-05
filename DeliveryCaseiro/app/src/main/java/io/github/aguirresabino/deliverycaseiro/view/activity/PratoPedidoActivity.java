package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.ItemPedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Prato;
import io.github.aguirresabino.deliverycaseiro.model.enums.StatusPedidoEnum;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroPedido;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PratoPedidoActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityPratoPedidoTextView) TextView descricao;
    @BindView(R.id.activityPratoPedidoInputQuantidade) TextInputLayout quantidade;
    @BindView(R.id.appCompatImageView) AppCompatImageView imagem;

    private APIDeliveryCaseiroPedido apiDeliveryCaseiroPedido;
    private Prato prato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_pedido);
        //
        ButterKnife.bind(this);

        this.apiDeliveryCaseiroPedido = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroPedido();

        prato = getIntent().getParcelableExtra("prato");

        Picasso.get().load(prato.getImagem()).into(imagem);

        toolbar.setTitle(prato.getNome());
        setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        descricao.setText(prato.getDescricao());

        //TODO ALTERAR IMAGEM DO PRATO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_prato_pedido_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //se clicar no botão voltar, a activity atual é finalizada
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_ok:
                this.fazerPedido();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fazerPedido() {
//        ToastHelper.toastShort(PratoPedidoActivity.this, "Clicou");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescricao(prato.getDescricao());
        itemPedido.setNome(prato.getNome());
        itemPedido.setQuantidade(Integer.valueOf(quantidade.getEditText().getText().toString()));
        itemPedido.setValor(String.valueOf(prato.getValor()));

        Pedido pedido = new Pedido();
        pedido.setEndereco(DeliveryApplication.usuarioLogado.getEndereco());
        pedido.setIdUsuario(DeliveryApplication.usuarioLogado.getId());
        pedido.setEntregar(true);
        pedido.setIdFornecedor(getIntent().getStringExtra("idChefe"));
        pedido.setStatus(StatusPedidoEnum.PEDIDO_EM_PREPARO.getValue());
        pedido.setValor(String.valueOf(itemPedido.getValor() * itemPedido.getQuantidade()));
        pedido.setItens(Arrays.asList(itemPedido));
        pedido.setImagem(prato.getImagem());

        showDialog(pedido);
    }

    private void showDialog(Pedido pedido) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Deseja finalizar o pedido?")
                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .setPositiveButton("Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Call<Pedido> call = apiDeliveryCaseiroPedido.fazerPedido(pedido);

                                call.enqueue(new Callback<Pedido>() {
                                    @Override
                                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                                        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PratoPedidoActivity.class, "Pedido realizado: " + pedido.toString());
                                        ToastHelper.toastShort(getBaseContext(), "O pedido foi realizado!");
                                    }

                                    @Override
                                    public void onFailure(Call<Pedido> call, Throwable t) {
                                        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PratoPedidoActivity.class, "Pedido não foi realizado!");
                                    }
                                });
                            }
                        })
                .setNegativeButton("Não", null).show();
    }

}
