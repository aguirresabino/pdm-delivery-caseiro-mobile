package io.github.aguirresabino.deliverycaseiro.model.services;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroPedido;
import io.github.aguirresabino.deliverycaseiro.model.retrofit.APIDeliveryCaseiroRetrofitFactory;
import io.github.aguirresabino.deliverycaseiro.view.activity.AceitarPedidoCustomizadoActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.PratoPedidoActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.UsuarioPerfilActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabClientePedidosFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.TabPedidoCustomizadoFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoService {

    private APIDeliveryCaseiroPedido apiDeliveryCaseiroPedido;
    private Context context;

    public PedidoService(Context context) {
        this.context = context;
        apiDeliveryCaseiroPedido = APIDeliveryCaseiroRetrofitFactory.getApiDeliveryCaseiroPedido();
    }

    public void create(Pedido pedido) {
        Call<Pedido> call = apiDeliveryCaseiroPedido.create(pedido);
        Intent intent = new Intent(PratoPedidoActivity.LocalBroadcastReceiver.LOCAL_BROADCAST_RECEIVER_PRATO_PEDIDO_ACTIVITY);
        if(pedido.isPedidoCustomizado()) intent.setAction(TabPedidoCustomizadoFragment.LocalBroadcastReceiver.LOCAL_BROADCAST_TAB_PEDIDO_CUSTOMIZADO_FRAGMENT);

        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PedidoService.class, "Pedido realizado: " + pedido.toString());
                intent.putExtra("pedido.service.create", true);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PedidoService.class, "Pedido não foi realizado!");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

     public void readByUsuario(String id) {
         Call<List<Pedido>> call = apiDeliveryCaseiroPedido.readByUsuario(DeliveryApplication.usuarioLogado.getId());
         Intent intent = new Intent(TabClientePedidosFragment.LocalBroadcastReceiver.LOCAL_BROADCAST_TAB_CLIENTE_PEDIDOS_FRAGMENT);

         call.enqueue(new Callback<List<Pedido>>() {
             @Override
             public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                 MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), TabClientePedidosFragment.class, "Buscando pedidos do usuário!\n" + response.body());
                 List<Pedido> pedidos = response.body();
                 intent.putParcelableArrayListExtra("pedido.service.readByUsuario", (ArrayList<? extends Parcelable>) pedidos);
                 LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
             }

             @Override
             public void onFailure(Call<List<Pedido>> call, Throwable t) {
                 MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), TabClientePedidosFragment.class, "Erro ao buscar pedidos do usuário!");
                 LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
             }
         });
     }

    public void update(Pedido pedido) {
        Call<Pedido> call =  apiDeliveryCaseiroPedido.update(pedido.get_id(), pedido);
        Intent intent = new Intent(AceitarPedidoCustomizadoActivity.LocalBroadcastReceiver.LOCAL_BROADCAST_ACEITAR_PEDIDO_CUSTOMIZADO_ACEITO_ACTIVITY);

        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), UsuarioService.class, "Atualizando pedido: " + response.toString());
                Pedido pedidoReponse = response.body();
                intent.putExtra("pedido.service.update", pedidoReponse);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }
}
