package id.go.muaraenimkab.mance.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.utils.Utilities;

/**
 * Created by Tris on 4/5/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    TextView textView;
    String myVersionCode;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//
//        @SuppressLint("InlinedApi") int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.splash_screen);
        textView = findViewById(R.id.tvVersion);

        PackageManager packageManager = SplashScreenActivity.this.getPackageManager();
        String packageName = SplashScreenActivity.this.getPackageName();
        myVersionCode = "null";

        try {
            myVersionCode = String.valueOf(packageManager.getPackageInfo(packageName, 0).versionName);
            textView.setText(myVersionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        splashTread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    int waited = 0;
//                    while (waited < 1500) {
//                        sleep(100);
//                        waited += 100;
//                    }
//                    if (!Utilities.isFirstLaunch(SplashScreenActivity.this)) {
//                        Utilities.setFirstLaunch(SplashScreenActivity.this);
//                    }
//
//                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(intent);
//                    finish();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    finish();
//                }
//            }
//        };
//        splashTread.start();

        if (!Utilities.isFirstLaunch(SplashScreenActivity.this)) {
            Utilities.setFirstLaunch(SplashScreenActivity.this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new VersionChecker().execute();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            Snackbar.make(findViewById(android.R.id.content), "Loading...",
                    Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }

//    @SuppressLint("StaticFieldLeak")
//    private class GetVersionCode extends AsyncTask<Void, String, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            try {
////                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+SplashScreenActivity.this.getPackageName()+"&hl=en")
////                        .timeout(5000)
////                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
////                        .referrer("http://www.google.com")
////                        .get()
////                        .select("div[itemprop=softwareVersion]")
////                        .first()
////                        .ownText();
//
//                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashScreenActivity.this.getPackageName()+ "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select("div.hAyfc:nth-child(3) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
//                        .first()
//                        .ownText();
//
////                Log.e("latestversion","---"+newVersion);
//
//                return newVersion;
//            } catch (Exception e) {
//                Log.e("error version", ""+e.toString());
//                return newVersion;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String onlineVersion) {
//            super.onPostExecute(onlineVersion);
//            PackageManager packageManager = SplashScreenActivity.this.getPackageManager();
//            String packageName = SplashScreenActivity.this.getPackageName();
//            String myVersionCode = "null";
//            try {
//                myVersionCode = String.valueOf(packageManager.getPackageInfo(packageName, 0).versionName);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            Log.e("version", myVersionCode+" "+onlineVersion);
//
//            if (!myVersionCode.equalsIgnoreCase(onlineVersion)) {
//                //show dialog
//                new AlertDialog.Builder(SplashScreenActivity.this)
//                        .setTitle("Update aplikasi tersedia")
//                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                                try {
////                                    Toast.makeText(getApplicationContext(), "App is in BETA version cannot update", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                } catch (ActivityNotFoundException anfe) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
//                                }
//                            }
//                        })
//                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                                dialog.dismiss();
//                                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                                startActivity(myIntent);
//                                finish();
////                                onBackPressed();
//                            }
//                        })
//                        .setCancelable(false)
//                        .setIcon(R.drawable.ic_action_info)
//                        .show();
//
//            }else {
//                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(myIntent);
//                finish();
//            }
//        }
//    }

    @SuppressLint("StaticFieldLeak")
    public class VersionChecker extends AsyncTask<String, Document, Document> {

        private Document document;

        @Override
        protected Document doInBackground(String... params) {

            try {
                document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashScreenActivity.this.getPackageName() + "&hl=en")
                        .timeout(3000)
                        .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                        .get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return document;
        }

        @Override
        protected void onPostExecute(Document d) {
            super.onPostExecute(d);

            String newVersion = "bull";

            try {
                Elements es = d.body().getElementsByClass("xyOfqd").select(".hAyfc");
                newVersion = es.get(3).child(1).child(0).child(0).ownText();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("version", myVersionCode + " " + newVersion);

            if (Utilities.isNetworkAvailable(SplashScreenActivity.this)) {
                if (!myVersionCode.equalsIgnoreCase(newVersion)) {
                    //show dialog
                    new AlertDialog.Builder(SplashScreenActivity.this)
                            .setTitle("Pembaruan aplikasi tersedia")
                            .setPositiveButton("Perbarui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
//                                    Toast.makeText(getApplicationContext(), "App is in BETA version cannot update", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.dismiss();
                                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    finish();
//                                onBackPressed();
                                }
                            })
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_action_info)
                            .show();

                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            } else {
                Thread splashTread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            int waited = 0;
                            while (waited < 1500) {
                                sleep(100);
                                waited += 100;
                            }

                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            finish();
                        }
                    }
                };
                splashTread.start();
            }
        }
    }

}
