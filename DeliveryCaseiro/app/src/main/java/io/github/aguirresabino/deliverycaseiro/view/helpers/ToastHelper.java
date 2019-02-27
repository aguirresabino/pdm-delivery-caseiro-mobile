package io.github.aguirresabino.deliverycaseiro.view.helpers;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static void toastShort(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
