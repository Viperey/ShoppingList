package es.DenNonDos.Shoppinglist.Views.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import es.DenNonDos.Shoppinglist.Controllers.HistoryShoppingListAdapter;
import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Utils.ApplicationContext;
import es.DenNonDos.Shoppinglist.Utils.FileManager;
import es.DenNonDos.Shoppinglist.Utils.Finals;
import es.DenNonDos.Shoppinglist.Utils.Keyboard;
import es.DenNonDos.Shoppinglist.Utils.Trace;
import es.DenNonDos.Shoppinglist.Views.Activities.ListsPager;

public class ListHistory extends ListFragment {

    private boolean firstTime = true;
    private TouchHistoryItemCallback callback;


    public static ListHistory getInstance(){
        ListHistory listHistory = new ListHistory();
        return listHistory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = R.layout.list_history;
        super.onCreateView(inflater, container, savedInstanceState);

        try{
            firstTime = savedInstanceState.getBoolean( Finals.FIRSTTIME );
        } catch ( NullPointerException ignored ) {
        }
    return rootView;
    }

    protected void reloadData(Bundle savedInstanceState) {
        ShoppingList values = new ShoppingList();
        try{
            values.setValues(FileManager.readHistoryShoppingList());
            values.setValues((ShoppingList) savedInstanceState.getSerializable(Finals.SHOPPINGLIST));
        } catch (NullPointerException ignored){
        } finally {
            createAdapter( values );
        }
    }

    protected void createAdapter(ShoppingList values) {
        mAdapter = new HistoryShoppingListAdapter(ApplicationContext.get(), values);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Keyboard.hideKeyboard( this.getView(), getActivity() );
        getActivity().getActionBar().setTitle( getResources().getString( R.string.history ) );
        showFirstTimeMessage();

        inflater.inflate(R.menu.lists_history, menu);
        menu.getItem(0).setVisible( true );

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.back :
                ((ListsPager)getActivity()).gotoMainView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (TouchHistoryItemCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.callback_error2));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean( Finals.FIRSTTIME, firstTime );
        super.onSaveInstanceState(outState);
    }

    private void showFirstTimeMessage() {
        if( firstTime ){
            Trace.warn( getResources().getString(R.string.history_advice) );
            firstTime = false;
        }
    }

    protected void _setListeners() {
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = mAdapter.getList().get( position ).getLabel();
                callback.onItemTouch(item);
            }
        });
    }

    public void addItemToHistory(String item) {
        mAdapter.getList().add(item);
        mAdapter.notifyDataSetChanged();
    }

    public interface TouchHistoryItemCallback {
        public void onItemTouch( String item );
    }
}
