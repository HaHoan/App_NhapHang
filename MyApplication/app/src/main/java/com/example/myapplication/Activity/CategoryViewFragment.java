package com.example.myapplication.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.Database.DataBaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.Views.ExpandAdapter;
import com.example.myapplication.models.Category;

import java.util.List;

public class CategoryViewFragment extends Fragment {
    private ExpandableListView lvCategory;
    private SearchView searchCategory;
    private ExpandAdapter adapter;
    private DataBaseHelper db;
    private  List<Category> categories;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DataBaseHelper(getActivity());
        lvCategory = view.findViewById(R.id.lv_category);
        searchCategory = view.findViewById(R.id.search_category);
        searchCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s;
                adapter.filterCategories(text);
                return false;
            }
        });
        addListCategory(getActivity());
    }

    public void addListCategory(Context context) {

        categories = db.getAllCategory();
        adapter = new ExpandAdapter(context, categories);
        lvCategory.setAdapter(adapter);

        lvCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (categories.get(groupPosition).getItems().size() == 0) {
                    openDetail(groupPosition);
                }
            }
        });

        lvCategory.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        lvCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
              openDetail(groupPosition);
                return false;
            }
        });
    }
    private  void openDetail(int position){
        Bundle bundle = new Bundle();
        bundle.putString("category_id", categories.get(position).getCategoryId());
        NavHostFragment.findNavController(CategoryViewFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
    }
}