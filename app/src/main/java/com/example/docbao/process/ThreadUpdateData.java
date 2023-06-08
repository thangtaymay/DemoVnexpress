package com.example.docbao.process;

import static com.example.docbao.activities.HomeActivity.vnExpressCategoryArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.docbao.activities.HomeActivity;
import com.example.docbao.database.MyDatabaseHelper;
import com.example.docbao.objects.VnExpressCategory;
import com.example.docbao.objects.VnExpressItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


/*
sử dụng để cào data trên mạng về
 */
public class ThreadUpdateData extends AsyncTask<Void, Integer, ArrayList<VnExpressItem>> {
    MyDatabaseHelper dbHelper;
    HomeActivity.ProcessInfo processInfo;

    public ThreadUpdateData(Context context, HomeActivity.ProcessInfo processInfo) {
        this.processInfo = processInfo;
        dbHelper = new MyDatabaseHelper(context);
    }

    @Override
    protected ArrayList<VnExpressItem> doInBackground(Void... voids) {// xử lý, bóc tác dữ liệu
        Log.e("ThreadUpdateData", "doInBackground");
        ArrayList<VnExpressItem> vnExpressItems; // mảng bài báo
        vnExpressItems = new ArrayList<>();
        try {
            Log.e("vnExpressCategory", "bắt đầu  cào " + vnExpressCategoryArrayList.size() + " Thể Loại");//
            for (VnExpressCategory vnExpressCategory : vnExpressCategoryArrayList) {//
                String link_the_loai = vnExpressCategory.getLink();
//                Log.e("Abc", "link chuẩn bị cào " + link_the_loai);
                Document doc = Jsoup.connect(link_the_loai).get(); // vao trang web
                Elements items = doc.select("item");
//                Log.e("doc_rss", "Có tưng đây item " + items.size() + "");

                publishProgress(vnExpressItems.size());

                for (Element item : items) {
                    String title = item.select("title").first().text();
                    String description = item.select("description").first().text();
                    String pubDate = item.select("pubDate").first().text();
                    String img = Jsoup.parse(description).select("img").attr("src");
                    String link = Jsoup.parse(description).select("a").attr("href");
                    String description_text = Jsoup.parse(description).text();
                    VnExpressItem bai_bao = new VnExpressItem(title, description_text, img, link, pubDate);
                    dbHelper.QueryData("CREATE TABLE IF NOT EXISTS BAIBAO(link TEXT PRIMARY KEY, link_the_loai TEXT, img TEXT, desciption TEXT, time TEXT, title TEXT)");
                    dbHelper.addUser(bai_bao);
                    bai_bao.setLink_the_loai(link_the_loai);
                    vnExpressItems.add(bai_bao);

                    Log.e("demo",bai_bao.toString());
                }
            }
        } catch (Exception e) {
//            Log.e("lo", e.getMessage());
        }
        return vnExpressItems;
    }

    @Override
    protected void onPostExecute(ArrayList<VnExpressItem> vnExpressItems) {// doing backgroupd chạy xong thì vào đây
        Log.e("ThreadUpdateData", "onPostExecute");

        processInfo.onResult(vnExpressItems);


    }

    @Override
    protected void onProgressUpdate(Integer... values) {// xử thông tin cập nhật trong quá doInBackground
        Log.e("ThreadUpdateData", "onProgressUpdate");
        processInfo.updateInfor("đã cào tất cả được " + values[values.length - 1]);
    }
}
