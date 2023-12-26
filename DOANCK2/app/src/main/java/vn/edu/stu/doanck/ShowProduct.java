package vn.edu.stu.doanck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.doanck.adapter.ProductAdapter;
import vn.edu.stu.doanck.dao.DBHelper;
import vn.edu.stu.doanck.model.product;
import vn.edu.stu.doanck.ultil.DBconfigUtil;

public class ShowProduct extends AppCompatActivity {
    private ImageButton btnAdd;
    private TextView tv1;
    private ListView listView;
    private ImageView imageView;
    private BottomNavigationView menu;
    private  ProductAdapter productAdapter;
    private DBHelper dbHelper;
    private List<product> productList = new ArrayList<>();
    public final static int add = 123;
    public final static int update = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        addControls();
        setActionMenu(ShowProduct.this,menu);

        getAll();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        ShowProduct.this,
                        AddUpdateProduct.class
                );
                startActivityForResult(intent,ShowProduct.add);
            }
        });
    }
    private void addControls() {
        tv1 = findViewById(R.id.tv1);
        dbHelper = new DBHelper(ShowProduct.this);
        listView = findViewById(R.id.listView);
        menu = findViewById(R.id.menu);
        imageView = findViewById(R.id.imageView);
        btnAdd = findViewById(R.id.btnAdd);
    }
    private void getAll() {
        productList = dbHelper.getAllProducts();
        productAdapter = new ProductAdapter(ShowProduct.this, R.layout.layout_custom ,productList, dbHelper);
        listView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    public void setActionMenu(AppCompatActivity activity, BottomNavigationView menu) {
        menu.setSelectedItemId(R.id.itHome);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.itExit){
                    activity.finish();
                    return true;
                }else if(item.getItemId() == R.id.itAbout){
                    Intent intent = new Intent(activity, About.class);
                    activity.startActivity(intent);

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            product pd =(product) data.getSerializableExtra("product");

            if(requestCode == ShowProduct.add){

                if(dbHelper.addProduct(pd) > 0){

                    productAdapter.clear();
                    getAll();
                    Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                }
            }

            if(requestCode == ShowProduct.update){
                if(dbHelper.updateProduct(pd) >0){
                    productAdapter.clear();
                    getAll();
                    Toast.makeText(this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}