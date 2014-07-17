package es.DenNonDos.Shoppinglist.Controllers;

import android.graphics.Paint;
import android.widget.TextView;

import es.DenNonDos.Shoppinglist.Models.ShoppingListItem;
import es.DenNonDos.Shoppinglist.R;

public class TextViewHandler {

    public static void toggleTextColor(TextView textView, ShoppingListItem item) {
        if( item.isStudded() ){
            textView.setTextColor( textView.getResources().getColor(R.color.grey) );
        } else {
            textView.setTextColor(textView.getResources().getColor(R.color.black));
        }
    }

    public static void togglePaintFlag(TextView textView, ShoppingListItem item) {
        if( item.isStudded() ){
            textView.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG );
        } else {
            textView.setPaintFlags( Paint.LINEAR_TEXT_FLAG );
        }
    }
}
