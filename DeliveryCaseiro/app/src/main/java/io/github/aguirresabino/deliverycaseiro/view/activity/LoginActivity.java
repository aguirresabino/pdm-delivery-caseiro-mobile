package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIClientDeliveryCaserio;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroServiceI;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
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
        //
        apiDeliveryCaseiroServiceI = APIClientDeliveryCaserio.getClient().create(APIDeliveryCaseiroServiceI.class);
    }

    // Implementações das ações que os botões deverão executar ao serem clicados.

    @OnClick(R.id.activityLoginButtonEntrar)
    public void btEntrarListener(){
        btEntrar.setText(R.string.entrar_aguarde);
        btEntrar.setEnabled(false);

        Call<Usuario> call = apiDeliveryCaseiroServiceI.login(email.getEditText().getText().toString(),
                                                            senha.getEditText().getText().toString());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Clicou no botão LOGIN   | Thread: " + Thread.currentThread().getName());
                //
                Usuario usuario = response.body();
                if(usuario != null) {
                    MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Usuário encontrado: " + usuario.toString());
                    // TODO Utilizar outra forma de manter o usuário na sessão do aplicativo.
                    DeliveryApplication.usuarioLogado = usuario;
                    // Restaurando o botão Entrar
                    btEntrar.setEnabled(true);
                    btEntrar.setText(R.string.entrar);
                    // Iniciando MainActivity
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Usuário não existe");
                    btEntrar.setEnabled(true);
                    btEntrar.setText(R.string.entrar);
                    ToastHelper.toastShort(getBaseContext(), "Usuário não encontrado!");
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                MyLogger.logError(DeliveryApplication.MY_TAG, LoginActivity.class, "Erro na requisição: " + t.getMessage());
                ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");
            }
        });
    }
    @OnClick(R.id.activityLoginButtonCrieSuaConta)
    public void btCadastrarListener(){
        Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
        startActivity(intent);
    }
}
