package es.DenNonDos.Shoppinglist.Utils;

import android.util.Log;
import android.widget.Toast;

public class Trace{
    private static final Boolean develop = false;

    public static void devWarn( String msg ){
        if(develop){
            toast( msg );
        }
        log( msg );
    }

    public static void warn( String msg ){
        log( msg );
        toast( msg );
    }

    private static void log( String msg ){
        Log.wtf("Trace", msg);
    }

    private static void toast( String msg ){
        Toast.makeText(
                ApplicationContext.get(),
                msg,
                Toast.LENGTH_SHORT).show();
    }

    public static void apiLog( String message, int code){
        if( develop ){
            warn(code + ". " + message);
        } else if( code != 200) {
            warn(message);
        }
    }
}