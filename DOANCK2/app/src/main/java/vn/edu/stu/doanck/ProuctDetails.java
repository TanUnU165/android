package vn.edu.stu.doanck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import vn.edu.stu.doanck.dao.DBHelper;
import vn.edu.stu.doanck.model.product;

public class ProuctDetails extends AppCompatActivity {
    private ImageView imageView;
    private EditText etName,etSoluong,etGia,etLoai;
    private product Product;
    private ArrayList<String> spinnerAdapter;
    private DBHelper dbHelper;
    private BottomNavigationView menu;
    private ShowProduct showProduct = new ShowProduct();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prouct_details);
        addControls();
        showProduct.setActionMenu(ProuctDetails.this,menu);
        setIntentData();
    }

    private void setIntentData(){
        Intent intent = getIntent();

        if(intent.hasExtra("product")){
            Product =(product) intent.getSerializableExtra("product");

            etName.setText(Product.getTen().toString());
            etSoluong.setText(Product.getSoluong().toString());
            etGia.setText(Product.getGia().toString());
            etLoai.setText(dbHelper.getCategoryName(Product.getPhanLoai()));

            Bitmap bitmap = BitmapFactory.decodeByteArray(Product.getHinh(),0,Product.getHinh().length);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void addControls() {
        imageView = findViewById(R.id.imageView);
        etName = findViewById(R.id.etName);
        etSoluong = findViewById(R.id.etSoluong);
        etGia = findViewById(R.id.etGia);
        etLoai = findViewById(R.id.etLoai);
        Product = new product();
        dbHelper = new DBHelper(ProuctDetails.this);
        menu = findViewById(R.id.menu);
    }
}