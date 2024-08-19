package com.nts.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {

    SQLiteDatabase database;
    DBHelper helper;

    public MyDatabase(Context context) {
        helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }

    public Cursor layTatCaDuLieu() {
        String[] cot = {DBHelper.COT_ID, DBHelper.COT_TEN, DBHelper.COT_LOP};

        //Cursor như là 1 bảng dữ liệu được trả ra sau khi truy vấn CSDL
        Cursor cursor = null;
        //Dùng SQLiteDatabase truy xuất phương thức "query" để lấy dữ liệu
        //Phương thức "layTatCaDuLieu" này yêu cầu lấy tất cả nên chỉ truyền các tham số
        //như: tên bảng, các cột cần lấy (cot) và sắp xếp nếu cần
        cursor = database.query(DBHelper.TEN_BANG_SINHVIEN, cot, null, null, null,null, DBHelper.COT_ID + " DESC");

        return cursor;
    }

    public long them(SinhVien sinhVien){
        //ContentValues là đối tượng lưu trữ dữ liệu
        //SQLiteDatabase sẽ nhận dữ liệu thông qua đối tượng này để thực hiện các câu lệnh truy vấn
        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_TEN, sinhVien.get_ten());
        values.put(DBHelper.COT_LOP, sinhVien.get_lop());
        return database.insert(DBHelper.TEN_BANG_SINHVIEN, null, values);
    }

    public long xoa(SinhVien sinhVien){
        //Xoá bớt một dòng dữ liệu vần 3 đối số: Tên bảng, Câu điều kiện và Đối số cho câu điều kiện
        //Nhưng có thể gộp 2 đối số cuối thành 1 như ở dưới
        return database.delete(DBHelper.TEN_BANG_SINHVIEN, DBHelper.COT_TEN + " = " + " ' " + sinhVien.get_ten() + " ' ", null);
    }

    public long sua(SinhVien sinhVien) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COT_TEN, sinhVien.get_ten());
        values.put(DBHelper.COT_LOP, sinhVien.get_lop());

        //Sửa 1 dòng dữ liệu thì cần 3 đối số: Tên bảng, Dữ liệu cần sửa và câu điều kiện
        return database.update(DBHelper.TEN_BANG_SINHVIEN, values, DBHelper.COT_ID + " = " + sinhVien.get_id(), null);
    }
}
