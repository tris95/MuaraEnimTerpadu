package id.go.muaraenimkab.bappeda.muaraenimterpadu.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.ValueAdd;
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

public class SignUpActivity extends AppCompatActivity {
    Button btnDaftar;
    String idp;
    EditText etNama, etNoKtp, etNoHp, etEmail, etAlamat, etPassword;
    RelativeLayout lysignup;

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnDaftar = findViewById(R.id.button);
        etAlamat = findViewById(R.id.etAlamat);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etNoHp = findViewById(R.id.etNoHp);
        etNoKtp = findViewById(R.id.etNoKtp);
        etPassword = findViewById(R.id.etPass);
        lysignup= findViewById(R.id.lysignup);

        TedRx2Permission.with(Objects.requireNonNull(SignUpActivity.this))
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
                            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(SignUpActivity.this), Manifest.permission.READ_PHONE_STATE) !=
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
                            idp = Settings.Secure.getString(Objects.requireNonNull(SignUpActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
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
            idp = Settings.Secure.getString(Objects.requireNonNull(SignUpActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Silahkan isi alamat email Anda");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Silahkan isi password Anda");
                } else if (etNama.getText().toString().isEmpty()) {
                    etNama.setError("Silahkan isi nama Anda");
                } else if (etNoKtp.getText().toString().isEmpty()) {
                    etNoKtp.setError("Silahkan isi nomor KTP Anda");
                } else if (etNoHp.getText().toString().isEmpty()) {
                    etNoHp.setError("Silahkan isi nomor HP Anda");
                } else if (etAlamat.getText().toString().isEmpty()) {
                    etAlamat.setError("Silahkan isi alamat Anda");
                } else {
                    ceknik();
                }
            }
        });
        lysignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ceknik() {
        if(etNoKtp.getText().length()==16) {
            String random = Utilities.getRandom(5);

            OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Utilities.getBaseURLUser())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            APIServices api = retrofit.create(APIServices.class);
            Call<ValueAdd> call = api.ceknik(random, etNoKtp.getText().toString().trim());
            call.enqueue(new Callback<ValueAdd>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                    if (response.body() != null) {
                        int success = Objects.requireNonNull(response.body()).getSuccess();
                        if (success == 1) {
                            signup();
                        } else if (success == 2) {
                            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "NIK Sudah terdaftar",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal menyimpan data. Silahkan coba lagi",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                    System.out.println("Retrofit Error:" + t.getMessage());
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }else {
            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "NIK Tidak Sesuai",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void signup() {
        final ProgressDialog pDialog = new ProgressDialog(SignUpActivity.this);
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
        Call<ValueAdd> call = api.signup(random, etEmail.getText().toString().trim(), etPassword.getText().toString().trim(),
                etNama.getText().toString().trim().toUpperCase(), etNoKtp.getText().toString().trim(), etNoHp.getText().toString().trim(),
                etAlamat.getText().toString().trim().toUpperCase(),idp);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Akun berhasil terdaftar. Silahkan masuk aplikasi", 3500).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3500);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email sudah terdaftar. Silahkan masuk aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal menyimpan data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
