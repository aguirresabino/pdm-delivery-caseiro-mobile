package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import java.util.List;

import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIDeliveryCaseiroChefe {
    @GET("chefes")
    Call<List<Chefe>> readByCep(@Query("endereco.cep") String cep);
}
