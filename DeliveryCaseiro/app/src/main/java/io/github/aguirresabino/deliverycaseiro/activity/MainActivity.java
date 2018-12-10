package io.github.aguirresabino.deliverycaseiro.activity;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.base.BaseActivity;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
