package com.example.docbao.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docbao.activities.HomeActivity;
import com.example.docbao.activities.NewsActivity;
import com.example.docbao.objects.VnExpressCategory;
import com.example.docbao.R;

import java.util.ArrayList;

// kế thừa pt baseAdapter
public class CategoryAdaptor extends BaseAdapter {



    private Context context;
    private ArrayList<VnExpressCategory> vnExpressCategories;

//    khởi tạo hàm contractor
    public CategoryAdaptor(Context context, ArrayList<VnExpressCategory> vnExpressCategories) {
        this.context = context;
        this.vnExpressCategories = vnExpressCategories;

    }


    @Override
    public int getCount() {//    số lượng item cần hiển thị
        return vnExpressCategories.size();
    }

    @Override
    public VnExpressCategory getItem(int i) {
        return vnExpressCategories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();// khoửi tạo inflater
            rowView = inflater.inflate(R.layout.item_view_category, viewGroup, false);// từ ìnflater ta tạo ra một cái layout item giao diện view
        }
        TextView lblName = (TextView) rowView.findViewById(R.id.lbl_name);
        ImageView btn_click = rowView.findViewById(R.id.btn_click);


//        btn_click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, vnExpressCategories.get(position).getLink(), Toast.LENGTH_SHORT).show();
//
//
//                Intent intent = new Intent(context, NewsActivity.class);
//                intent.putExtra("item_click", vnExpressCategories.get(position));
//                context.startActivity(intent);
//
//
//            }
//        });

//         các hàm findViewby : take time ( tốn tgian )
        lblName.setText(vnExpressCategories.get(position).getName());
        return rowView;


    }


}
