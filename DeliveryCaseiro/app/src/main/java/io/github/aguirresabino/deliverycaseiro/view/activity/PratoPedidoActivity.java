package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class PratoPedidoActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_pedido);
        //
        ButterKnife.bind(this);
        //TODO Colocar nome do prato.
        toolbar.setTitle("Nome do Prato...");
        setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                ToastHelper.toastShort(PratoPedidoActivity.this, "Clicou");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
