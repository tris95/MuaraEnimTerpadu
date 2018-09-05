package id.go.muaraenimkab.bappeda.muaraenimterpadu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utilities {
    private static Toast mToast;
    private static String server = "http://.co.id/";
    private static String alphabet = "0123456789abcdefghijklmnopqrstuvwqyz";

    public static String getBaseURLUser() {
        return server + "";
    }

    public static String getURLImageIklan() {
        return server + "";
    }

    public static String getBaseURLImageUser() {
        return server + "";
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

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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

    public Boolean isLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("xLogin", false);
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
}
