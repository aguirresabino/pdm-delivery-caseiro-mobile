package io.github.aguirresabino.deliverycaseiro.model.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginIntentService extends IntentService {

    public static final String FILTER_ACTION_LOGIN_INTENT_SERVICE = "filter.action.login.intent.service";

    public LoginIntentService() {
        super("LoginIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginIntentService.class, "Executando onHandleIntent( " + intent.toString() + " )");

        final String url = "https://comida-caseira.herokuapp.com/comida-caseira/login";
        final String email = intent.getStringExtra("email");
        final String senha = intent.getStringExtra("senha");


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("senha", senha)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {

            Response response = client.newCall(request).execute();
            Usuario cliente = new Gson().fromJson(response.body().string(), Usuario.class);

            MyLogger.logInfo(DeliveryApplication.MY_TAG, LoginIntentService.class, "Retornando Usuário como resposta: " + client.toString());

            intent.setAction(FILTER_ACTION_LOGIN_INTENT_SERVICE);
            intent.putExtra("usuarioLogado", cliente);
            LocalBroadcastManager.getInstance(LoginIntentService.this).sendBroadcast(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
