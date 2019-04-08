package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import java.util.List;

import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIDeliveryCaseiroPedido {

    @POST("pedidos")
    Call<Pedido> create(@Body Pedido pedido);

    @GET("pedidos")
    Call<List<Pedido>> readByUsuario(@Query("idUsuario") String id);

    @PUT("pedidos/{id}")
    Call<Pedido> update(@Path("id") String id, @Body Pedido pedido);

}
