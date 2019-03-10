package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Endereco;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroUsuario;
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

    private APIDeliveryCaseiroUsuario apiDeliveryCaseiroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //
        ButterKnife.bind(this);

        apiDeliveryCaseiroUsuario = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroUsuario();
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

        MyLogger.logInfo(DeliveryApplication.MY_TAG, CadastroActivity.class, usuario.toString());

        cadastrar.setEnabled(false);
        cancelar.setEnabled(false);
        cadastrar.setText("Aguarde...");

        Call<Usuario> call =  apiDeliveryCaseiroUsuario.create(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, CadastroActivity.class, "Usuário cadastrado: " + usuario.toString());
                //
                cadastrar.setText("Usuário cadastrado!");
                cancelar.setText("Voltar");
                cancelar.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, CadastroActivity.class, "Erro no cadastro do usuário!");
                ToastHelper.toastShort(getBaseContext(), "Tente novamente mais tarde!");
                cadastrar.setEnabled(true);
                cancelar.setEnabled(true);
                cadastrar.setText(R.string.cadastrar);
            }
        });
    }

    @OnClick(R.id.activityCadastroButtonCancelar)
    public void btnCancelar() {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, CadastroActivity.class, "Botão Cancelar clicado.");
        startActivity(new Intent(this, LoginActivity.class));
    }
}
