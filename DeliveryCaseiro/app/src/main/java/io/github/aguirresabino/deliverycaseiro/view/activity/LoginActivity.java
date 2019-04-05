package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroUsuario;
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

    private APIDeliveryCaseiroUsuario apiDeliveryCaseiroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ButterKnife
        ButterKnife.bind(this);
        //
        apiDeliveryCaseiroUsuario = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroUsuario();
    }

    // Implementações das ações que os botões deverão executar ao serem clicados.

    @OnClick(R.id.activityLoginButtonEntrar)
    public void btEntrarListener(){
        btEntrar.setText(R.string.entrar_aguarde);
        btEntrar.setEnabled(false);

        Call<List<Usuario>> call = apiDeliveryCaseiroUsuario.login(email.getEditText().getText().toString(),
                                                            senha.getEditText().getText().toString());

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Clicou no botão LOGIN   | Thread: " + Thread.currentThread().getName());
                //
                List<Usuario> usuarios = response.body();
                if(usuarios != null && !usuarios.isEmpty()) {
                    MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Usuário encontrado: " + usuarios.toString());
                    // TODO Utilizar outra forma de manter o usuário na sessão do aplicativo.
                    DeliveryApplication.usuarioLogado = usuarios.get(0);
                    // Iniciando MainActivity
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Usuário não existe");
                    btEntrar.setEnabled(true);
                    btEntrar.setText(R.string.entrar);
                    ToastHelper.toastShort(getBaseContext(), "Usuário não encontrado!");
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                btEntrar.setEnabled(true);
                btEntrar.setText(R.string.entrar);
                MyLogger.logError(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Erro na requisição: " + t.getMessage());
//                ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");
                ToastHelper.toastShort(getBaseContext(), call.toString());
            }
        });
    }
    @OnClick(R.id.activityLoginButtonCrieSuaConta)
    public void btCadastrarListener(){
        Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
        startActivity(intent);
    }
}
