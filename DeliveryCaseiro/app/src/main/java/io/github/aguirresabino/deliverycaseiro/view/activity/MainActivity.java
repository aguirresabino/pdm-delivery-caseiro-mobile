package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIClientDeliveryCaserio;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroServiceI;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.InitialFragment;
import retrofit2.Call;

public class MainActivity extends BaseActivity {

    private Fragment fragmentChild;
    @BindView(R.id.toolbar) @Nullable Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Inicializando o fragment InitialFragment
        if(savedInstanceState == null){
            fragmentChild = new InitialFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivityfragmentContainer, fragmentChild)
                    .commit();
        }

        MyLogger.logInfo(DeliveryApplication.MY_TAG, MainActivity.class, DeliveryApplication.usuarioLogado.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpNavigationDrawer(toolbar);
    }
}
