package io.github.aguirresabino.deliverycaseiro.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.fragments.InitialFragment;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends BaseActivity {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName() + " ESPECIFICA ";
    private Fragment fragmentChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando o fragment InitialFragment
        if(savedInstanceState == null){
            fragmentChild = new InitialFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivityfragmentContainer, fragmentChild)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toolbar toolbar = findViewById(R.id.toolbar);

        setUpNavigationDrawer(toolbar);
    }
}
