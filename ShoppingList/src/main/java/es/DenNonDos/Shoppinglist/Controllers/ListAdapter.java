package es.DenNonDos.Shoppinglist.Controllers;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.Models.ShoppingListItem;
import es.DenNonDos.Shoppinglist.R;

public class ListAdapter extends com.nhaarman.listviewanimations.ArrayAdapter<ShoppingListItem> {

    private Context mContext;
    private ShoppingList values;
    private SparseBooleanArray mSelectedItemsIds;

    public ListAdapter(Context context, ShoppingList items) {
        super( items );
        mSelectedItemsIds = new SparseBooleanArray();
        values = items;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) convertView;
        if (tv == null) {
            tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
        }
        tv.setText( getItem(position).getLabel() );
        TextViewHandler.toggleTextColor(tv, values.get(position));
        TextViewHandler.togglePaintFlag(tv, values.get(position));
        return tv;
    }

    public ShoppingList getList(){
        return this.values;
    }

    public void setList( ShoppingList values ){
        this.values = values;
        notifyDataSetChanged();
    }

    public void remove(String object) {
        values.remove(object);
        notifyDataSetChanged();
    }


    public ShoppingList getString() {
        return values;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
