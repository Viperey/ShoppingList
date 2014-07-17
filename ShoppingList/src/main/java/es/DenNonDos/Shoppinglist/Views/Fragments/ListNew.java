package es.DenNonDos.Shoppinglist.Views.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.InjectView;
import butterknife.OnEditorAction;
import es.DenNonDos.Shoppinglist.Controllers.ShoppingListAdapter;
import es.DenNonDos.Shoppinglist.Controllers.TextViewHandler;
import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.Models.ShoppingListItem;
import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Utils.ApplicationContext;
import es.DenNonDos.Shoppinglist.Utils.FileManager;
import es.DenNonDos.Shoppinglist.Utils.Finals;
import es.DenNonDos.Shoppinglist.Utils.Keyboard;
import es.DenNonDos.Shoppinglist.Utils.Trace;
import es.DenNonDos.Shoppinglist.Views.Activities.ListsPager;

public class ListNew extends ListFragment{


    @InjectView( R.id.item_input )
    EditText input;

    Menu menu;

    private boolean editMode = false;
    private boolean firstTime = true;
    private AddingItemToListCallback callback;

    public static Fragment getInstance(){
        return new ListNew();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = R.layout.list_new;
        super.onCreateView(inflater, container, savedInstanceState);

        showFirstTimeMessage( savedInstanceState );
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean( Finals.FIRSTTIME, firstTime );
        super.onSaveInstanceState(outState);
    }

    private void showFirstTimeMessage( Bundle savedInstanceState ) {
        try{
            firstTime = savedInstanceState.getBoolean( Finals.FIRSTTIME );
        } catch ( NullPointerException ignored ){

        } finally {
            if( firstTime ){
                firstTime = false;
                Trace.warn(getResources().getString(R.string.swipe_right));
            }
        }
    }

    protected void reloadData(Bundle savedInstanceState) {
        ShoppingList values = new ShoppingList();
        try{
            values.setValues(FileManager.readShoppingList());
            values.setValues( (ShoppingList) savedInstanceState.getSerializable(Finals.SHOPPINGLIST) );
        } catch (NullPointerException ignored){
        } finally {
            createAdapter( values );
        }
    }

    @Override
    protected void createAdapter(ShoppingList values) {
        mAdapter = new ShoppingListAdapter(ApplicationContext.get(), values);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void _setListeners() {
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = ((TextView)view.findViewById(R.id.text));
                ShoppingListItem item = ( (ShoppingListItem) parent.getItemAtPosition( position ) );
                ShoppingList list = mAdapter.getList();

                item.toggleStudded();
                FileManager.saveShoppingList( list );
                TextViewHandler.togglePaintFlag(textView, item);
                TextViewHandler.toggleTextColor(textView, item);

                if( menu==null )
                    return;
                if( mAdapter.getList().hasStuddedItems() ){
                    menu.getItem( 0 ).setVisible( true );
                } else{
                    menu.getItem( 0 ).setVisible( false );
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (AddingItemToListCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + activity.getString(R.string.callback_error));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lists_pager, menu);
        this.menu = menu;
        if(
            !mAdapter.getList().isEmpty() &&
            mAdapter.getList().hasStuddedItems()
        )
            menu.getItem(0).setVisible( true );
        else
            menu.getItem(0).setVisible( false );
        menu.getItem(1).setVisible( false );
        menu.getItem(2).setVisible( false );
        menu.getItem(3).setVisible( true );
        if( !editMode && !mAdapter.getList().isEmpty() )
            useMode();
        else
            editMode();

        getActivity().getActionBar().setTitle( getResources().getString( R.string.my_shopping_list ) );
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.accept :
                useMode();
                return true;
            case R.id.edit :
                editMode();
                return true;
            case R.id.history :
                ((ListsPager)getActivity()).gotoHistory();
                return true;
            case R.id.delete :
                removeStuddedItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void removeStuddedItems() {
        ((ShoppingListAdapter)mAdapter).removeStuddedItems();
    }

    private void useMode(){
        editMode = false;
        input_Hide();
        editIcon_Show();
        acceptIcon_Hide();
        Keyboard.hideKeyboard(input);
    }

    private void editMode(){
        editMode = true;
        input_Show();
        editIcon_Hide();
        acceptIcon_Show();
        input.requestFocus();
        Keyboard.showKeyboard( input );
    }

    private void input_Hide() {
        if( input.getVisibility()!=View.GONE ){
            Animation a = AnimationUtils.loadAnimation(ApplicationContext.get(), R.anim.collapse_top);
            input.startAnimation( a );
            input.setVisibility( View.GONE );
        }
    }

    private void input_Show(){
        if( input.getVisibility()!=View.VISIBLE ) {
            Animation a = AnimationUtils.loadAnimation(ApplicationContext.get(), R.anim.expand_top);
            input.startAnimation(a);
            input.setVisibility(View.VISIBLE);
        }
    }

    private void editIcon_Show(){
        this.menu.getItem(2).setVisible(true);
    }

    private void editIcon_Hide(){
        this.menu.getItem(2).setVisible(false);
    }

    private void acceptIcon_Show(){
        this.menu.getItem(1).setVisible( true );
    }

    private void acceptIcon_Hide(){
        this.menu.getItem(1).setVisible( false );
    }

    @OnEditorAction( R.id.item_input )
    public boolean click(){
        String newItem = input.getText().toString();
        ShoppingList values = mAdapter.getList();
        if( !newItem.equals("")){
            if( !values.add( newItem ) ){
                Trace.warn( getResources().getString(R.string.duplicated_item) );
            }
            mAdapter.notifyDataSetChanged();
            input.setText("");
            scrollMyListViewToBottom();
            callback.onItemAdded( newItem );
        } else {
            Trace.warn(ApplicationContext.get().getString(R.string.buy_air));
        }

        showFinalIcon();

        FileManager.saveShoppingList( mAdapter.getList() );

    return true;
    }

    private void showFinalIcon() {
        if( !mAdapter.getList().isEmpty() )
            this.menu.getItem(1).setVisible(true);
    }

    private void scrollMyListViewToBottom() {
        listView.post(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }

    public void addItemToList(String item) {
        if( this.mAdapter.getList().add( item ) )
            Trace.warn(item +" "+ getResources().getString(R.string.item_added) );
        else
            Trace.warn( getResources().getString(R.string.already_list) );
        mAdapter.notifyDataSetChanged();
        FileManager.saveShoppingList( mAdapter.getList() );
    }

    public interface AddingItemToListCallback{
        public void onItemAdded( String item );
    }
}
