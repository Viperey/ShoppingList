package es.DenNonDos.Shoppinglist.Utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Keyboard {

    public static void showKeyboard( EditText e ){
        InputMethodManager imm = (InputMethodManager) ApplicationContext.get().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput( e, 0);
    }

    public static void hideKeyboard( EditText e) {
        InputMethodManager imm = (InputMethodManager) ApplicationContext.get().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) ApplicationContext.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
