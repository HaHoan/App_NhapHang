package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Database.DataBaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.models.Category;

public class AddCategoryActivity extends AppCompatActivity {
    private ImageButton btnSave;
    private EditText edName;
    private Category category;
    private DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        db = new DataBaseHelper(this);
        btnSave = findViewById(R.id.btn_save);
        edName = findViewById(R.id.ed_category_name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        category = new Category();
        try{
            category.setCategoryId(getIntent().getExtras().getString("category_id"));
            category = db.getCategoryById(Integer.valueOf(category.getCategoryId()));
            edName.setText(category.getName());
        }catch (Exception e){
        }
    }
    private void save(){
        if(edName.getText().toString().equals("")){
            Toast.makeText(this,"Chưa nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
        } else {
            category.setName(edName.getText().toString());
            if(category.getCategoryId() == null){
                db.addCategory(category);
            } else{
                db.updateCategory(category);
            }
            finish();
        }
    }

}