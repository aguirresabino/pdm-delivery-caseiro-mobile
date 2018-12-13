package io.github.aguirresabino.deliverycaseiro.activity.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

/**
 * BaseActivity é um objeto que implementa o LifeCycle da Activity e outras lógicas
 * que serão herdadas por Activities da aplicação (reuso).
 */
public class BaseActivity extends AppCompatActivity {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onCreate() chamado " + savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MyLogger.logInfo(this.TAG, getClass(), "onStart() chamado");
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLogger.logInfo(this.TAG, getClass(), "onResume() chamado");
    }

    @Override
    protected void onPause() {
        super.onPause();

        MyLogger.logInfo(this.TAG, getClass(), "onPause() chamado");
    }

    @Override
    protected void onStop() {
        super.onStop();

        MyLogger.logInfo(this.TAG, getClass(), "onStop() chamado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyLogger.logInfo(this.TAG, getClass(), "onDestroy() chamado");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyLogger.logInfo(this.TAG, getClass(), "onRestart() chamado");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        MyLogger.logInfo(this.TAG, getClass(), "onSaveInstanceState() chamado " + outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onRestoreInstanceState() chamado " + savedInstanceState);

        super.onRestoreInstanceState(savedInstanceState);
    }
}
