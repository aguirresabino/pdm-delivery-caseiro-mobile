package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import io.github.aguirresabino.deliverycaseiro.view.transform.CircleTransform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioPerfilActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityClientePerfilInputLayoutEmail) TextInputLayout email;
    @BindView(R.id.activityClientePerfilInputLayoutNome) TextInputLayout nome;
    @BindView(R.id.activityClientePerfilInputLayoutSenha) TextInputLayout senha;
    @BindView(R.id.activityClientePerfilInputLayoutTelefone) TextInputLayout telefone;
    @BindView(R.id.activityClientePerfilButtonDeletar) AppCompatButton deletar;
    @BindView(R.id.activityClientePerfilAppCompatImageView) AppCompatImageView appCompatImageView;
    @BindView(R.id.activityClientePerfilAlterarFoto) CardView cardAlterarFoto;

    private UsuarioService usuarioService;
    private UsuarioPerfilActivity.LocalBroadcastReceiverUpdate localBroadcastReceiverUpdate;
    private UsuarioPerfilActivity.LocalBroadcastReceiverDelete localBroadcastReceiverDelete;

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
        // Registrando BroadcastReceiver
        localBroadcastReceiverUpdate = new UsuarioPerfilActivity.LocalBroadcastReceiverUpdate();
        localBroadcastReceiverDelete = new UsuarioPerfilActivity.LocalBroadcastReceiverDelete();
        LocalBroadcastManager.getInstance(UsuarioPerfilActivity.this).registerReceiver(localBroadcastReceiverUpdate, new IntentFilter(UsuarioPerfilActivity.LocalBroadcastReceiverUpdate.LOCAL_BROADCAST_UPDATE_USUARIO_PERFIL_ACTIVITY));
        LocalBroadcastManager.getInstance(UsuarioPerfilActivity.this).registerReceiver(localBroadcastReceiverDelete, new IntentFilter(UsuarioPerfilActivity.LocalBroadcastReceiverDelete.LOCAL_BROADCAST_DELETE_USUARIO_PERFIL_ACTIVITY));
        usuarioService = new UsuarioService(UsuarioPerfilActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Inicializando inputs do formulário com os dados do usuário logado.
        initInputs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_prato_pedido_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //se clicar no botão voltar, a activity atual é finalizada
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_ok:
                this.atualizarPerfil();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(UsuarioPerfilActivity.this).unregisterReceiver(localBroadcastReceiverUpdate);
        LocalBroadcastManager.getInstance(UsuarioPerfilActivity.this).unregisterReceiver(localBroadcastReceiverDelete);
    }

    @OnClick(R.id.activityClientePerfilAlterarFoto)
    public void setCardAlterarFoto() {
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), UsuarioPerfilActivity.class, "Clicou no CardView Alterar Foto");
    }

    @OnClick(R.id.activityClientePerfilButtonDeletar)
    public void btnDeletar() {
        deletar.setEnabled(false);
        deletar.setText(R.string.entrar_aguarde);
        //
        usuarioService.delete(DeliveryApplication.usuarioLogado);
    }

    private void atualizarPerfil() {
        Usuario usuarioUpdate = DeliveryApplication.usuarioLogado;
        usuarioUpdate.setEmail(email.getEditText().getText().toString());
        usuarioUpdate.setNome(nome.getEditText().getText().toString());
        usuarioUpdate.setSenha(senha.getEditText().getText().toString());
        usuarioUpdate.setTelefone(telefone.getEditText().getText().toString());
        //
        usuarioService.update(usuarioUpdate);
    }

    private void initInputs() {
        Picasso.get().load(DeliveryApplication.usuarioLogado.getImagem()).transform(new CircleTransform()).into(appCompatImageView);
        nome.getEditText().setText(DeliveryApplication.usuarioLogado.getNome());
        email.getEditText().setText(DeliveryApplication.usuarioLogado.getEmail());
        senha.getEditText().setText(DeliveryApplication.usuarioLogado.getSenha());
        telefone.getEditText().setText(DeliveryApplication.usuarioLogado.getTelefone());
    }

    // Local BroadcastReceiver

    public class LocalBroadcastReceiverUpdate extends android.content.BroadcastReceiver {

        public static final String LOCAL_BROADCAST_UPDATE_USUARIO_PERFIL_ACTIVITY = "local.broadcast.update.usuario.perfil.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            Usuario usuario = intent.getParcelableExtra("usuario.service.update");

            if(usuario != null) {
                DeliveryApplication.usuarioLogado = usuario;
                ToastHelper.toastShort(UsuarioPerfilActivity.this, "Usuário atualizado!");
            } else ToastHelper.toastShort(UsuarioPerfilActivity.this, "Erro durante atualização! Tente novamente mais tarde.");
        }
    }

    public class LocalBroadcastReceiverDelete extends android.content.BroadcastReceiver {

        public static final String LOCAL_BROADCAST_DELETE_USUARIO_PERFIL_ACTIVITY = "local.broadcast.delete.usuario.perfil.activity";

        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean successMessage = intent.getBooleanExtra("usuario.service.delete", false);

            if(successMessage) {
                DeliveryApplication.usuarioLogado = null;
                UsuarioPerfilActivity.this.startActivity(new Intent(UsuarioPerfilActivity.this, LoginActivity.class));
            } else {
                ToastHelper.toastShort(UsuarioPerfilActivity.this, "Erro durante exclusão! Tente novamente mais tarde.");
                deletar.setEnabled(true);
                deletar.setText(R.string.deletar);
            }
        }
    }
}
