package es.DenNonDos.Shoppinglist.Utils;

import android.content.Context;

public class ApplicationContext {

    private Context appContext;
    private static ApplicationContext instance;

    private ApplicationContext(){

    }

    public static ApplicationContext getInstance(){
        return instance == null ?
                (instance = new ApplicationContext()):
                instance;
    }

    public void init(Context context){
        if(appContext == null){
            appContext = context;
        }
    }

    private Context getContext(){
        return appContext;
    }

    public static Context get(){
        return getInstance().getContext();
    }
}