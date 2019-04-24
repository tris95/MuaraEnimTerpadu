package id.go.muaraenimkab.mance.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.utils.Utilities;

/**
 * Created by Tris on 4/5/2018.
 */

@SuppressLint("Registered")
public class FullImageEvent extends AppCompatActivity {
    TouchImageView fullImageView;
//    ImageView ivBackground;
    String imgURL;
    Toolbar toolbar;
    TextView tvDetail, tvTitle;
    RelativeLayout rlup;
    boolean flag;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        toolbar = findViewById(R.id.toolbar);
        tvDetail = findViewById(R.id.tv_ket);
        tvTitle = findViewById(R.id.tv_title);
        rlup = findViewById(R.id.rlup);

        fullImageView = findViewById(R.id.image);
//        ivBackground = findViewById(R.id.iv_background);

        flag = false;
        tvDetail.setMaxLines(2);

        tvTitle.setText(getIntent().getStringExtra("event"));
        tvDetail.setText(getIntent().getStringExtra("tgl")+" di "+getIntent().getStringExtra("loc")+"\n\n"+getIntent().getStringExtra("des"));

        rlup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    tvDetail.setMaxLines(2);
                    flag = false;
                }else {
                    tvDetail.setMaxLines(100);
                    flag = true;
                }
            }
        });

//        setSupportActionBar(toolbar);

        Objects.requireNonNull(this).setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra("event"));
        }

        if (getIntent().getStringExtra("img").equals("")){
            imgURL = "";
        }else {
            imgURL = Utilities.getBaseURLImageEvent() + getIntent().getStringExtra("img");
        }
//        Log.e("URL", imgURL);

        new DownloadImageTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        Bitmap imagebitmap;
        protected Bitmap doInBackground(String... urls) {
            Log.e("url image", ""+imgURL);
            if (imgURL.equals("")){
                Drawable myDrawable = getResources().getDrawable(R.drawable.ic_me_colorful);
                imagebitmap = ((BitmapDrawable) myDrawable).getBitmap();
//                ivBackground.setImageBitmap(imagebitmap);
//                ivBackground.setAlpha((float) 0.3);
            }else {
                imagebitmap = Utilities.getBitmapFromURL(imgURL);
//                ivBackground.setImageBitmap(imagebitmap);
//                ivBackground.setAlpha((float) 0.3);
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
//            imagebitmap = Bitmap.createScaledBitmap(imagebitmap, 480, 480, true);
            fullImageView.setImageBitmap(imagebitmap);
            fullImageView.setMaxZoom(4f);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onBackPressed();
    }
}
