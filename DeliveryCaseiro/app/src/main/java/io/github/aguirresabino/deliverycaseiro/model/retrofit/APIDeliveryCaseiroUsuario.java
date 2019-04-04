package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import java.util.List;

import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIDeliveryCaseiroUsuario {
    @POST("usuarios")
    Call<Usuario> create(@Body Usuario usuario);

    @FormUrlEncoded
    @POST("login")
    Call<Usuario> login(@Field("email") String email, @Field("senha") String senha);

    @PUT("usuarios/{id}")
    Call<Usuario> update(@Path("id") String id, @Body Usuario usuario);

    @DELETE("usuarios/{id}")
    Call<Usuario> delete(@Path("id") String id);

    @GET("usuario/pedidos/{idUsuario}")
    Call<List<Pedido>> getPedidosUsuario(@Path("idUsuario") String id);
}
