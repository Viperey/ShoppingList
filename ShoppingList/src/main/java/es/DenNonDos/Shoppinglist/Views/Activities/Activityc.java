package es.DenNonDos.Shoppinglist.Views.Activities;

import android.app.Activity;
import android.os.Bundle;

import es.DenNonDos.Shoppinglist.Utils.ApplicationContext;


public class Activityc extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationContext.getInstance().init( this );
    }

}
