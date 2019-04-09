package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import io.github.aguirresabino.deliverycaseiro.model.services.UsuarioService;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.activityLoginButtonEntrar) AppCompatButton btEntrar;
    @BindView(R.id.activityLoginButtonCrieSuaConta) AppCompatButton btCadastrar;
    @BindView(R.id.activityLoginInputUser) TextInputLayout email;
    @BindView(R.id.activityLoginInputPassword) TextInputLayout senha;

    private LocalBroadcastReceiver localBroadcastReceiver;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ButterKnife
        ButterKnife.bind(this);
        //
        usuarioService = new UsuarioService(LoginActivity.this);
        // Registrando BroadcastReceiver
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(LoginActivity.this).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_LOGIN_ACTIVITY));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Removendo registro do BroadcastReceiver
        LocalBroadcastManager.getInstance(LoginActivity.this).unregisterReceiver(localBroadcastReceiver);
    }

    // Implementações das ações que os botões deverão executar ao serem clicados.

    @OnClick(R.id.activityLoginButtonEntrar)
    public void btEntrarListener(){
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Clicou no botão LOGIN   | Thread: " + Thread.currentThread().getName());
        // Desativando botão Entrar
        btEntrar.setEnabled(false);
        btEntrar.setText(R.string.entrar_aguarde);
        // Executando método de login de UsuarioService
        usuarioService.login(email.getEditText().getText().toString(), senha.getEditText().getText().toString());
    }
    @OnClick(R.id.activityLoginButtonCrieSuaConta)
    public void btCadastrarListener(){
        Intent intent = new Intent(getBaseContext(), CadastroActivity.class);
        startActivity(intent);
    }

    // Local BroadcastReceiver

    public class LocalBroadcastReceiver extends android.content.BroadcastReceiver {

        public static final String LOCAL_BROADCAST_LOGIN_ACTIVITY = "local.broadcast.login.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            Usuario usuario = intent.getParcelableExtra("usuario.service.login");

            if(usuario != null) {
                DeliveryApplication.usuarioLogado = usuario;
//                Iniciando MainActivity
                intent = new Intent(context, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            } else if(usuario == null){
                ToastHelper.toastShort(getBaseContext(), "Usuário não encontrado!");
            } else ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");

            btEntrar.setEnabled(true);
            btEntrar.setText(R.string.entrar);
        }
    }
}
