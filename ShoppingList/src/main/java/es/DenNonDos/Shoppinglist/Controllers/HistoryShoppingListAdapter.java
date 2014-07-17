package es.DenNonDos.Shoppinglist.Controllers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.Utils.FileManager;

public class HistoryShoppingListAdapter extends ListAdapter {

    public HistoryShoppingListAdapter(Context context, ShoppingList items) {
        super(context, items);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        ArrayList<String> values = getList().getValues();
        Collections.sort( values );
        this.getList().setValuesArray(values);
        FileManager.saveHistoryShoppingList( getList() );
    }
}
