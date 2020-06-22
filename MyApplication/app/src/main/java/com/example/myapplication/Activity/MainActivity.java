package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAddCategory, fabAddItem, fabAdd;
    private boolean isFABOpen;
    public static final int AFTER_ADD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAdd = findViewById(R.id.fab_add);
        fabAddCategory = findViewById(R.id.fab_add_category);
        fabAddItem = findViewById(R.id.fab_add_item);
        fabAddCategory.hide();
        fabAddItem.hide();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCategoryActivity.class);
                startActivityForResult(intent, AFTER_ADD);
            }
        });
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intent, AFTER_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AFTER_ADD) {
            Fragment navHostFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
            Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
            if(fragment.getClass() == CategoryViewFragment.class){
                CategoryViewFragment categoryViewFragment = (CategoryViewFragment)fragment;
                categoryViewFragment.addListCategory(this);
            } else if(fragment.getClass() == CategoryDetailFragment.class){
                CategoryDetailFragment categoryDetailFragment = (CategoryDetailFragment)fragment;
                categoryDetailFragment.refreshList();
            }

        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabAddCategory.show();
        fabAddItem.show();
        fabAddCategory.animate().translationY(-getResources().getDimension(R.dimen.standard_70));
        fabAddItem.animate().translationY(-getResources().getDimension(R.dimen.standard_135));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabAddCategory.hide();
        fabAddItem.hide();
        fabAddCategory.animate().translationY(0);
        fabAddItem.animate().translationY(0);
    }
}