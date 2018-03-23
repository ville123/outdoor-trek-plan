package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.ItemListAdapter;
import com.trekplanner.app.utils.AppUtils;

public class ItemListFragment extends ListFragment {

    private static ItemListFragment instance;

    public static ItemListFragment getInstance(DbHelper db) {
        Log.d("TREK_ItemListFrag", "Returning ItemListFragment -instance");
        if (instance == null) {
            instance = new ItemListFragment();
            instance.db = db;
        }
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.listview_list_layout;
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_ItemListFrag", "Building ItemList view");

        TextView headerText = view.findViewById(R.id.view_header_text);
        headerText.setText(R.string.term_items);

        ImageView imageView = view.findViewById(R.id.view_header_image);
        imageView.setImageResource(R.drawable.item);
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_ItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);
        ItemListAdapter adapter = new ItemListAdapter(this.getActivity());
        adapter.setListRows(db.getItems(null));
        listView.setAdapter(adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.ITEM_LIST_ACTION_ID, view);
    }
}
