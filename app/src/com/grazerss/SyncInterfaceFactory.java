package com.grazerss;

import java.lang.reflect.Constructor;

import android.content.Context;
import android.util.Log;

public class SyncInterfaceFactory {
    private static final String TAG = SyncInterfaceFactory.class.getName();

    public static BackendProvider getSyncInterface(Context context) {
        String className = NewsRob.getDebugProperties(context).getProperty("syncClassName",
                "com.grazerss.NewsBlurBackendProvider");

        Log.d(TAG, "Attempting to load sync class: " + className);

        try {
            Class<?> syncClass = Class.forName(className);
            Log.d(TAG, "Class loaded: " + syncClass.toString());
            Constructor<Context> constructor = (Constructor<Context>) syncClass.getConstructor(Context.class);
            Log.d(TAG, "Constructor loaded: " + constructor.toString());
            BackendProvider obj = (BackendProvider) constructor.newInstance(context);
            Log.d(TAG, "SyncInterface loaded: " + obj.toString());
            return obj;
        } catch (Exception e) {
            Log.wtf(TAG, e);
            return new NewsBlurBackendProvider(context);
        }
    }
}
