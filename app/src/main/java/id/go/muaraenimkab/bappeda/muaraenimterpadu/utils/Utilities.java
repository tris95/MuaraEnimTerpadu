package id.go.muaraenimkab.bappeda.muaraenimterpadu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.ValueAdd;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilities {
    //private static Toast mToast;
    private static String server = "http://www.mance.muaraenimkab.go.id/";

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

    public static String getBaseURLImageLaporan() {
        return server + "wp/gambar_laporan/";
    }

    public static String getBaseURLImageTempat() {
        return server + "wp/gambar_tempat_wisata/";
    }

    public static String getBaseURLImageEvent() {
        return server + "wp/gambar_event/";
    }

//    public static void showAsToast(Context context, String text) {
//        if (context != null) {
//            if (mToast != null) mToast.cancel();
//            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//            mToast.show();
//        }
//    }

//    public static String formatDatabaseTimeStamp(String tanggalKonfirmasi) {
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//        Date testDate = null;
//        try {
//            testDate = sdf.parse(tanggalKonfirmasi);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        return formatter.format(testDate);
//    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static void hideKeyboard(Context context) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//    }

    public static Boolean isLogin(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("xLogin", false);
    }

    public static void setLogin(final Context context, String email, String idp) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<ValueAdd> call = api.setlogindb(random, email, idp);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 0) {
                        prefsEditor.putBoolean("xLogin", false);
                        prefsEditor.apply();
                        Utilities.signOutUser(context);
                    } else if (success == 1) {
                        prefsEditor.putBoolean("xLogin", true);
                        prefsEditor.apply();
                    } else if (success == 2) {
                        prefsEditor.putBoolean("xLogin", false);
                        prefsEditor.apply();
                        Utilities.signOutUser(context);
                    }
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
            }
        });
    }

    public static String getToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token == null) {
            token = "";
        }
        return token;
    }

//    public static boolean isValidEmail(String email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }

//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        assert connectivityManager != null;
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

//    public static String getCurrency(String value) {
//        DecimalFormat currency = (DecimalFormat) NumberFormat.getInstance();
//        Locale currentLocale = new Locale("in", "ID");
//        String symbol = Currency.getInstance(currentLocale).getSymbol(currentLocale);
//        currency.setGroupingUsed(true);
//        currency.setPositivePrefix(symbol + " ");
//        currency.setNegativePrefix("-" + symbol + " ");
//        return currency.format(Double.parseDouble(value));
//    }

//    public static Bitmap getBitmapFromPath(String path) {
//        File imgFile = new File(path);
//        if (imgFile.exists()) {
//            return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        } else {
//            return null;
//        }
//    }

//    public static byte[] getStringFromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
//        return stream.toByteArray();
//    }

//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            java.net.URL url = new java.net.URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String getArrayByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //return stream.toByteArray();
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }

//    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
//        int width = image.getWidth();
//        int height = image.getHeight();
//
//        float bitmapRatio = (float) width / (float) height;
//        if (bitmapRatio > 1) {
//            width = maxSize;
//            height = (int) (width / bitmapRatio);
//        } else {
//            height = maxSize;
//            width = (int) (height * bitmapRatio);
//        }
//
//        return Bitmap.createScaledBitmap(image, width, height, true);
//    }


    public static String getRandom(int x) {
        String alphabet = "0123456789abcdefghijklmnopqrstuvwqyz";
        final int N = alphabet.length();
        Random r = new Random();
        StringBuilder alpha = new StringBuilder();
        for (int i = 0; i < x; i++) {
            alpha.append(alphabet.charAt(r.nextInt(N)));
        }
        return alpha.toString();
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
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
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
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
