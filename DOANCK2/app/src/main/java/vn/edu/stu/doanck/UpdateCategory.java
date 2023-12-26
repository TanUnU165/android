package vn.edu.stu.doanck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import vn.edu.stu.doanck.dao.DBHelper;
import vn.edu.stu.doanck.model.categories;

public class UpdateCategory extends AppCompatActivity {
    private ShowProduct showProduct = new ShowProduct();
    private BottomNavigationView menu;
    private ArrayAdapter<categories> adapter;
    private DBHelper dbHelper;
    private ListView listView;
    private TextView tvCategoryID;
    private EditText etCategoryName;
    private categories category;
    private ImageButton btnAdd;
    private List<categories> categoryList;
    private Button button;
    private int pos = -1;
    private Button btnDeleteCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        addControls();
        setItemData();

        btnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos < 0){
                    Toast.makeText(UpdateCategory.this,"Chọn loại muốn xóa",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCategory.this);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc muốn xóa không ?");
                    builder.setPositiveButton("Đống ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer id = Integer.parseInt(tvCategoryID.getText().toString());
                            long result = dbHelper.deleteCategory(id);
                            if(result > 0 ){
                                categoryList.remove(pos);
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(UpdateCategory.this,"Xóa thất bati",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                     builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
}
    private void setItemData(){
        Intent intent = getIntent();

        if(intent.hasExtra("categories")){
            category = (categories) intent.getSerializableExtra("categories");
            etCategoryName.setText(category.getTenLoai());
        }
    }
    private void addControls() {
        tvCategoryID = findViewById(R.id.txtIdCategory);
        etCategoryName = findViewById(R.id.txtCategory);
        btnDeleteCategory = findViewById(R.id.btnDeleteCategory);
    }
}