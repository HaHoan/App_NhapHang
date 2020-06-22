package com.example.myapplication.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Category> filterCategories;
    private ArrayList<Category> categories;
    private int selectedCategory;
    public ExpandAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.filterCategories = categories;
        this.categories = new ArrayList<>();
        this.categories.addAll(filterCategories);
    }

    @Override
    public int getGroupCount() {
        return this.categories.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.categories.get(listPosition).getItems()
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.categories.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.categories.get(listPosition).getItems().get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        selectedCategory = listPosition;
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Category category = (Category) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_view_category, null);
        }
        TextView tvName = (TextView) convertView
                .findViewById(R.id.tv_name);
        tvName.setTypeface(null, Typeface.BOLD);
        tvName.setText(category.getName());
        TextView tvNumber = (TextView) convertView
                .findViewById(R.id.tv_number);
        tvNumber.setText(String.valueOf(category.getItems().size()) + " sp");
        return convertView;
    }
    public void onDetailClick(int id){

    }
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Item item = (Item) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_view_item, null);
        }
        TextView tvName = (TextView) convertView
                .findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) convertView
                .findViewById(R.id.tv_price);
        tvName.setText(item.getName());
        tvPrice.setText(String.valueOf(item.getQuantity()));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void filterCategories(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        categories.clear();
        if (charText.length() == 0) {
            categories.addAll(filterCategories);
        } else {
            for (Category category : filterCategories) {
                if (category.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    categories.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }

}
