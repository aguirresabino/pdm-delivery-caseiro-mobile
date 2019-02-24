package io.github.aguirresabino.deliverycaseiro.application;

import android.app.Application;

import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

public class DeliveryApplication extends Application {

    public static final String MY_TAG = "DELIVERY_CASEIRO_DEBUG";

    @Override
    public void onCreate() {
        super.onCreate();

        MyLogger.logInfo(MY_TAG, getClass(), " onCreate() chamado ");
    }
}
