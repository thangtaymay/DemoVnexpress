package com.example.docbao.activities;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docbao.database.MyDatabaseHelper;
import com.example.docbao.adaptor.CategoryAdaptor;
import com.example.docbao.objects.VnExpressCategory;
import com.example.docbao.R;
import com.example.docbao.objects.VnExpressItem;
import com.example.docbao.process.MyThreadRSSReader;
import com.example.docbao.process.ThreadUpdateData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    //    khai báo mảng động ArrayList có kiểu dữ liệu là 1 đối tượng VnExpressCategory
    public static ArrayList<VnExpressCategory> vnExpressCategoryArrayList;// mảng thể loaj
    ArrayList<VnExpressItem> vnExpressItems; // mảng bài báo
    MyDatabaseHelper dbHelper;

    //    khai báo ListView
    private ListView listCategory;

    public interface ProcessInfo {
        void updateInfor(String str);

        void onResult(ArrayList<VnExpressItem> vnExpressItems);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("eee","vao onCreate");

        setContentView(R.layout.activity_home);//

        initialData();//

        dbHelper = new MyDatabaseHelper(this);

        addUserToDatabase();

        initView();
//----------------------------------------------

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Cập nhật");
        progressDialog.show();
        new ThreadUpdateData(this, new ProcessInfo() {
            @Override
            public void updateInfor(String str) {
                progressDialog.setMessage(str);
            }

            @Override
            public void onResult(ArrayList<VnExpressItem> vnExpressItems) {
// cập nhật lại giao diện
                Log.e("eee", "cập nhật lại giao diện " + vnExpressItems.size());
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }
        }).execute();


    }

    private void initView() {
        Log.e("eee","vao initView");
//        Ánh xạ tìm ListView
        listCategory = (ListView) this.findViewById(R.id.list_cate_gory);

//        tạo new adapter để truyền
        CategoryAdaptor categoryAdaptor = new CategoryAdaptor(this, vnExpressCategoryArrayList);


        listCategory.setAdapter(categoryAdaptor);

//        bắt sự kiện onclick 1 item

        listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                VnExpressCategory vnExpressCategory = vnExpressCategoryArrayList.get(i);


                Intent intent = new Intent(HomeActivity.this, NewsActivity.class);

                intent.putExtra("item_click", vnExpressCategory);

                startActivity(intent);

            }
        });


    }


    //    tạo hàm initialData để truyền dữ liệu
    private void initialData() {

        Log.e("eee","vao  initialData()");

        vnExpressCategoryArrayList = new ArrayList<>();// mang du lieu


        vnExpressCategoryArrayList.add(new VnExpressCategory("Trang chủ", "https://vnexpress.net/rss/tin-moi-nhat.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Thế giới", "https://vnexpress.net/rss/the-gioi.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Thời sự", "https://vnexpress.net/rss/thoi-su.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Kinh doanh", "https://vnexpress.net/rss/kinh-doanh.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Start up", "https://vnexpress.net/rss/start-up.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Giải trí", "https://vnexpress.net/rss/giai-tri.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Thể thao", "https://vnexpress.net/rss/the-thao.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Pháp luật", "https://vnexpress.net/rss/phap-luat.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Giáo dục", "https://vnexpress.net/rss/giao-duc.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Sức khỏe", "https://vnexpress.net/rss/suc-khoe.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Đời sống", "https://vnexpress.net/rss/doi-song.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Du lịch", "https://vnexpress.net/rss/du-lich.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Khoa học", "https://vnexpress.net/rss/thoi-su.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Số hóa", "https://vnexpress.net/rss/so-hoa.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Tin Mới nhất", "https://vnexpress.net/rss/tin-moi-nhat.rss"));

        vnExpressCategoryArrayList.add(new VnExpressCategory("Ý kiến", "https://vnexpress.net/rss/y-kien.rss"));

        vnExpressItems = new ArrayList<>();

        new Thread() {

            @Override
            public void run() {

            }

        }.start();


    }

    private void addUserToDatabase() {

        Log.e("eee","vao addUserToDatabase()");

        VnExpressItem vnExpressItem = new VnExpressItem("title", "desiption", "img", "link", "time");

        dbHelper.addUser(vnExpressItem);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MyThreadRSSReader.ThanhCong) {
                vnExpressItems = (ArrayList<VnExpressItem>) msg.obj;
                Log.e("eee", "vnExpressCategory cào được " + vnExpressItems.size() + " bài báo");
                for (VnExpressItem vnExpressItem : vnExpressItems) {
//                    Log.e("--------", "---------------------------");
//                    Log.e("title", vnExpressItem.getTitle());
//                    Log.e("desciption", vnExpressItem.getDesciption());
//                    Log.e("img", vnExpressItem.getImg());
//                    Log.e("link", vnExpressItem.getLink());
//                    Log.e("time", vnExpressItem.getTime());
                }
            } else {
            }
        }
    };


}

