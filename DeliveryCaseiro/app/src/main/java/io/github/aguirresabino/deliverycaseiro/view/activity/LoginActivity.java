package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIClientDeliveryCaserio;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroServiceI;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.activityLoginButtonEntrar) AppCompatButton btEntrar;
    @BindView(R.id.activityLoginButtonCrieSuaConta) AppCompatButton btCadastrar;
    @BindView(R.id.activityLoginInputUser) TextInputLayout email;
    @BindView(R.id.activityLoginInputPassword) TextInputLayout senha;

    private APIDeliveryCaseiroServiceI apiDeliveryCaseiroServiceI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        ButterKnife.bind(this);
        apiDeliveryCaseiroServiceI = APIClientDeliveryCaserio.getClient().create(APIDeliveryCaseiroServiceI.class);
    }

    // Implementações das ações que os botões deverão executar ao serem clicados.

    @OnClick(R.id.activityLoginButtonEntrar)
    public void btEntrarListener(){

        Log.i(DeliveryApplication.MY_TAG, "Clicou no botão LOGIN");

        Call<Usuario> call = apiDeliveryCaseiroServiceI.login(email.getEditText().getText().toString(),
                                                            senha.getEditText().getText().toString());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuario = response.body();
                if(usuario != null) {
                    Log.i(DeliveryApplication.MY_TAG, usuario.toString());
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                else Log.i(DeliveryApplication.MY_TAG, "Usuário não existe");
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
    @OnClick(R.id.activityLoginButtonCrieSuaConta)
    public void btCadastrarListener(){
        Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
        startActivity(intent);
    }
}
