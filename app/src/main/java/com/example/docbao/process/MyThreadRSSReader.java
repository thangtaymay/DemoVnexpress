package com.example.docbao.process;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.docbao.objects.VnExpressItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MyThreadRSSReader extends Thread {
    String url_rss;
    Handler handler;
    ArrayList<VnExpressItem> vnExpressItems;

    public MyThreadRSSReader(String url_rss, Handler handler) {
        this.url_rss = url_rss;
        this.handler = handler;
        vnExpressItems = new ArrayList<VnExpressItem>();
    }

    public static int ThanhCong = 1;
    public static int ThatBai = 0;

    @Override
    public void run() {
        Message message = new Message();

        try {
            Document doc = Jsoup.connect(url_rss).get(); // vao trang web
            Elements items = doc.select("item");
            Log.e("e","lấy ra từ database");
            Log.e("doc_rss", items.size() + "");
            for (Element item : items) {
                String title = item.select("title").first().text();
                String description = item.select("description").first().text();
                String pubDate = item.select("pubDate").first().text();
                String img = Jsoup.parse(description).select("img").attr("src");
                String link = Jsoup.parse(description).select("a").attr("href");
                String description_text = Jsoup.parse(description).text();
                vnExpressItems.add(new VnExpressItem(title, description_text, img, link, pubDate));
            }
            message.obj = vnExpressItems;
            message.what = ThanhCong;
        } catch (IOException e) {
            message.obj = null;
            message.what = ThatBai;
            e.printStackTrace();
        }
        handler.sendMessage(message);
    }
}
