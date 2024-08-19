package com.nts.quanlysinhvien;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static ListView listView;
    public static ArrayList<SinhVien> sinhViens;
    public static MyDatabase database;
    private EditText editTextTen, editTextLop;
    private Button btnThem, btnSua;
    private static long id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new MyDatabase(this);
        listView = findViewById(R.id.listSinhVien);
        editTextTen = findViewById(R.id.txtTen);
        editTextLop = findViewById(R.id.txtLop);
        btnSua = findViewById(R.id.btnSua);
        btnThem = findViewById(R.id.btnThem);

        capNhatDuLieu();

        if (sinhViens != null) {
            listView.setAdapter(new MyAdapter(this, sinhViens));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                editTextTen.setText(sinhViens.get(position).get_ten());
                editTextLop.setText(sinhViens.get(position).get_lop());
                MainActivity.id = id;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                them(view);
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sua(view);
            }
        });
    }

    @SuppressLint("Range")
    public void capNhatDuLieu() {
        if (sinhViens == null) {
            sinhViens = new ArrayList<SinhVien>();
        } else {
            sinhViens.clear();
        }

        Cursor cursor = database.layTatCaDuLieu();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                SinhVien sinhVien = new SinhVien();
                sinhVien.set_id(cursor.getInt(cursor.getColumnIndex(DBHelper.COT_ID)));
                sinhVien.set_ten(cursor.getString(cursor.getColumnIndex(DBHelper.COT_TEN)));
                sinhVien.set_lop(cursor.getString(cursor.getColumnIndex(DBHelper.COT_LOP)));
                sinhViens.add(sinhVien);
            }
        }
    }

    public SinhVien layDuLieuNguoiDung() {
        String ten = editTextTen.getText().toString();
        String lop = editTextLop.getText().toString();
        if (ten.trim().isEmpty() || lop.trim().isEmpty())
            return null;
        SinhVien sinhVien = new SinhVien();
        sinhVien.set_id(id);
        sinhVien.set_ten(ten);
        sinhVien.set_lop(lop);
        return sinhVien;
    }

    public void them(View view) {
        SinhVien sinhVien1 = layDuLieuNguoiDung();
        if (sinhVien1 != null) {
            if (database.them(sinhVien1) != -1) {
                sinhViens.add(sinhVien1);
                capNhatDuLieu();
                listView.invalidateViews();
                editTextLop.setText(null);
                editTextTen.setText(null);
                id = -1;
            }
        }
    }

    public void sua(View view) {
        SinhVien sinhVien1 = layDuLieuNguoiDung();
        if (sinhVien1 != null && id != -1) {
            database.sua(sinhVien1);
            capNhatDuLieu();
            listView.invalidateViews();
            editTextTen.setText(null);
            editTextLop.setText(null);
            id = -1;
        }
    }
}
