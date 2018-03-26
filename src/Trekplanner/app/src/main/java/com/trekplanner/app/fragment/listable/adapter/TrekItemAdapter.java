package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.model.TrekItem;

import java.util.List;

/**
 * Created by Sami
 *
 * Adapter for trekitems
 */
public class TrekItemAdapter extends ListAdapter {

    private List<TrekItem> listRows;

    public TrekItemAdapter(Context context) {
        super(context);
    }

    public void setListRows(List<TrekItem> rows) {
        this.listRows = rows;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final TrekItem trekItem = (TrekItem) getChild(groupPosition, childPosition);

        Log.d("TREK_TrekItemListAdaptr", "Getting list trekItem #" + childPosition + " for group #" + groupPosition + " with itemname " + trekItem.getItem().getName());

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.editview_row_item_content_layout, null);
        }
/**
        RadioButton rbtn1 = convertView.findViewById(R.id.listview_item_rbtn_status2);
        RadioButton rbtn2 = convertView.findViewById(R.id.listview_item_rbtn_status2);
        RadioButton rbtn3 = convertView.findViewById(R.id.listview_item_rbtn_status3);

        String status = String.valueOf(trekItem.getStatus());

        if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus1))) {
            rbtn1.setChecked(true);
        } else if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus2))) {
            rbtn2.setChecked(true);
        } else if (status.equals(convertView.getContext().getString(R.string.enum_itemstatus3))) {
            rbtn3.setChecked(true);
        }

        RadioGroup radioGroup = convertView.findViewById(R.id.listview_item_status_rbtgroup);
        final View finalConvertView = convertView;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton = finalConvertView.findViewById(checkedId);

                if (radioButton.getId() == R.id.listview_item_rbtn_status2) {
                    trekItem.setStatus(1);
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status2) {
                    trekItem.setStatus(2);
                } else if (radioButton.getId() == R.id.listview_item_rbtn_status3) {
                    trekItem.setStatus(3);
                }

                // TODO: save status
                Log.d("TREK/TrekItemAdapter.getChildView.Radiobutton.onChangeListener", "Item status changed for trekItem " + trekItem.getName() + " to " + radioButton.getText());
                Snackbar.make(finalConvertView, "Item status changed for trekItem " + trekItem.getName() + " to " + radioButton.getText(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
 **/

        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listRows.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listRows.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final TrekItem trekItem = (TrekItem) getGroup(groupPosition);

        Log.d("TREK_TrekItemListAdaptr", "Getting group trekitem #" + groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.editview_row_header_layout, null);
        }

        TextView headlineTextView = convertView
                .findViewById(R.id.editview_row_header_headline);
        headlineTextView.setText(trekItem.getItem().getName());

        TextView textView = convertView
                .findViewById(R.id.editview_row_header_text);
        textView.setText(trekItem.getCount() + convertView.getContext().getString(R.string.term_pieces));

        ImageView imageView = convertView.findViewById(R.id.editview_row_header_image);
        String type = String.valueOf(trekItem.getItem().getType());

        if (type.equals(convertView.getContext().getString(R.string.enum_itemtype1))) {
            imageView.setImageResource(R.drawable.idea);
        } else if (type.equals(convertView.getContext().getString(R.string.enum_itemtype2))) {
            imageView.setImageResource(R.drawable.food);
        } else if (type.equals(convertView.getContext().getString(R.string.enum_itemtype3))) {
            imageView.setImageResource(R.drawable.backpack);
        }

        ImageView addBtn = convertView.findViewById(R.id.editview_row_add_count_button);

        addBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked add count for item " + trekItem.getItem().getName());

                trekItem.setCount(trekItem.getCount() + 1);

                actionListener.onModifyCountButtonClicked(trekItem);
                notifyDataSetChanged();

            }
        });

        ImageView subBtn = convertView.findViewById(R.id.editview_row_substract_count_button);

        subBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked substract count for item " + trekItem.getItem().getName());

                if (trekItem.getCount() > 0) {
                    trekItem.setCount(trekItem.getCount() - 1);
                    actionListener.onModifyCountButtonClicked(trekItem);
                    notifyDataSetChanged();
                }

            }
        });

        ImageView delBtn = convertView.findViewById(R.id.editview_row_delete_button);

        delBtn.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d("TREK_TrekItemListAdaptr", "Clicked delete for item " + trekItem.getItem().getName());

                actionListener.onDeleteButtonClicked(trekItem.getId());
            }
        });

        return convertView;
    }
}