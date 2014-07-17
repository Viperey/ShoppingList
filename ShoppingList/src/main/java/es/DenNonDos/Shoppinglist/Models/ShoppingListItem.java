package es.DenNonDos.Shoppinglist.Models;

import java.io.Serializable;

public class ShoppingListItem implements Serializable{
    private String label;
    private boolean studded = false;

    public ShoppingListItem(String item, boolean b) {
        this.label = item;
        this.studded = b;
    }

    public ShoppingListItem( String item ){
        this.label = item;
        this.studded = false;
    }

    public boolean isStudded() {
        return studded;
    }

    public void toggleStudded() {
        this.studded = !this.studded;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
