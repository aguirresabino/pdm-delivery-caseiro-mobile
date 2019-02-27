package io.github.aguirresabino.deliverycaseiro.logs;

import android.util.Log;

public class MyLogger {

    public static void logInfo(String TAG, Class anyClass, String message){
        Log.i(TAG, anyClass.getSimpleName() + " | " + message);
    }

    public static void logWarning(String TAG, Class anyClass, String message){
        Log.w(TAG, anyClass.getSimpleName() + " | " + message);
    }

    public static void logError(String TAG, Class anyClass, String message){
        Log.e(TAG, anyClass.getSimpleName() + " | " + message);
    }

    public static void logVerbose(String TAG, Class anyClass, String message){
        Log.v(TAG, anyClass.getSimpleName() + " | " + message);
    }

    public static void logDebug(String TAG, Class anyClass, String message){
        Log.d(TAG, anyClass.getSimpleName() + " | " + message);
    }
}
