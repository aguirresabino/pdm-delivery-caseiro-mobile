package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIDeliveryCaseiroRetrofitFactory {
    private static Retrofit retrofit = null;

    public static void initClientRetrofit() {

        if(retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ValuesApplicationEnum.URL_BASE_DELIVERY_SERVICE.getValue())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
    }

    public static APIDeliveryCaseiroUsuario getApiDeliveryCaseiroUsuario() {
        initClientRetrofit();
        return retrofit.create(APIDeliveryCaseiroUsuario.class);
    }

    public static APIDeliveryCaseiroChefe getApiDeliveryCaseiroChefe() {
        initClientRetrofit();
        return retrofit.create(APIDeliveryCaseiroChefe.class);
    }
}
