package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
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
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientePerfilFragment extends BaseFragment {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fragmentClientePerfilInputLayoutEmail) TextInputLayout email;
    @BindView(R.id.fragmentClientePerfilInputLayoutNome) TextInputLayout nome;
    @BindView(R.id.fragmentClientePerfilInputLayoutSenha) TextInputLayout senha;
    @BindView(R.id.fragmentClientePerfilInputLayoutTelefone) TextInputLayout telefone;
    @BindView(R.id.fragmentClientePerfilButtonAtualizar) AppCompatButton atualizar;
    @BindView(R.id.fragmentClientePerfilButtonDeletar) AppCompatButton deletar;

    private APIDeliveryCaseiroUsuario apiDeliveryCaseiroUsuario;

    @Override
    public void onAttach(@NonNull Context context) {
        //Recuperando serviço para consumir API
        apiDeliveryCaseiroUsuario = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroUsuario();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflando o fragment e salvando na variavel view
        View view = inflater.inflate(R.layout.fragment_cliente_perfil, container, false);
        // ButterKnife
        ButterKnife.bind(this, view);
        //
        toolbar.setTitle(R.string.perfil);
        //Adicionando o toolbar a activity do contexto
        //A activity do contexto é recuperada e depois é utilizado o método setUpToolbar implementado em BaseActivity
        //A variável activityContext está definida em BaseFragment como protected. Ela é inicializada em onAttach, pois
        //neste momento do ciclo de vida do fragment, já podemos ter uma referência para a activity pai
        activityContext.setUpToolbar(toolbar);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Inicializando inputs do formulário com os dados do usuário logado.
        initInputs();
    }

    @OnClick(R.id.fragmentClientePerfilButtonAtualizar)
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
                ToastHelper.toastShort(getContext(), "Usuário atualizado!");
                atualizar.setEnabled(true);
                atualizar.setText(R.string.atualizar);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                ToastHelper.toastShort(getContext(), "Erro durante atualização! Tente novamente mais tarde.");
                atualizar.setEnabled(true);
                atualizar.setText(R.string.atualizar);
            }
        });

        MyLogger.logInfo(DeliveryApplication.MY_TAG, ClientePerfilFragment.class, "Atualizando usuário: " + DeliveryApplication.usuarioLogado.toString());
    }

    @OnClick(R.id.fragmentClientePerfilButtonDeletar)
    public void btnDeletar() {
        deletar.setEnabled(false);
        deletar.setText("Aguarde...");

        Call<Usuario> call = apiDeliveryCaseiroUsuario.delete(DeliveryApplication.usuarioLogado.getId());

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                MyLogger.logInfo(DeliveryApplication.MY_TAG, ClientePerfilFragment.class, "Deletando usuário: " + response.toString());
                DeliveryApplication.usuarioLogado = null;
                startActivity(new Intent(getContext(), LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                ToastHelper.toastShort(getContext(), "Erro durante exclusão! Tente novamente mais tarde.");
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
