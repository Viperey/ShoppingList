package es.DenNonDos.Shoppinglist.Controllers;

import android.content.Context;

import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.Utils.FileManager;

public class ShoppingListAdapter extends ListAdapter {
    public ShoppingListAdapter(Context context, ShoppingList items) {
        super(context, items);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        FileManager.saveShoppingList( getList() );
    }

    public void removeStuddedItems(){
        getList().removeStuddedItems();
        notifyDataSetChanged();
    }
}
