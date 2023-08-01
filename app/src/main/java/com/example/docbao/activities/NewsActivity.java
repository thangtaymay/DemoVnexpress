package com.example.docbao.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.docbao.R;
import com.example.docbao.adaptor.NewsAdaptor;
import com.example.docbao.objects.VnExpressCategory;
import com.example.docbao.objects.VnExpressItem;
import com.example.docbao.process.MyThreadRSSReader;

import java.util.ArrayList;

public class NewsActivity extends Activity {

    //     khai báo mảng động ArrayList có kiểu dữ liệu là 1 đối tượng VnExpressItem
    ArrayList<VnExpressItem> vnExpressItems;
    TextView tvTitle;
    ImageView imageViewBack;
    ProgressBar progressBarTaiDuLieu;
    ListView lvBaiBao;

    public static VnExpressItem vnExpressItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initData();

    }

    private void initData() {
        VnExpressCategory vnExpressCategory = (VnExpressCategory) getIntent().getSerializableExtra("item_click");
        Toast.makeText(NewsActivity.this, "new activty " + vnExpressCategory.getLink(), Toast.LENGTH_SHORT).show();
        // lay du lieu tu 1 the loai

        tvTitle.setText(vnExpressCategory.getName());
        MyThreadRSSReader myThreadReadRss = new MyThreadRSSReader(vnExpressCategory.getLink(), handler);
        myThreadReadRss.start();


    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        lvBaiBao = findViewById(R.id.lv_bai_bao);
        imageViewBack = findViewById(R.id.imv_back);

        Log.e("xxxxxx","xxxx");

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsActivity.this.onBackPressed();
            }
        });
        progressBarTaiDuLieu = findViewById(R.id.progress_bar_tai_du_lieu);

        lvBaiBao.setVisibility(View.GONE); // an view di
        progressBarTaiDuLieu.setVisibility(View.VISIBLE); // hien view di

        lvBaiBao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vnExpressItem = vnExpressItems.get(i);

//
                Log.e("eee","eee"+vnExpressItem.getImg());

               Intent intent = new Intent(NewsActivity.this, ImageActivity.class);
                intent.putExtra("VnExpressItem", vnExpressItem);
                startActivity(intent);

            }

        });
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MyThreadRSSReader.ThanhCong) {
                vnExpressItems = (ArrayList<VnExpressItem>) msg.obj;
//                for (VnExpressItem vnExpressItem : vnExpressItems) {
//                    Log.e("--------", "---------------------------");
//                    Log.e("title", vnExpressItem.getTitle());
//                    Log.e("desciption", vnExpressItem.getDesciption());
//                    Log.e("img", vnExpressItem.getImg());
//                    Log.e("link", vnExpressItem.getLink());
//                    Log.e("time", vnExpressItem.getTime());
//                }

                initListViewData();


            } else {

            }
        }
    };

    private void initListViewData() {
        lvBaiBao.setVisibility(View.VISIBLE); // hien view di
        progressBarTaiDuLieu.setVisibility(View.GONE); // an view di
        NewsAdaptor newsAdaptor = new NewsAdaptor(vnExpressItems, NewsActivity.this);
        lvBaiBao.setAdapter(newsAdaptor);

    }

    public void openImage(View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

}
