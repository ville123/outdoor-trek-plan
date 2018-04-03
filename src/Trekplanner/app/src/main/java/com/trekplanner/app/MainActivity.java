package com.trekplanner.app;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.editable.ItemEditFragment;
import com.trekplanner.app.fragment.editable.MainEditFragment;
import com.trekplanner.app.fragment.editable.TrekEditFragment;
import com.trekplanner.app.fragment.listable.ItemListFragment;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.fragment.listable.TrekItemListFragment;
import com.trekplanner.app.fragment.listable.TrekListFragment;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Main activity.
 *
 * Handles all the UI navigation actions
 */
public class MainActivity extends AppCompatActivity implements ListFragment.ListViewActionListener {

    private DbHelper db;
    private Fragment itemListFragment;
    private Fragment trekListFragment;
    private Menu menu;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // this instace of dbHelper is used everywhere
        // no other instances should be created
        db = new DbHelper(this);

        // item and trek -list context will not change, only the content
        // thus singletons can be used
        itemListFragment = ItemListFragment.getInstance(db);
        trekListFragment = TrekListFragment.getInstance(db);

        Log.d("TREK_MainActivity", "opening splash screen");
        openSplashScreenActivity();

        // Nav menu
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        if (menuItem.getItemId() == R.id.nav_first_fragment) {
                            Log.d("TREK_MainActivity", "First nav item selected");
                            openItemList();
                        } else if (menuItem.getItemId() == R.id.nav_second_fragment) {
                            Log.d("TREK_MainActivity", "Second nav item selected");
                            openTrekList();
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TREK_MainActivity", "opening item list");
        // TODO: for now item list is opened as default before the left menu is implemented
        openItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d("TREK_MainActivity", "Creating actionbar menu");
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        // by default the search is hidden
        menu.findItem(R.id.action_search).setVisible(false);

        // save the menu object for later use in showing search action where needed
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("TREK_MainActivity", "Actionbar action selected");
        int id = item.getItemId();

        if (id == R.id.action_loaddefaults) {
            db.resetDefaults();
            openItemList();
            return true;
        } else if (id == R.id.action_cleardatabase) {
            // TODO: needed? db.dropDb();
            return true;
        } else if (id == R.id.action_export) {
            // TODO: export items and treks to a json/csv/xml file
            return true;
        } else if (id == R.id.action_import) {
            // TODO: import items and treks from a json/csv/xml file
            return true;
        } else if (id == R.id.action_help) {
            // TODO: show help -page
            return true;
        } else if (id == R.id.action_settings) {
            // TODO: implement settings page
            return true;
        } else if (id == android.R.id.home){
            // Open navigation menu
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        // TODO: implement search for items

        return super.onOptionsItemSelected(item);
    }

    // Floating button clicked on some listview
    public void onListViewActionButtonClick(String listId, View view) {
        Log.d("TREK_MainActivity", "List view floating button clicked");

        // TODO: for now this action toggles between item and trek -lists
        // until -left menu is implemented

        // TODO: Item / trek editor should be opened here for creating new object

        if (listId.equals(AppUtils.ITEM_LIST_ACTION_ID)) {
            openTrekList();
        } else if (listId.equals(AppUtils.TREK_LIST_ACTION_ID)) {
            openItemList();
        } else if (listId.equals(AppUtils.TREKITEM_LIST_ACTION_ID)) {
            // TODO: open item selection list
            Snackbar.make(view, "Tästä pitäisi avautua varusteiden valintalista retkelle", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    // forward button clicked on Item or Trek list view
    @Override
    public void onForwardButtonClick(Object o) {
        Log.d("TREK_MainActivity", "listview row forward button clicked");
        if (o instanceof Item) {
            openItemPage((Item)o);
        } else if (o instanceof Trek) {
            openTrekPage((Trek)o);
        }
    }

    // add or substract count -button clicked on trekitem listview
    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        db.saveTrekItem(trekItem);
        showOkMessage(R.string.phrase_save_success);
    }

    // delete -button clicked on trekitem listview
    @Override
    public void onDeleteButtonClicked(TrekItem trekItem) {
        db.deleteTrekItem(trekItem);
        showOkMessage(R.string.phrase_delete_success);
    }

    // save action fired from itemlistview
    @Override
    public void saveButtonClicked(Item item) {
        db.saveItem(item);
        showOkMessage(R.string.phrase_save_success);
    }

    @Override
    public void saveButtonClicked(TrekItem trekItem) {
        db.saveTrekItem(trekItem);
        showOkMessage(R.string.phrase_save_success);
    }

    private void showOkMessage(int message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void openItemList(){

        getSupportActionBar().setTitle(R.string.term_items);

        // TODO: cant do this since openItemList() is called before menu is set
        //MenuItem item = this.menu.findItem(R.id.action_search);
        //item.setVisible(true);

        openFragment(this.itemListFragment, false);
    }

    private void openTrekList() {

        getSupportActionBar().setTitle(R.string.term_treks);

        openFragment(this.trekListFragment, false);
    }

    private void openItemPage(Item item) {

        getSupportActionBar().setTitle(R.string.term_item);
        openFragment(ItemEditFragment.getNewInstance(db, item), true);
    }

    private void openTrekPage(Trek trek) {

        getSupportActionBar().setTitle(R.string.term_trek);

        // opening tab -layout by using MainEditFragment

        // since edit fragment context changes (some item / trek),
        // a new instances are always created
        openFragment(
                MainEditFragment.getNewInstance(
                        TrekEditFragment.getNewInstance(db, trek),
                        TrekItemListFragment.getNewInstance(db, trek.getId())),
                true);
    }

    private void openSplashScreenActivity() {
        startActivity(new Intent(this, SplashActivity.class));
    }

    private void openFragment(Fragment fragment, boolean addToBackStack) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, fragment);

        // if added to back stack, android back -button gets back to previous page
        if (addToBackStack)  ft.addToBackStack(fragment.getClass().getName());

        ft.commit();

    }
}
