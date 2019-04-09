package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static String mCurrentPhotoPath;

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

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else dispatchTakePictureIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) dispatchTakePictureIntent();
                else ToastHelper.toastShort(UsuarioPerfilActivity.this, "É necessário dar a permissão para tirar a foto!");
                return;
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile("PHOTOAPP", ".jpg", storageDir);
                mCurrentPhotoPath = "file:" + photoFile.getAbsolutePath();
            }
            catch(IOException ex){
                ToastHelper.toastShort(UsuarioPerfilActivity.this, "Erro ao tirar a foto");
            }

            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            DeliveryApplication.usuarioLogado.setImagem(mCurrentPhotoPath);
            Picasso.get().load(new File(mCurrentPhotoPath)).transform(new CircleTransform()).into(appCompatImageView);
        }
    }

    @OnClick(R.id.activityClientePerfilAlterarFoto)
    public void cardViewAlterarFoto() {
        getPermissions();
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
