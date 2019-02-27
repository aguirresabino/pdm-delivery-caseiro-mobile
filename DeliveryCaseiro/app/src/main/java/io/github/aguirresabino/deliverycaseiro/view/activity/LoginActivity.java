package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIClientDeliveryCaserio;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroServiceI;
import io.github.aguirresabino.deliverycaseiro.model.services.LoginIntentService;
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

    private LocalBroadcastLoginIntentService localBroadcastLoginIntentService;

//    private APIDeliveryCaseiroServiceI apiDeliveryCaseiroServiceI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //
        ButterKnife.bind(this);
        //
//        apiDeliveryCaseiroServiceI = APIClientDeliveryCaserio.getClient().create(APIDeliveryCaseiroServiceI.class);

        localBroadcastLoginIntentService = new LocalBroadcastLoginIntentService();
        LocalBroadcastManager.getInstance(LoginActivity.this).registerReceiver(localBroadcastLoginIntentService, new IntentFilter(LoginIntentService.FILTER_ACTION_LOGIN_INTENT_SERVICE));
    }

    // Implementações das ações que os botões deverão executar ao serem clicados.

    @OnClick(R.id.activityLoginButtonEntrar)
    public void btEntrarListener(){
        btEntrar.setText(R.string.entrar_aguarde);
        btEntrar.setEnabled(false);

        Intent intent = new Intent(LoginActivity.this, LoginIntentService.class);
        intent.putExtra("email", email.getEditText().getText().toString());
        intent.putExtra("senha", senha.getEditText().getText().toString());

        startService(intent);

//        Call<Usuario> call = apiDeliveryCaseiroServiceI.login(email.getEditText().getText().toString(),
//                                                            senha.getEditText().getText().toString());

//        call.enqueue(new Callback<Usuario>() {
//            @Override
//            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//                MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Clicou no botão LOGIN   | Thread: " + Thread.currentThread().getName());
//                //
//                Usuario usuario = response.body();
//                if(usuario != null) {
//                    MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Usuário encontrado: " + usuario.toString());
//                    // TODO Utilizar outra forma de manter o usuário na sessão do aplicativo.
//                    DeliveryApplication.usuarioLogado = usuario;
//                    // Iniciando MainActivity
//                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                    startActivity(intent);
//                }
//                else {
//                    MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Usuário não existe");
//                    btEntrar.setEnabled(true);
//                    btEntrar.setText(R.string.entrar);
//                    ToastHelper.toastShort(getBaseContext(), "Usuário não encontrado!");
//                }
//            }
//            @Override
//            public void onFailure(Call<Usuario> call, Throwable t) {
//                btEntrar.setEnabled(true);
//                btEntrar.setText(R.string.entrar);
//                MyLogger.logError(DeliveryApplication.MY_TAG, LoginActivity.class, "Erro na requisição: " + t.getMessage());
//                ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");
//            }
//        });
    }
    @OnClick(R.id.activityLoginButtonCrieSuaConta)
    public void btCadastrarListener(){
        Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
        startActivity(intent);
    }

    private class LocalBroadcastLoginIntentService extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Executando onReceive(" + context.toString() + " " + intent.toString() +" )");
            btEntrar.setEnabled(true);
            btEntrar.setText(R.string.entrar);
            Usuario usuario = intent.getParcelableExtra("usuarioLogado");
            DeliveryApplication.usuarioLogado = usuario;

            MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginActivity.class, "Executando Thread.sleep(2000);");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
