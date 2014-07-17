package es.DenNonDos.Shoppinglist.Controllers;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import es.DenNonDos.Shoppinglist.R;
import es.DenNonDos.Shoppinglist.Utils.ApplicationContext;
import es.DenNonDos.Shoppinglist.Views.Fragments.ListHistory;
import es.DenNonDos.Shoppinglist.Views.Fragments.ListNew;

public class ListPagerAdapter extends FragmentPagerAdapter {

    private final int NUM_PAGES = 2;

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch ( position ){
            case 0 :
                return ListNew.getInstance();
            case 1 :
                return ListHistory.getInstance();
            default:
                throw new NullPointerException();
        }
    }

    @Override
    public int getCount() {
        return this.NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ApplicationContext.get().getString(R.string.my_list);
            case 1:
                return ApplicationContext.get().getString(R.string.history);
            default:
                throw new NullPointerException();
        }
    }
}
