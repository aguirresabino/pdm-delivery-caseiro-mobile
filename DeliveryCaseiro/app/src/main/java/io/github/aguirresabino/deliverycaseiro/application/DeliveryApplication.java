package io.github.aguirresabino.deliverycaseiro.application;

import android.app.Application;

import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;

public class DeliveryApplication extends Application {

    public static Usuario usuarioLogado;

    @Override
    public void onCreate() {
        super.onCreate();
        //
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), "onCreate() chamado ");
    }
}
