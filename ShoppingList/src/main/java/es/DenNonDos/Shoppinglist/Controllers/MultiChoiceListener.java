package es.DenNonDos.Shoppinglist.Controllers;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import es.DenNonDos.Shoppinglist.Models.ShoppingListItem;
import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Utils.FileManager;

public class MultiChoiceListener implements AbsListView.MultiChoiceModeListener {

    private ListView listView;
    private ListAdapter mAdapter;

    public MultiChoiceListener( ListAdapter mAdapter, ListView listView) {
        this.listView = listView;
        this.mAdapter = mAdapter;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode,
                                          int position, long id, boolean checked) {
        final int checkedCount = listView.getCheckedItemCount();
        mode.setTitle(checkedCount + " Selected");
        mAdapter.toggleSelection(position);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                // Calls getSelectedIds method from ListViewAdapter Class
                SparseBooleanArray selected = mAdapter.getSelectedIds();
                // Captures all selected ids with a loop
                for (int i = (selected.size() - 1); i >= 0; i--) {
                    if (selected.valueAt(i)) {
                        ShoppingListItem shoppingListItem = mAdapter.getItem(selected.keyAt(i));
                        mAdapter.remove( shoppingListItem );
                    }
                }
                FileManager.saveShoppingList( mAdapter.getList() );
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate( R.menu.lists_pager , menu);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible( false );
        menu.getItem(3).setVisible( false );
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // TODO Auto-generated method stub
        mAdapter.removeSelection();
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        // TODO Auto-generated method stub
        return false;
    }
}

