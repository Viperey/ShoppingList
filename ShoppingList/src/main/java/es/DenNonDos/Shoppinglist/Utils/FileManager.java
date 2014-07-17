package es.DenNonDos.Shoppinglist.Utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import es.DenNonDos.Shoppinglist.Models.ShoppingList;

public class FileManager {

    public static void saveShoppingList( ShoppingList list) {
        String fileName = "shoppingList.dat";
        _saveShoppingList(fileName, list);
    }

    public static void saveHistoryShoppingList( ShoppingList list) {
        String fileName = "historyShoppingList.dat";
        _saveShoppingList(fileName, list);
    }

    private static void _saveShoppingList(String filename, ShoppingList list) {
        FileOutputStream outputStream;
        try {
            outputStream = ApplicationContext.get().openFileOutput( filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream( outputStream );
            oos.writeObject( list );
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    public static ShoppingList readShoppingList() throws NullPointerException{
        String filename = "shoppingList.dat";
        return _readShoppingList(filename);
    }

    public static ShoppingList readHistoryShoppingList() throws NullPointerException{
        String filename = "historyShoppingList.dat";
        return _readShoppingList(filename);
    }

    private static ShoppingList _readShoppingList(String filename){
        ShoppingList values;
        try {
            FileInputStream inputStream;
            inputStream = ApplicationContext.get().openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream( inputStream );
            values = (ShoppingList) ois.readObject();
            inputStream.close();
        } catch (Exception e) {
            throw new NullPointerException();
        }
        if( values.isEmpty() )
            throw new NullPointerException();
        return values;
    }
}
