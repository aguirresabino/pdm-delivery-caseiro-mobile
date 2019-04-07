package io.github.aguirresabino.deliverycaseiro.model.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroUsuario;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.MainActivity;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioService {

    private Context context;
    private APIDeliveryCaseiroUsuario apiDeliveryCaseiroUsuario;

    public UsuarioService(Context context) {
        this.context = context;
        apiDeliveryCaseiroUsuario = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroUsuario();
    }

    public void login(String email, String senha) {
        Call<List<Usuario>> call = apiDeliveryCaseiroUsuario.login(email, senha);

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> usuarios = response.body();
                Usuario usuario = null;
                if(usuarios != null && !usuarios.isEmpty()) {
                    MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), UsuarioService.class, "Usuário encontrado: " + usuarios.get(0).toString());
                    usuario = usuarios.get(0);
                }
                else
                    MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), UsuarioService.class, "Usuário não existe");

                // Enviando o usuário em broadcast para a tela de login
                Intent intent = new Intent(LoginActivity.LocalBroadcastReceiver.LOCAL_BROADCAST_LOGIN_ACTIVITY);
                intent.putExtra("usuario.service.login", usuario);
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(intent);
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                MyLogger.logError(ValuesApplicationEnum.MY_TAG.getValue(), LoginActivity.class, "Erro na requisição: " + t.getMessage());
                // Enviando o usuário em broadcast para a tela de login
                Intent intent = new Intent(LoginActivity.LocalBroadcastReceiver.LOCAL_BROADCAST_LOGIN_ACTIVITY);
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(intent);
            }
        });
    }
}
