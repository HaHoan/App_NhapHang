package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Database.DataBaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.Views.SpinerAdapter;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Item;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private Spinner spin;
    private List<Category> categories;
    private  EditText edName, edPrice,edNote, edQuantity;
    private ImageButton btnSave;
    private Button btnDelete;
    private Item item = new Item();
    private DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        spin = findViewById(R.id.sp_category);
        edName = findViewById(R.id.ed_item_name);
        edPrice = findViewById(R.id.ed_item_price);
        edNote = findViewById(R.id.ed_item_note);
        edQuantity = findViewById(R.id.ed_item_quantity);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        try{
            item.setItemId(getIntent().getExtras().getString("item_id"));
        }catch (Exception e){}

        db = new DataBaseHelper(this);
        categories = db.getAllCategory();
        SpinerAdapter adapter = new SpinerAdapter(AddItemActivity.this,
                android.R.layout.simple_spinner_item,
                categories);
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item.setCategoryId(categories.get(i).getCategoryId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                item.setCategoryId(null);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        if(item.getItemId() != null){
            item = db.getItemById(Integer.valueOf(item.getItemId()));
            if(item == null){
                item = new Item();
                btnDelete.setVisibility(View.GONE);
            }else {
                btnDelete.setVisibility(View.VISIBLE);
                edName.setText(item.getName());
                edPrice.setText(String.valueOf(item.getPrice()));
                edQuantity.setText(String.valueOf(item.getQuantity()));
                edNote.setText(item.getNote());
                for(int i = 0;i < categories.size();i++){
                    Category category = categories.get(i);
                    if(category.getCategoryId() == item.getItemId()){
                        spin.setSelection(i);
                        break;
                    }
                }
            }
        } else{
            btnDelete.setVisibility(View.GONE);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

    }
    private void delete(){
        try{
            db.deleteItem(Integer.valueOf(item.getItemId()));
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    }
    private void save(){
       if(edName.getText().toString().equals("") || edPrice.getText().toString().equals("") || item.getCategoryId().equals("") || edQuantity.getText().toString().equals("")){
           Toast.makeText(this,"Chưa nhập đầy đủ thông tin!",Toast.LENGTH_SHORT).show();
       } else {
           try{
               item.setName(edName.getText().toString());
               item.setPrice(Float.parseFloat(edPrice.getText().toString()));
               item.setNote(edNote.getText().toString());
               item.setQuantity(Integer.valueOf(edQuantity.getText().toString()));
               if(item.getItemId() == null){
                   db.addItem(item);
               } else{
                   db.updateItem(item);
               }

               finish();
           } catch (Exception e){
               Toast.makeText(this,"Kiểm tra lại định dạng nhập!",Toast.LENGTH_SHORT).show();
           }
       }
    }
}
