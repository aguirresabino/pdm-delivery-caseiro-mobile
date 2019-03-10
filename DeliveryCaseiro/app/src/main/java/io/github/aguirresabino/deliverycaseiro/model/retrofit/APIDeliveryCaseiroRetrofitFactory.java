package io.github.aguirresabino.deliverycaseiro.model.retrofit;

import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIDeliveryCaseiroRetrofitFactory {
    private static Retrofit retrofit = null;

    public static void initClientRetrofit() {

        if(retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(DeliveryApplication.URL_BASE_API_DELIVERY_CASEIRO)
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
