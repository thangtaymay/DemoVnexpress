package com.example.docbao.activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.docbao.R;
import com.example.docbao.objects.VnExpressItem;
import com.example.docbao.process.MyThreadRSSReader;
import java.util.ArrayList;
public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;
    ArrayList<VnExpressItem> vnExpressItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.image_view);
//         Lấy đối tượng VnExpressCategory từ Intent (đảm bảo bạn đã đặt giá trị cho nó trong Intent từ Activity trước)
        VnExpressItem vnExpressItem = (VnExpressItem) getIntent().getSerializableExtra("VnExpressItem");
//        để lấy 1 đối tượng từ màn trước thông qua intent cần dùng pt getSerializableExtra hoặc getParcelableExtra
//        nhớ là đối tượng đó phải implements pt tương ứng

        Log.e("xxxxxx:",vnExpressItem.getImg());
            // Load image from URL using Glide library
            Glide.with(getApplicationContext())
                    .load(vnExpressItem.getImg())
                    .placeholder(R.drawable.ic_launcher_background)// Truyền link ảnh từ VnExpressItem vào Glide
                    .into(imageView);
            Log.e("eee","link "+vnExpressItem.getImg());

    }
}