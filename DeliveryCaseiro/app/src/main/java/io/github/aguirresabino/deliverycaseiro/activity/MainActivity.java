package io.github.aguirresabino.deliverycaseiro.activity;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.fragments.InitialFragment;

import android.os.Bundle;

public class MainActivity extends BaseActivity {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerMainActivity, new InitialFragment())
                    .commit();
        }
    }
}
