package io.github.aguirresabino.deliverycaseiro.application;

import android.app.Application;

import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class CustomApplication extends Application {

    private final String MY_TAG = "DELIVERY_CASEIRO_DEBUG";

    @Override
    public void onCreate() {
        super.onCreate();

        MyLogger.logInfo(MY_TAG, getClass(), " onCreate() chamado ");

//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        new CalligraphyConfig.Builder()
//                                .setDefaultFontPath("font/montserrat_medium.ttf")
//                                .setFontAttrId(R.attr.fontPath)
//                                .build()))
//                .build());

    }
}
