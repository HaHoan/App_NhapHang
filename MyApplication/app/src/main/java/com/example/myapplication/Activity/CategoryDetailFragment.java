package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.Database.DataBaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.Views.ItemAdapter;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Item;

import java.util.List;

public class CategoryDetailFragment extends Fragment {
    private ListView lvItem;
    private List<Item> items;
    private SearchView searchItem;
    private ItemAdapter adapter;
    private ImageButton  btnEdit;
    private TextView tvCategory;
    private int categoryId;
    private DataBaseHelper db;
    private Category category;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DataBaseHelper(getActivity());
        categoryId = Integer.valueOf(getArguments().getString("category_id"));
        lvItem = view.findViewById(R.id.lv_items);
        searchItem = view.findViewById(R.id.search_item);
        tvCategory = view.findViewById(R.id.tv_category);
        category = db.getCategoryById(categoryId);
        btnEdit = view.findViewById(R.id.btn_edit);
        if (category != null) {
            tvCategory.setText(category.getName());
            if(category.getItems().size() == 0){
                btnEdit.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.icon_delete));
            }
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category.getItems().size() == 0){
                    delete();
                }else{
                    edit();
                }

            }
        });
        searchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                adapter.filterItems(text);
                return false;
            }
        });
        setListItem();
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               backToView();
            }
        });
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AddItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("item_id", items.get(i).getItemId());
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent, MainActivity.AFTER_ADD);
            }
        });
    }

    public void setListItem() {
        items = db.getItemsByCategoryId(categoryId);
        adapter = new ItemAdapter(getActivity(), R.layout.custom_view_item, items);
        lvItem.setAdapter(adapter);
    }
    public void refreshList(){
        try{
            items.clear();
            items.addAll(db.getItemsByCategoryId(categoryId));
            adapter.notifyDataSetChanged();
            tvCategory.setText(db.getCategoryById(categoryId).getName());
        }catch (Exception e){

        }
    }
    private void  edit(){
        Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("category_id", String.valueOf(categoryId));
        intent.putExtras(bundle);
        getActivity().startActivityForResult(intent, MainActivity.AFTER_ADD);
    }
    private void delete(){
        try{
            db.deleteCategory(Integer.valueOf(category.getCategoryId()));
            Toast.makeText(getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
            backToView();
        }catch (Exception e){
            Toast.makeText(getActivity(),"Có lỗi sảy ra!",Toast.LENGTH_SHORT).show();
        }
    }
    private void backToView(){
        Fragment navHostFragment = getActivity().getSupportFragmentManager().getPrimaryNavigationFragment();
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if(fragment.getClass() == CategoryViewFragment.class) {
            CategoryViewFragment categoryViewFragment = (CategoryViewFragment) fragment;
            categoryViewFragment.addListCategory(getActivity());
        }
        NavHostFragment.findNavController(CategoryDetailFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment);
    }

}