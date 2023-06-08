package com.example.docbao.adaptor;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.docbao.R;
import com.example.docbao.activities.NewsActivity;
import com.example.docbao.objects.VnExpressItem;

import java.util.ArrayList;

public class NewsAdaptor extends BaseAdapter {

    ArrayList<VnExpressItem> vnExpressItems;

    NewsActivity newsActivity;

    public NewsAdaptor(ArrayList<VnExpressItem> vnExpressItems, NewsActivity newsActivity) {
        this.vnExpressItems = vnExpressItems;
        this.newsActivity = newsActivity;
    }

    @Override
    public int getCount() {
        return vnExpressItems.size();
    }

    @Override
    public VnExpressItem getItem(int i) {
        return vnExpressItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {

        LinearLayout viewItem = (LinearLayout) View.inflate(newsActivity, R.layout.item_news, null);


        ImageView imageViewBia = viewItem.findViewById(R.id.imv_anh_bia);
        TextView tvTitle = viewItem.findViewById(R.id.tv_title), tvMoTa = viewItem.findViewById(R.id.tv_mo_ta_ngan), tvTime = viewItem.findViewById(R.id.tv_thoi_gian);
        VnExpressItem vnExpressItem = getItem(index);
        tvTitle.setText(vnExpressItem.getTitle());
        tvMoTa.setText(vnExpressItem.getDesciption());
        tvTime.setText(vnExpressItem.getTime());
        Glide.with(newsActivity).load(vnExpressItem.getImg()).into(imageViewBia);
        return viewItem;

    }
}
