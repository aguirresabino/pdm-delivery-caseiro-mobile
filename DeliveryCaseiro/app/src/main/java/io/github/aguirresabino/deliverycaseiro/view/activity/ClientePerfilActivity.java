package io.github.aguirresabino.deliverycaseiro.view.activity;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroUsuario;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;

public class ClientePerfilActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityClientePerfilInputLayoutEmail) TextInputLayout email;
    @BindView(R.id.activityClientePerfilInputLayoutNome) TextInputLayout nome;
    @BindView(R.id.activityClientePerfilInputLayoutSenha) TextInputLayout senha;
    @BindView(R.id.activityClientePerfilInputLayoutTelefone) TextInputLayout telefone;
    @BindView(R.id.activityClientePerfilButtonAtualizar) AppCompatButton atualizar;
    @BindView(R.id.activityClientePerfilButtonDeletar) AppCompatButton deletar;

    private APIDeliveryCaseiroUsuario apiDeliveryCaseiroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_perfil);
        // ButterKnife
        ButterKnife.bind(this);
        //
        toolbar.setTitle(R.string.perfil);
        setUpToolbar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        apiDeliveryCaseiroUsuario = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroUsuario();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Inicializando inputs do formulário com os dados do usuário logado.
        initInputs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //se clicar no botão voltar, a activity atual é finalizada
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.activityClientePerfilButtonAtualizar)
    public void btnAtualizar() {

        atualizar.setEnabled(false);
        atualizar.setText("Altualizando dados...");

        DeliveryApplication.usuarioLogado.setEmail(email.getEditText().getText().toString());
        DeliveryApplication.usuarioLogado.setNome(nome.getEditText().getText().toString());
        DeliveryApplication.usuarioLogado.setSenha(senha.getEditText().getText().toString());
        DeliveryApplication.usuarioLogado.setTelefone(telefone.getEditText().getText().toString());

        Call<Usuario> call = apiDeliveryCaseiroUsuario.update(DeliveryApplication.usuarioLogado.getId(), DeliveryApplication.usuarioLogado);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                ToastHelper.toastShort(ClientePerfilActivity.this, "Usuário atualizado!");
                atualizar.setEnabled(true);
                atualizar.setText(R.string.atualizar);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                ToastHelper.toastShort(ClientePerfilActivity.this, "Erro durante atualização! Tente novamente mais tarde.");
                atualizar.setEnabled(true);
                atualizar.setText(R.string.atualizar);
            }
        });

        MyLogger.logInfo(DeliveryApplication.MY_TAG, ClientePerfilActivity.class, "Atualizando usuário: " + DeliveryApplication.usuarioLogado.toString());
    }

    @OnClick(R.id.activityClientePerfilButtonDeletar)
    public void btnDeletar() {
        deletar.setEnabled(false);
        deletar.setText("Aguarde...");

        Call<Usuario> call = apiDeliveryCaseiroUsuario.delete(DeliveryApplication.usuarioLogado.getId());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, ClientePerfilActivity.class, "Deletando usuário: " + response.toString());
                DeliveryApplication.usuarioLogado = null;
                startActivity(new Intent(ClientePerfilActivity.this, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                ToastHelper.toastShort(ClientePerfilActivity.this, "Erro durante exclusão! Tente novamente mais tarde.");
                deletar.setEnabled(true);
                deletar.setText(R.string.deletar);
            }
        });
    }

    private void initInputs() {
        nome.getEditText().setText(DeliveryApplication.usuarioLogado.getNome());
        email.getEditText().setText(DeliveryApplication.usuarioLogado.getEmail());
        senha.getEditText().setText(DeliveryApplication.usuarioLogado.getSenha());
        telefone.getEditText().setText(DeliveryApplication.usuarioLogado.getTelefone());
    }
}
