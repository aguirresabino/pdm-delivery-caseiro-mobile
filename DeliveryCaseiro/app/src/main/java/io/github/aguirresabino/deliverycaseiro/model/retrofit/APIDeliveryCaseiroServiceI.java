package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIDeliveryCaseiroServiceI {
    @POST("usuarios")
    public void create(@Body Usuario usuario);

    @FormUrlEncoded
    @POST("login")
    public Call<Usuario> login(@Field("email") String email, @Field("senha") String senha);
}
