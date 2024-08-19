package com.nts.quanlysinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<SinhVien> sinhViens;

    public MyAdapter(Context context, List<SinhVien> sinhViens) {
        this.context = context;
        this.sinhViens = sinhViens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sinhViens.size();
    }

    @Override
    public Object getItem(int position) {
        return sinhViens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sinhViens.get(position).get_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.design_item_list_view, parent, false);
            holder = new ViewHolder();
            holder.textViewID = convertView.findViewById(R.id.item_textView_ID);
            holder.textViewTen = convertView.findViewById(R.id.item_textView_Ten);
            holder.textViewLop = convertView.findViewById(R.id.item_textView_Lop);
            holder.imageDelete = convertView.findViewById(R.id.imageDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SinhVien sinhVien = sinhViens.get(position);
        holder.textViewID.setText(String.valueOf(sinhVien.get_id()));
        holder.textViewTen.setText(sinhVien.get_ten());
        holder.textViewLop.setText(sinhVien.get_lop());

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.database.xoa(sinhVien);
                sinhViens.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView textViewID;
        TextView textViewTen;
        TextView textViewLop;
        ImageView imageDelete;
    }
}
