package com.example.myapplication.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Category> listCategory;
    private int positionSelect = -1;

    public CategoryAdapter(Context context, int idLayout, List<Category> listLanguage) {
        this.context = context;
        this.idLayout = idLayout;
        this.listCategory = listLanguage;
    }

    @Override
    public int getCount() {
        if (listCategory.size() != 0 && !listCategory.isEmpty()) {
            return listCategory.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        final Category category = listCategory.get(position);
        final LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.idLinearLayout);
        tvName.setText(category.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionSelect = position;
                notifyDataSetChanged();
            }
        });

        if (positionSelect == position) {
            linearLayout.setBackgroundColor(Color.GRAY);
        } else {
            linearLayout.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}
