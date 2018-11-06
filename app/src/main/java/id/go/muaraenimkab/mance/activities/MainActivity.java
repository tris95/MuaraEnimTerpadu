package id.go.muaraenimkab.mance.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.gun0912.tedpermission.TedPermissionResult;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.fragments.DetailBeritaFragment;
import id.go.muaraenimkab.mance.fragments.EventFragment;
import id.go.muaraenimkab.mance.fragments.HomeFragment;
import id.go.muaraenimkab.mance.fragments.KontakFragment;
import id.go.muaraenimkab.mance.fragments.LaporanFragment;
import id.go.muaraenimkab.mance.fragments.ProfilFragment;
import id.go.muaraenimkab.mance.models.Ad;
import id.go.muaraenimkab.mance.models.Berita;
import id.go.muaraenimkab.mance.models.Content;
import id.go.muaraenimkab.mance.models.Event;
import id.go.muaraenimkab.mance.models.KategoriBerita;
import id.go.muaraenimkab.mance.models.Kontak;
import id.go.muaraenimkab.mance.models.Laporan;
import id.go.muaraenimkab.mance.models.LaporanSpik;
import id.go.muaraenimkab.mance.models.Opd;
import id.go.muaraenimkab.mance.models.Pariwisata;
import id.go.muaraenimkab.mance.models.TempatPariwisata;
import id.go.muaraenimkab.mance.models.ValueAdd;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    Fragment fragment;
    public static int flag2, flag3;
    String idp;
    static Fragment lastFragment, lastFragment2, lastFragment3;
    static FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    BottomNavigationViewEx bottomNavigationView;
    static int currentFragment = 0, nowFragment = 0;
    //Home
    public static List<Ad> ads = new ArrayList<>();
    public static List<Content> contents = new ArrayList<>();
    public static List<Berita> Beritas = new ArrayList<>();
    public static List<KategoriBerita> kategoriBeritas = new ArrayList<>();
    public static List<Berita> Beritask = new ArrayList<>();
    public static List<Pariwisata> pariwisatas = new ArrayList<>();
    public static List<TempatPariwisata> tempatPariwisatas = new ArrayList<>();

    //Kontak
    public static List<Kontak> kontaks = new ArrayList<>();

    //Laporan
    //public static List<Laporan> laporans = new ArrayList<>();
    public static List<LaporanSpik> laporans = new ArrayList<>();
    public static List<Opd> opds = new ArrayList<>();

    //Event
    public static List<Event> events = new ArrayList<>();

    //Selected Kategori
    public static String selectedKategori = "";


//    private BroadcastReceiver broadcastRefresh = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            nowFragment = 0;
//            currentFragment = 0;
//            bottomNavigationView.setCurrentItem(currentFragment);
//        }
//    };

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{
//                    Manifest.permission.READ_PHONE_STATE}, 1);
//        }

        TedRx2Permission.with(Objects.requireNonNull(MainActivity.this))
                .setRationaleTitle("Izin Akses")
                .setRationaleMessage("Untuk mengakses aplikasi harap izinkan telepon")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .request()
                .subscribe(new Observer<TedPermissionResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TedPermissionResult tedPermissionResult) {
                        if (tedPermissionResult.isGranted()) {
                            cekIDP();
                        } else {
                            Snackbar.make(Objects.requireNonNull(MainActivity.this).findViewById(android.R.id.content), "Harap mengaktifkan izin Telepon",
                                    Snackbar.LENGTH_INDEFINITE)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                            //Snackbar.make(getWindow().getDecorView().getRootView(),
//                                    "Harap mengaktifkan izin Telepon",
//                                    Snackbar.LENGTH_INDEFINITE)
//                                    .setAction("OK", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Intent intent = new Intent();
//                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        if (getIntent().getStringExtra("flag") != null) {
            switch (getIntent().getStringExtra("flag")) {
                case "5":
                    fragment = DetailBeritaFragment.newInstance(getIntent().getStringExtra("id"), "5");
                    currentFragment = 0;
                    nowFragment = 0;
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case "2":
                    fragment = EventFragment.newInstance(getIntent().getStringExtra("id"), "2");
                    currentFragment = 0;
                    nowFragment = 0;
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    break;
                case "3":
                    fragment = LaporanFragment.newInstance(getIntent().getStringExtra("id"), "3");
                    currentFragment = 0;
                    nowFragment = 0;
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                    break;
            }
        } else {
            fragment = HomeFragment.newInstance();
            currentFragment = 0;
            nowFragment = 0;
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }

        bottomNavigationView.enableAnimation(true);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setIconSize(27, 27);
        bottomNavigationView.setTextSize(11);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentFragment = 0;
                        nowFragment = 0;
                        fragment = HomeFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_kontak:
                        currentFragment = 1;
                        nowFragment = 1;
                        fragment = KontakFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_event:
                        currentFragment = 2;
                        nowFragment = 2;
                        fragment = EventFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_laporan:
                        currentFragment = 3;
                        nowFragment = 3;
                        fragment = LaporanFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_profil:
                        currentFragment = 4;
                        nowFragment = 4;
                        fragment = ProfilFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    public static void replaceFragment(Fragment fragment, int flag) {
        switch (flag) {
            case 8:
                nowFragment = flag;
                break;
            case 7:
                nowFragment = flag;
                flag3 = flag;
                lastFragment3 = fragment;
                break;
            case 6:
                nowFragment = flag;
                flag2 = flag;
                lastFragment2 = fragment;
                break;
            default:
                nowFragment = flag;
                lastFragment = fragment;
                break;
        }
//        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.enter_from_right);
//        fragmentTransaction.replace(R.id.container, fragment).commit();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Back() {
        if (nowFragment != currentFragment) {
            switch (nowFragment) {
                case 8:
                    fragmentManager.beginTransaction().replace(R.id.container, lastFragment3).commit();
                    nowFragment = 7;
                    break;
                case 7:
//                fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
//                fragmentTransaction.replace(R.id.container, lastFragment2).commit();
                    fragmentManager.beginTransaction().replace(R.id.container, lastFragment2).commit();
                    nowFragment = 6;
                    break;
                case 6:
//                fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
//                fragmentTransaction.replace(R.id.container, lastFragment).commit();
                    fragmentManager.beginTransaction().replace(R.id.container, lastFragment).commit();
                    nowFragment = 5;
                    flag2 = 4;
                    break;
                default:
//                fragmentTransaction=fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
//                fragmentTransaction.replace(R.id.container, fragment).commit();
                    bottomNavigationView.setCurrentItem(currentFragment);
                    nowFragment = currentFragment;
                    break;
            }
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Snackbar.make(Objects.requireNonNull(MainActivity.this).findViewById(android.R.id.content), "Tekan sekali lagi untuk keluar", Snackbar.LENGTH_SHORT).show();
            //Toast.makeText(this, "Tekan Lagi untuk Keluar", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed() {
        Back();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onSupportNavigateUp() {
        Back();
        return true;
    }
//    @TargetApi(Build.VERSION_CODES.M)
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(MainActivity.this),
//                            Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//                    } else {
//                        cekIDP();
//                    }
//                } else {
//                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
//                }
//            }
//        }
//    }

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void cekIDP() {
//        TelephonyManager telephonyManager = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(MainActivity.this), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //ime = Objects.requireNonNull(telephonyManager).getDeviceId();
        idp = Settings.Secure.getString(Objects.requireNonNull(MainActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);

        if (!idp.equals("")) {
            String random = Utilities.getRandom(5);

            OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Utilities.getBaseURLUser())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            APIServices api = retrofit.create(APIServices.class);
            Call<ValueAdd> call = api.setperangkat(random, idp, Utilities.getToken());
            call.enqueue(new Callback<ValueAdd>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {

                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                    System.out.println("Retrofit Error:" + t.getMessage());
                }
            });
        }
    }
}
