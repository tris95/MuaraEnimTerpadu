package id.go.muaraenimkab.bappeda.muaraenimterpadu.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.BeritaViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.ProfilFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button button;
    TextView tvDaftar, lupakatasandi;
    RelativeLayout lysignin;
    String idp;
    public static boolean flagsignin;

    public static String BROADCAST_ACTION = "id.go.muaraenimkab.bappeda.muaraenimterpadu.activities";

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        button = findViewById(R.id.button);
        tvDaftar = findViewById(R.id.textView4);
//        lupakatasandi= findViewById(R.id.lupakatasandi);
        lysignin = findViewById(R.id.lysignin);

        ProfilFragment.flagback = true;
        flagsignin = false;

        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        TedRx2Permission.with(Objects.requireNonNull(SignInActivity.this))
                .setRationaleTitle("Izin Akses")
                .setRationaleMessage("Untuk mengakses Aplikasi harap izinkan Telepon")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .request()
                .subscribe(new Observer<TedPermissionResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("HardwareIds")
                    @Override
                    public void onNext(TedPermissionResult tedPermissionResult) {
                        if (tedPermissionResult.isGranted()) {
                            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(SignInActivity.this), Manifest.permission.READ_PHONE_STATE) !=
                                    PackageManager.PERMISSION_GRANTED) {
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
                            idp = Settings.Secure.getString(Objects.requireNonNull(SignInActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
                        } else {
                            Snackbar.make(getWindow().getDecorView().getRootView(),
                                    "Harap mengaktifkan izin Telepon",
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
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        try {
            idp = Settings.Secure.getString(Objects.requireNonNull(SignInActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty() || etPass.getText().toString().isEmpty()) {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Silahkan isi email dan password Anda",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    signin();
                }
            }
        });
        lysignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.hideKeyboard(SignInActivity.this);
            }
        });
//        lupakatasandi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    private void signin() {
        final ProgressDialog pDialog = new ProgressDialog(SignInActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<User>> call = api.signin(random, etEmail.getText().toString().trim(), etPass.getText().toString().trim(), Utilities.getToken(),idp);
        call.enqueue(new Callback<Value<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        flagsignin = true;
                        for (User user : Objects.requireNonNull(response.body()).getData()) {
                            Utilities.setUser(SignInActivity.this, user);
                        }
                        Utilities.setLogin(SignInActivity.this, etEmail.getText().toString().trim(), idp);
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "SignIn Success",
                                2000).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 2000);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Password tidak sesuai",
                                Snackbar.LENGTH_LONG).show();
                    } else if (success == 3) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email tidak terdaftar",
                                Snackbar.LENGTH_LONG).show();
                    } else if (success == 4) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Akun di Banned",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal masuk aplikasi. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<User>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
}
