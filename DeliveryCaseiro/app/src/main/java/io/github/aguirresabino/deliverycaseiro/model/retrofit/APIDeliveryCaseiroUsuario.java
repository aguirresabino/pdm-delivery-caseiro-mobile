package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import java.util.List;

import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIDeliveryCaseiroUsuario {
    @POST("usuarios")
    Call<Usuario> create(@Body Usuario usuario);

    @GET("usuarios")
    Call<List<Usuario>> login(@Query("email") String email, @Query("senha") String senha);

    @PUT("usuarios/{id}")
    Call<Usuario> update(@Path("id") String id, @Body Usuario usuario);

    @DELETE("usuarios/{id}")
    Call<Usuario> delete(@Path("id") String id);
}
