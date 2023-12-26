package vn.edu.stu.doanck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.stu.doanck.dao.DBHelper;
import vn.edu.stu.doanck.model.categories;
import vn.edu.stu.doanck.model.product;

public class AddUpdateProduct extends AppCompatActivity {
    private ImageView imageView;
    private EditText txtName;
    private EditText txtPrice;
    private EditText txtQuantity;
    private Spinner spinner;
    private Button confirm;
    private Button selectImage;
    private product pd;
    private ArrayAdapter<String> spinnerAdapter;
    private DBHelper dbHelper;
    private BottomNavigationView menu;
    private ShowProduct showProduct = new ShowProduct();

    static final int RESULT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_product);
        addControls();

        List<categories> categoryList = dbHelper.getAllCategories();
        List<String> list = categoryList.stream()
                .map(category -> category.getTenLoai()).collect(Collectors.toList());

        spinnerAdapter = new ArrayAdapter<>(
                AddUpdateProduct.this, android.R.layout.simple_spinner_dropdown_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        controlUpdateAction(categoryList);
        showProduct.setActionMenu(AddUpdateProduct.this, menu);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer categoryId = categoryList.get(position).getMaCate();
                pd.setPhanLoai(categoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageIntent, AddUpdateProduct.RESULT_IMAGE);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                Integer price = Integer.parseInt(txtPrice.getText().toString());
                String quantity = txtQuantity.getText().toString();

                pd.setTen(name);
                pd.setSoluong(Integer.valueOf(quantity));
                pd.setGia(price);

                Intent intent = getIntent();
                intent.putExtra("product", pd);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void controlUpdateAction(List<categories> list) {
        Intent intent = getIntent();

        if (intent.hasExtra("product")) {
            pd = (product) intent.getSerializableExtra("product");

            txtName.setText(pd.getTen());
            txtQuantity.setText(pd.getSoluong().toString());
            txtPrice.setText(pd.getGia() + "");

            Bitmap bitmap = BitmapFactory.decodeByteArray(pd.getHinh(), 0, pd.getHinh().length);
            imageView.setImageBitmap(bitmap);

            List<Integer> listId = list.stream().map(category -> category.getMaCate()).collect(Collectors.toList());
            int position = listId.indexOf(pd.getPhanLoai());

            spinner.setSelection(position);
        }
    }

    private void addControls() {
        imageView = findViewById(R.id.img_product_add_update);
        txtName = findViewById(R.id.txtProductName);
        txtPrice = findViewById(R.id.txtProductPrice);
        txtQuantity = findViewById(R.id.txtProductQuantity);
        spinner = findViewById(R.id.spinner);
        confirm = findViewById(R.id.confirm);
        selectImage = findViewById(R.id.btnImage);
        pd = new product();
        dbHelper = new DBHelper(AddUpdateProduct.this);
        menu = findViewById(R.id.menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AddUpdateProduct.RESULT_IMAGE) {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    int image = (int) (bitmap.getHeight() * (256.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 256, image, true);
                    imageView.setImageBitmap(scaled);
                    pd.setHinh(getBytes(scaled));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);

        return stream.toByteArray();
    }
}