package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import java.util.List;

import io.github.aguirresabino.deliverycaseiro.model.entities.Chefe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIDeliveryCaseiroChefe {
    @GET("chefes/local/{cep}")
    public Call<List<Chefe>> buscarChefesPorCep(@Path("cep") String cep);
}
