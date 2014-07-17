package es.DenNonDos.Shoppinglist.Views.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import es.DenNonDos.Shoppinglist.Controllers.ListAdapter;
import es.DenNonDos.Shoppinglist.Controllers.MultiChoiceListener;
import es.DenNonDos.Shoppinglist.Models.ShoppingList;
import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Utils.Finals;

public abstract class ListFragment extends Fragment{

    @InjectView( R.id.list_view )
    ListView listView;

    protected ListAdapter mAdapter;
    protected View rootView;
    protected int layout = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(
                layout,
                container,
                false
        );

        ButterKnife.inject(this, rootView);

        reloadData(savedInstanceState);
        SwingBottomInAnimationAdapter animation = new SwingBottomInAnimationAdapter(mAdapter);

        animation.setAbsListView(listView);

        listView.setAdapter(animation);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new MultiChoiceListener(mAdapter, listView));

        _setListeners();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Finals.SHOPPINGLIST, mAdapter.getList());
        super.onSaveInstanceState(outState);
    }

    protected abstract void reloadData(Bundle savedInstanceState);
    protected abstract void createAdapter( ShoppingList values );
    protected abstract void _setListeners();
}
