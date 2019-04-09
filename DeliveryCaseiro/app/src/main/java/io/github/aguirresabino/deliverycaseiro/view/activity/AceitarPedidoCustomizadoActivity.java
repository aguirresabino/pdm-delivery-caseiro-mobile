package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.enums.StatusPedidoEnum;
import io.github.aguirresabino.deliverycaseiro.model.services.PedidoService;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class AceitarPedidoCustomizadoActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityAceitarPedidoCustomizadoAppCompatImageView) AppCompatImageView appCompatImageView;
    @BindView(R.id.activityAceitarPedidoCustomizadoTextViewNomeChefe) AppCompatTextView nomeChefe;
    @BindView(R.id.activityAceitarPedidoCustomizadoTextViewQuantidadePratos) AppCompatTextView quantidadePratos;
    @BindView(R.id.activityAceitarPedidoCustomizadoTextViewValorCobrado) AppCompatTextView valorCobrado;

    private PedidoService pedidoService;
    private LocalBroadcastReceiver localBroadcastReceiver;
    private Pedido pedido;
    private Integer chefeSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceitar_pedido_customizado);
        // ButterKnife
        ButterKnife.bind(this);
        //
        chefeSelecionado = getIntent().getIntExtra("chefe.selecionado", 0);
        pedido = getIntent().getParcelableExtra("pedido");
        //
        pedidoService = new PedidoService(AceitarPedidoCustomizadoActivity.this);
        //
        toolbar.setTitle(R.string.pedido_customizado);
        setUpToolbar(toolbar);
        //utilizando botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        Picasso.get().load(pedido.getChefesDoPedidoCustomizado().get(chefeSelecionado).getImagem()).into(appCompatImageView);
        nomeChefe.setText(getString(R.string.nome_chefe_espaco) + pedido.getChefesDoPedidoCustomizado().get(chefeSelecionado).getNome());
        quantidadePratos.setText(getString(R.string.quantidade_pratos_espaco) + pedido.getItens().get(0).getQuantidade());
        valorCobrado.setText(getString(R.string.valor_cobrado_por_prato_espaco) + " " + pedido.getChefesDoPedidoCustomizado().get(chefeSelecionado).getValor());
        //
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(AceitarPedidoCustomizadoActivity.this).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_ACEITAR_PEDIDO_CUSTOMIZADO_ACEITO_ACTIVITY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_aceitar_pedido_customizado_menu, menu);
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
        LocalBroadcastManager.getInstance(AceitarPedidoCustomizadoActivity.this).unregisterReceiver(localBroadcastReceiver);
    }

    private void fazerPedido() {
        pedido.getItens().get(0).setValor(pedido.getChefesDoPedidoCustomizado().get(chefeSelecionado).getValor());
        pedido.setValor(String.valueOf(Integer.valueOf(pedido.getItens().get(0).getValor()) * Integer.valueOf(pedido.getItens().get(0).getQuantidade())));
        pedido.setIdFornecedor(pedido.getChefesDoPedidoCustomizado().get(chefeSelecionado).getId());
        pedido.setStatus(StatusPedidoEnum.PEDIDO_EM_PREPARO.getValue());

        new AlertDialog.Builder(AceitarPedidoCustomizadoActivity.this)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Deseja finalizar o pedido?")
//                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .setPositiveButton(R.string.sim,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pedidoService.update(pedido);
                            }
                        })
                .setNegativeButton(R.string.nao, null).show();
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {

        public static final String LOCAL_BROADCAST_ACEITAR_PEDIDO_CUSTOMIZADO_ACEITO_ACTIVITY = "local.broadcast.aceitar.pedido.customizado.aceito.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            Pedido pedido = intent.getParcelableExtra("pedido.service.update");
            if(pedido != null) {
                ToastHelper.toastShort(AceitarPedidoCustomizadoActivity.this, "O pedido foi realizado!");
                intent = new Intent(AceitarPedidoCustomizadoActivity.this, MainActivity.class);
                startActivity(intent);
            } else ToastHelper.toastShort(AceitarPedidoCustomizadoActivity.this, "Erro durante o pedido! Tente novamente mais tarde.");
        }
    }
}
