package io.github.aguirresabino.deliverycaseiro.model.services;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroChefe;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabChefeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefeService {

    private Context context;
    private APIDeliveryCaseiroChefe apiDeliveryCaseiroChefe;

    public ChefeService(Context context) {
        this.context = context;
        apiDeliveryCaseiroChefe = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroChefe();
    }

    public void readByCep(String cep) {
        Call<List<Chefe>> call = apiDeliveryCaseiroChefe.readByCep(cep);
        Intent intent = new Intent(TabChefeFragment.LocalBroadcastReceiver.LOCAL_BROADCAST_TAB_CHEFE_FRAGMENT);
        //
        call.enqueue(new Callback<List<Chefe>>() {
            @Override
            public void onResponse(Call<List<Chefe>> call, Response<List<Chefe>> response) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), ChefeService.class, "Carregando todos os chefes do CEP " + cep);
                List<Chefe> chefes = response.body();
                intent.putParcelableArrayListExtra("chefe.service.readByCep", (ArrayList<? extends Parcelable>) chefes);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            @Override
            public void onFailure(Call<List<Chefe>> call, Throwable t) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), ChefeService.class, "Erro de requisição ao tentar buscar todos os chefes do CEP " + cep);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }
}
