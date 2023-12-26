package vn.edu.stu.doanck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.edu.stu.doanck.model.categories;
import vn.edu.stu.doanck.model.product;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_CATEGORIES ="categories";
    private static final String TABLE_PRODUCT ="product";
    private static final String COLUMN_CATEGORIES_ID = "id";
    private static final String COLUMN_CATEGORIES_NAME = "name";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME="tensp";
    private static final String COLUMN_PRODUCT_QUANTITY="soluong";
    private static final String COLUMN_PRODUCT_IMAGE="image";
    private static final String COLUMN_PRODUCT_GIA="gia";
    private static final String COLUMN_PRODUCT_CATEGORIES_ID ="categories_id";

    public DBHelper(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<product> getAllProducts(){
        List<product> productList = new ArrayList<>();
        String[] projection = {
                DBHelper.COLUMN_PRODUCT_ID,
                DBHelper.COLUMN_PRODUCT_NAME,
                DBHelper.COLUMN_PRODUCT_QUANTITY,
                DBHelper.COLUMN_PRODUCT_IMAGE,
                DBHelper.COLUMN_PRODUCT_GIA,
                DBHelper.COLUMN_PRODUCT_CATEGORIES_ID
        };

        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_PRODUCT,projection,null,null,null,null,null
        );

        while(cursor.moveToNext()){
            product pd = new product(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_CATEGORIES_ID)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_IMAGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_QUANTITY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PRODUCT_GIA))
            );
            productList.add(pd);
        }
        cursor.close();
        return productList;
    }

    public  List<categories> getAllCategories(){
        List<categories> categoriesList = new ArrayList<>();
        String[] projection = {
                DBHelper.COLUMN_CATEGORIES_ID,
                DBHelper.COLUMN_CATEGORIES_NAME
        };
        Cursor cursor = getReadableDatabase().query(
                DBHelper.TABLE_CATEGORIES,projection,null,null,null,null,null
        );
        while (cursor.moveToNext()){
            categories cate = new categories(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CATEGORIES_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_CATEGORIES_NAME))
            );
            categoriesList.add(cate);
        }
        cursor.close();
        return categoriesList;
    }

    public long addProduct(product pd){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_PRODUCT_NAME,pd.getTen());
        values.put(DBHelper.COLUMN_PRODUCT_QUANTITY,pd.getSoluong());
        values.put(DBHelper.COLUMN_PRODUCT_IMAGE,pd.getHinh());
        values.put(DBHelper.COLUMN_PRODUCT_GIA,pd.getGia());
        values.put(DBHelper.COLUMN_PRODUCT_CATEGORIES_ID,pd.getPhanLoai());

        return getReadableDatabase().insert(DBHelper.TABLE_PRODUCT,null,values);
    }

    public String getCategoryName(int categoryID){
        List<categories> categoriesList = getAllCategories();
        for(categories cate : categoriesList){
            if(categoryID == cate.getMaCate()){
                return cate.getTenLoai();
            }
        }

        return "";
    }

    public long updateProduct(product pd){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_PRODUCT_NAME,pd.getTen());
        values.put(DBHelper.COLUMN_PRODUCT_QUANTITY,pd.getSoluong());
        values.put(DBHelper.COLUMN_PRODUCT_GIA,pd.getGia());
        values.put(DBHelper.COLUMN_PRODUCT_IMAGE,pd.getHinh());
        values.put(DBHelper.COLUMN_PRODUCT_CATEGORIES_ID,pd.getPhanLoai());

        String selection = DBHelper.COLUMN_PRODUCT_ID + " = ?";
        String[] selectionArgs = {pd.getMa()+""};

        return getReadableDatabase().update(DBHelper.TABLE_PRODUCT,values,selection,selectionArgs);
    }

    public long deleteProduct(int prouctID){
        String selection = DBHelper.COLUMN_PRODUCT_ID + "= ?";
        String[] selectionArgs = {String.valueOf(prouctID)};
        return getReadableDatabase().delete(DBHelper.TABLE_PRODUCT,selection,selectionArgs);
    }

    public  long addCategory(categories cate){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_CATEGORIES_NAME,cate.getTenLoai());

        return getReadableDatabase().insert(DBHelper.TABLE_CATEGORIES,null,values);
    }

    public  long updateCategory(categories cate){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_CATEGORIES_NAME,cate.getTenLoai());

        String selection = DBHelper.COLUMN_CATEGORIES_ID + " = ?";
        String[] selectionArgs = {cate.getMaCate()+""};
        return getReadableDatabase().update(DBHelper.TABLE_CATEGORIES,values,selection,selectionArgs);
    }

    public long deleteCategory(int cateId){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {DBHelper.COLUMN_PRODUCT_ID};
        String selection = DBHelper.COLUMN_PRODUCT_CATEGORIES_ID + "= ?";
        String[] selectionArgs = {String.valueOf(cateId)};

        Cursor cursor = db.query(
                DBHelper.TABLE_PRODUCT,projection,selection,selectionArgs,null,null,null
        );
        int count = cursor.getCount();
        cursor.close();

        if(count > 0){
            return -1;
        }else {
            selection = DBHelper.COLUMN_CATEGORIES_ID + "= ?";
            selectionArgs = new String[]{String.valueOf(cateId)};
            return getWritableDatabase().delete(DBHelper.TABLE_CATEGORIES,selection,selectionArgs);
        }
    }
}
