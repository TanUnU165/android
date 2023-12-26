package vn.edu.stu.doanck.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import vn.edu.stu.doanck.AddUpdateProduct;
import vn.edu.stu.doanck.ProuctDetails;
import vn.edu.stu.doanck.R;
import vn.edu.stu.doanck.ShowProduct;
import vn.edu.stu.doanck.dao.DBHelper;
import vn.edu.stu.doanck.model.product;

public class ProductAdapter extends ArrayAdapter<product> {

    private Activity context;
    private int resource;
    private List<product> objects;
    private DBHelper dbHelper;
    private ImageView imageView;
    private TextView tvMa;
    private TextView tvName;
    private TextView tvType;
    private ImageButton btnMenu;

    public ProductAdapter(Activity context, int resource, List<product> objects, DBHelper dbHelper) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.dbHelper = dbHelper;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource, parent, false);

        imageView = view.findViewById(R.id.imageView);
        tvMa = view.findViewById(R.id.tvMa);
        tvName = view.findViewById(R.id.tvTen);
        tvType = view.findViewById(R.id.tvType);

        btnMenu = view.findViewById(R.id.btnMenu);

        final product pd = objects.get(position);

        tvMa.setText(pd.getMa() + "");
        tvName.setText(pd.getTen().toString());

        Bitmap bitmap = BitmapFactory.decodeByteArray(pd.getHinh(), 0, pd.getHinh().length);
        imageView.setImageBitmap(bitmap);

        tvType.setText(dbHelper.getCategoryName(pd.getPhanLoai()));

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();

                inflater.inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.itDetail) {
                            Intent intent = new Intent(getContext(), ProuctDetails.class);
                            intent.putExtra("product", pd);
                            getContext().startActivity(intent);

                            return true;
                        }

                        if (item.getItemId() == R.id.itUpdate) {
                            Intent intent = new Intent(getContext(), AddUpdateProduct.class);
                            intent.putExtra("product", pd);
                            ((Activity) getContext()).startActivityForResult(intent, ShowProduct.update);

                            return true;
                        }

                        if (item.getItemId() == R.id.itDelete) {
                            showDeleteConfirmationDialog(position);

                            return true;
                        }

                        return false;
                    }
                });
            }
        });

        return view;
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xác nhận xoá");
        builder.setMessage("Bạn có chắc chắn muốn xoá không?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long result = dbHelper.deleteProduct(objects.get(position).getMa());
                if (result > 0) {
                    objects.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
