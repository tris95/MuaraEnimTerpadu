package id.go.muaraenimkab.mance.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.fragments.ProfilFragment;
import id.go.muaraenimkab.mance.models.Berita;
import id.go.muaraenimkab.mance.models.User;
import id.go.muaraenimkab.mance.models.Value;
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

public class SignInActivity extends AppCompatActivity {
    EditText etEmail, etPass;
    Button button;
    TextView tvDaftar, lupakatasandi;
    RelativeLayout lysignin;
    String idp;
    public static boolean flagsignin;

    public static String BROADCAST_ACTION = "id.go.muaraenimkab.bappeda.activities";

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
                .setRationaleMessage("Untuk mengakses aplikasi harap izinkan telepon")
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
                            Snackbar.make(Objects.requireNonNull(SignInActivity.this).findViewById(android.R.id.content), "Harap mengaktifkan izin Telepon",
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
//                            Snackbar.make(getWindow().getDecorView().getRootView(),
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
                    Utilities.hideKeyboard(SignInActivity.this);
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Silakan isi email dan password Anda",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    ceklogin();
                }
            }
        });
        lysignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
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
        Call<Value<User>> call = api.signin(random, etEmail.getText().toString().trim(), etPass.getText().toString().trim(), Utilities.getToken(), idp);
        call.enqueue(new Callback<Value<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                Utilities.hideKeyboard(SignInActivity.this);
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        flagsignin = true;
                        for (User user : Objects.requireNonNull(response.body()).getData()) {
                            Utilities.setUser(SignInActivity.this, user);
                        }
                        Utilities.setLogin(SignInActivity.this, etEmail.getText().toString().trim(), idp);
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Login berhasil",
                                2000).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 2000);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email tidak terdaftar",
                                Snackbar.LENGTH_LONG).show();
                    } else if (success == 3) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Password yang Anda masukkan salah",
                                Snackbar.LENGTH_LONG).show();
                    } else if (success == 4) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Akun Anda telah diblokir",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal masuk aplikasi. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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

    private void ceklogin() {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<ValueAdd> call = api.ceklogin(random, etEmail.getText().toString().trim(), etPass.getText().toString().trim());
        call.enqueue(new Callback<ValueAdd>()

        {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse
                    (@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(SignInActivity.this));
                        builder.setCancelable(true)
                                .setTitle("Pemberitahuan")
                                .setCancelable(false)
                                .setMessage("Perangkat yang login sebelum ini akan logout")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        signin();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    } else if (success == 2) {
                        signin();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
}
