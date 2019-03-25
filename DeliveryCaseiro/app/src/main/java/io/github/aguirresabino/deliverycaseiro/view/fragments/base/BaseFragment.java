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
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;

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
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onAttach() chamado " + context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onCreate() chamado " + savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onCreateView() chamado " + inflater + container + savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onActivityCreated() chamado " + savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onStart() chamado ");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onResume() chamado ");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onPause() chamado ");
    }

    @Override
    public void onStop() {
        super.onStop();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onStop() chamado ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onDestroyView() chamado ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onDestroy() chamado ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), getClass(), " onDetach() chamado ");
    }
}
