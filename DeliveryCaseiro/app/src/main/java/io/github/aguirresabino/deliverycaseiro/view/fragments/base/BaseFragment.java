package io.github.aguirresabino.deliverycaseiro.view.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

/**
 * BaseFragment é um objeto que implementa o LifeCycle do Fragment e outras lógicas
 * que serão herdadas por Fragments da aplicação (reuso).
 */
public class BaseFragment extends Fragment {

    protected BaseActivity activityContext = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //recupera activity do contexto
        this.activityContext = (BaseActivity) getActivity();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onAttach() chamado " + context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onCreate() chamado " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onCreateView() chamado " + inflater + container + savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onActivityCreated() chamado " + savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onStart() chamado ");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onResume() chamado ");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onPause() chamado ");
    }

    @Override
    public void onStop() {
        super.onStop();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onStop() chamado ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onDestroyView() chamado ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onDestroy() chamado ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onDetach() chamado ");
    }
}
