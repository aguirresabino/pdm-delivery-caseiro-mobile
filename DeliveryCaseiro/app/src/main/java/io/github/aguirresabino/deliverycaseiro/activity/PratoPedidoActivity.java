package io.github.aguirresabino.deliverycaseiro.activity;

import android.os.Bundle;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.base.BaseActivity;

public class PratoPedidoActivity extends BaseActivity {

    private final String TAG = getClass().getName() + " ESPECIFICA ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_pedido);
    }
}
