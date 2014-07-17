package es.DenNonDos.Shoppinglist.Views.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import es.DenNonDos.Shoppinglist.Controllers.ActionBarTabListener;
import es.DenNonDos.Shoppinglist.Controllers.ListPagerAdapter;
import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Views.Fragments.ListHistory;
import es.DenNonDos.Shoppinglist.Views.Fragments.ListNew;

public class ListsPager extends Activityc implements ListNew.AddingItemToListCallback, ListHistory.TouchHistoryItemCallback{

    ListPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lists_pager);

        _initViewPager();
        _initActionBar();
    }

    private void _initViewPager(){
        mSectionsPagerAdapter = new ListPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById( R.id.pager );
        mViewPager.setAdapter( mSectionsPagerAdapter );
    }

    public void gotoHistory(){
        mViewPager.setCurrentItem( 1 );
    }

    public void gotoMainView(){
        mViewPager.setCurrentItem( 0 );
    }

    private void _initActionBar(){
        final ActionBar actionBar = getActionBar();
        ActionBar.TabListener tabListener = new ActionBarTabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        };

        if (actionBar != null) {
            for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
                if (actionBar != null) {
                    actionBar.addTab(
                            actionBar.newTab()
                                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                                    .setTabListener(tabListener));
                }
            }
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        }
    }

    @Override
    public void onItemAdded(String item) {

        ListHistory listHistory = (ListHistory) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":1");
        listHistory.addItemToHistory( item );
    }

    @Override
    public void onItemTouch(String item) {
        ListNew listNew = (ListNew) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":0");
        listNew.addItemToList( item );
    }
}
