package com.example.myapplication.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DataBaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Item> items;
    private List<Item> itemArrayList;
    private int positionSelect = -1;

    public ItemAdapter(Context context, int idLayout, List<Item> listLanguage) {
        this.context = context;
        this.idLayout = idLayout;
        this.items = listLanguage;
        itemArrayList = new ArrayList<>();
        itemArrayList.addAll(items);
    }

    @Override
    public int getCount() {
        if (items.size() != 0 && !items.isEmpty()) {
            return items.size();
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
        TextView tvNumber = (TextView) convertView.findViewById(R.id.tv_price);
        final Item category = items.get(position);
        tvName.setText(category.getName());
        tvNumber.setText(String.valueOf(category.getPrice()));
        return convertView;
    }
    private void deleteItem(int position) {
        new DataBaseHelper(context).deleteItem(Integer.valueOf(items.get(position).getItemId()));
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();
        items.remove(position);
        notifyDataSetChanged();
    }

    public void filterItems(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(itemArrayList);
        } else {
            for (Item item : itemArrayList) {
                if (item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

}
