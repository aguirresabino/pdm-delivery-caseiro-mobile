package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Endereco;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroUsuario;
import io.github.aguirresabino.deliverycaseiro.model.services.UsuarioService;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends BaseActivity {

    @BindView(R.id.activityCadastroButtonCadastrar) Button cadastrar;
    @BindView(R.id.activityCadastroButtonCancelar) Button cancelar;
    @BindView(R.id.activityCadastroInputEmail) TextInputLayout email;
    @BindView(R.id.activityCadastroInputNome) TextInputLayout nome;
    @BindView(R.id.activityCadastroInputSenha) TextInputLayout senha;
    @BindView(R.id.activityCadastroInputTelefone) TextInputLayout telefone;
    @BindView(R.id.activityCadastroInputCep) TextInputLayout cep;

    private UsuarioService usuarioService;
    private LocalBroadcastReceiver localBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //
        ButterKnife.bind(this);
        //
        usuarioService = new UsuarioService(CadastroActivity.this);
        //
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(CadastroActivity.this).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_RECEIVER_CADASTRO_ACTIVITY));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(CadastroActivity.this).unregisterReceiver(localBroadcastReceiver);
    }

    @OnClick(R.id.activityCadastroButtonCadastrar)
    public void btnCadastrar() {
        Usuario usuario = new Usuario();
        usuario.setNome(nome.getEditText().getText().toString());
        usuario.setEmail(email.getEditText().getText().toString());
        usuario.setTelefone(telefone.getEditText().getText().toString());
        usuario.setSenha(senha.getEditText().getText().toString());
        Endereco endereco = new Endereco();
        endereco.setCep(cep.getEditText().getText().toString());
        usuario.setEndereco(endereco);

        cadastrar.setEnabled(false);
        cancelar.setEnabled(false);
        //TODO Alterar isto para alguma coisa que mostre um loading.
        cadastrar.setText(R.string.entrar_aguarde);

        usuarioService.create(usuario);
    }

    @OnClick(R.id.activityCadastroButtonCancelar)
    public void btnCancelar() {
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), CadastroActivity.class, "Botão Cancelar clicado.");
        startActivity(new Intent(this, LoginActivity.class));
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {

        public static final String LOCAL_BROADCAST_RECEIVER_CADASTRO_ACTIVITY = "local.broadcast.receiver.cadastro.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successMessage = (boolean) intent.getBooleanExtra("usuario.service.create", false);

            if(successMessage) {
                // TODO Exibir dialog solicitando o login
                cadastrar.setText(getString(R.string.usuario_cadastrado));
                cancelar.setText(getString(R.string.voltar));
                cancelar.setEnabled(true);
            } else {
                cadastrar.setText(R.string.cadastrar);
                cadastrar.setEnabled(true);
                cancelar.setEnabled(true);
                ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");
            }
        }
    }
}
