package es.DenNonDos.Shoppinglist.Models;

import java.util.ArrayList;
import java.util.ListIterator;

public class ShoppingList extends ArrayList<ShoppingListItem> {

    public boolean add( String item ) {

        //Avoids adding duplicated items.
        for( ShoppingListItem itemList : this ){
            if( item.toLowerCase().equals( itemList.getLabel().toLowerCase() ) )
                return false;
        }

        return super.add( new ShoppingListItem( item, false) );
    }

    public void add(int index, String object) {
        super.add(index, new ShoppingListItem( object, false ));
    }

    public void remove( String item ){
        ListIterator<ShoppingListItem> itemListIterator = listIterator();
        while( itemListIterator.hasNext() ){
            ShoppingListItem listItem = itemListIterator.next();
            if( listItem.getLabel().equals( item ) ){
                this.remove( listItem );
            }
        }
    }

    public ArrayList<String> getValues(){
        ArrayList<String> values = new ArrayList<String>();
        for( ShoppingListItem shoppingListItem : this ){
            values.add( shoppingListItem.getLabel() );
        }
    return values;
    }

    public void setValuesArray(ArrayList<String> values){
        removeAll();
        for( String item: values ){
            this.add( new ShoppingListItem( item ) );
        }
    }

    public void setValues( ShoppingList values ) {
        removeAll();
        this.addAll( (ShoppingList) values );
    }

    public void removeAll() {
        if( isEmpty() )
            return;
        for( int i=this.size(); i>0; i--){
            remove( this.get( i-1 ) );
        }
    }

    public void removeStuddedItems() {
        if( isEmpty() )
            return;
        for( int i=this.size(); i>0; i--){
            if( this.get( i-1 ).isStudded() )
                remove( this.get( i-1 ) );
        }
    }

    public boolean hasStuddedItems() {
        for( ShoppingListItem item : this ){
            if( item.isStudded() )
                return true;
        }
    return false;
    }
}
