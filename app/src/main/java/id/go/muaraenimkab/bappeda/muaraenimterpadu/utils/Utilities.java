package id.go.muaraenimkab.bappeda.muaraenimterpadu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.cert.CertificateException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import okhttp3.OkHttpClient;

public class Utilities {
    private static Toast mToast;
    private static String server = "http://www.freshyummy.co.id/mance/";
    private static String alphabet = "0123456789abcdefghijklmnopqrstuvwqyz";

    public static String getBaseURLUser() {
        return server + "android/user/";
    }

    public static String getURLImageIklan() {
        return server + "wp/gambar_iklan/";
    }

    public static String getURLImagePariwisata() {
        return server + "wp/gambar_pariwisata/";
    }

    public static String getURLImageKategoriPariwisata() {
        return server + "wp/gambar_kategori_pariwisata/";
    }

    public static String getURLImageKategoriBerita() {
        return server + "wp/gambar_kategori_berita/";
    }
    public static String getURLImageBerita() {
        return server + "wp/gambar_berita/";
    }

    public static String getURLImageKontak() {
        return server + "wp/gambar_kontak_penting/";
    }

    public static String getBaseURLImageUser() {
        return server + "wp/gambar_user/";
    }

    public static void showAsToast(Context context, String text) {
        if (context != null) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static String formatDatabaseTimeStamp(String tanggalKonfirmasi) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date testDate = null;
        try {
            testDate = sdf.parse(tanggalKonfirmasi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(testDate);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("xUser", json);
        prefsEditor.apply();
    }

    public static void signOutUser(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putBoolean("xLogin", false);
        prefsEditor.apply();
    }

    public static User getUser(Context context) {
        if (isLogin(context)) {
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);

            Gson gson = new Gson();
            String json = mPrefs.getString("xUser", "");
            return gson.fromJson(json, User.class);
        } else {
            return new User();
        }
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static Boolean isLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("xLogin", false);
    }

    public static void setLogin(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putBoolean("xLogin", true);
        prefsEditor.apply();
    }

//    public static String getToken() {
//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.e("token", token);
//        if (token == null) {
//            token = "";
//        }
//        return token;
//    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getCurrency(String value) {
        DecimalFormat currency = (DecimalFormat) NumberFormat.getInstance();
        Locale currentLocale = new Locale("in", "ID");
        String symbol = Currency.getInstance(currentLocale).getSymbol(currentLocale);
        currency.setGroupingUsed(true);
        currency.setPositivePrefix(symbol + " ");
        currency.setNegativePrefix("-" + symbol + " ");
//        currency.setMinimumFractionDigits(2);
//        currency.setMaximumFractionDigits(2);
        return currency.format(Double.parseDouble(value));

        //Locale currentLocale=new Locale("in","ID");
        //NumberFormat currency=NumberFormat.getCurrencyInstance(currentLocale);
        //return currency.format(Double.parseDouble(value));
    }

    public static Bitmap getBitmapFromPath(String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        } else {
            return null;
        }
    }

    public static byte[] getStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
        return stream.toByteArray();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getArrayByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //return stream.toByteArray();
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String getRandom(int x){
        final int N = alphabet.length();
        Random r = new Random();
        String alpha="";
        for (int i = 0; i < x; i++) {
            alpha = alpha+alphabet.charAt(r.nextInt(N));
        }
        return alpha;
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
