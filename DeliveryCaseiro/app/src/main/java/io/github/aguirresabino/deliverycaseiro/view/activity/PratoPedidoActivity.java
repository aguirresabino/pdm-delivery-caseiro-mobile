package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import io.github.aguirresabino.deliverycaseiro.model.services.PedidoService;
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

    private PedidoService pedidoService;
    private LocalBroadcastReceiver localBroadcastReceiver;
    private Prato prato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_pedido);
        //
        ButterKnife.bind(this);
        //
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(PratoPedidoActivity.this).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_RECEIVER_PRATO_PEDIDO_ACTIVITY));

        pedidoService = new PedidoService(PratoPedidoActivity.this);
        prato = getIntent().getParcelableExtra("prato");

        // Carregando a imagem do prato
        Picasso.get().load(prato.getImagem()).into(imagem);

        toolbar.setTitle(prato.getNome());
        setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        descricao.setText(prato.getDescricao());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(PratoPedidoActivity.this).unregisterReceiver(localBroadcastReceiver);
    }

    private void fazerPedido() {
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

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(getString(R.string.deseja_finalizar_o_pedido))
                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .setPositiveButton(getString(R.string.sim),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pedidoService.create(pedido);
                            }
                        })
                .setNegativeButton(getString(R.string.nao), null)
                .show();
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {
        public static final String LOCAL_BROADCAST_RECEIVER_PRATO_PEDIDO_ACTIVITY = "local.broadcast.receiver.prato.pedido.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successMessage = (boolean) intent.getBooleanExtra("pedido.service.create", false);

            if(successMessage) ToastHelper.toastShort(PratoPedidoActivity.this, "O pedido foi realizado!");
            else ToastHelper.toastShort(PratoPedidoActivity.this, "Erro durante o pedido! Tente novamente mais tarde.");
        }
    }

}
