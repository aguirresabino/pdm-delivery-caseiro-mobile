package io.github.aguirresabino.deliverycaseiro.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

/**
 * BaseFragment é um objeto que implementa o LifeCycle do Fragment e outras lógicas
 * que serão herdadas por Fragments da aplicação (reuso).
 */
public class BaseFragment extends Fragment {

    private AppCompatActivity activityContext = null;

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //recupera activity do contexto
        this.activityContext = (AppCompatActivity) getActivity();

        MyLogger.logInfo(this.TAG, getClass(), "onAttach() chamado " + context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onCreate() chamado " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onCreateView() chamado" + inflater + container + savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onActivityCreated() chamado" + savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        MyLogger.logInfo(this.TAG, getClass(), "onStart() chamado");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.logInfo(this.TAG, getClass(), "onResume() chamado");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.logInfo(this.TAG, getClass(), "onPause() chamado");
    }

    @Override
    public void onStop() {
        super.onStop();
        MyLogger.logInfo(this.TAG, getClass(), "onStop() chamado");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLogger.logInfo(this.TAG, getClass(), "onDestroyView() chamado");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLogger.logInfo(this.TAG, getClass(), "onDestroy() chamado");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLogger.logInfo(this.TAG, getClass(), "onDetach() chamado");
    }

    /**
     * Este método adiciona uma toolbar a activity do contexto.
     * @param toolbar O layout da toolbar
     * @param title O título que será utilizado na activity do contexto.
     */
    protected void setUpToolbar(Toolbar toolbar, String title){
        if(toolbar != null) {
            //AppCompatActivity activity = (AppCompatActivity) this.getActivity();
            this.activityContext.setSupportActionBar(toolbar);
        }
    }
}
