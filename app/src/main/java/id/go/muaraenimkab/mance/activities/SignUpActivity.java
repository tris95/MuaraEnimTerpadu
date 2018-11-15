package id.go.muaraenimkab.mance.activities;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.mance.R;
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

public class SignUpActivity extends AppCompatActivity {
    Button btnDaftar;
    String idp;
    EditText etNama, etNoKtp, etNoHp, etEmail, etAlamat, etPassword;
    RelativeLayout lysignup;
    public static int APP_REQUEST_CODE = 99;
    ProgressDialog pDialog;
//    LinearLayout ll_phone;

    @SuppressLint({"HardwareIds", "ClickableViewAccessibility"})
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
        lysignup = findViewById(R.id.lysignup);
//        ll_phone = findViewById(R.id.ll_phone);

//        TedRx2Permission.with(Objects.requireNonNull(SignUpActivity.this))
//                .setRationaleTitle("Izin Akses")
//                .setRationaleMessage("Untuk mengakses aplikasi harap izinkan telepon")
//                .setPermissions(Manifest.permission.READ_PHONE_STATE)
//                .request()
//                .subscribe(new Observer<TedPermissionResult>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @SuppressLint("HardwareIds")
//                    @Override
//                    public void onNext(TedPermissionResult tedPermissionResult) {
//                        if (tedPermissionResult.isGranted()) {
//                            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(SignUpActivity.this), Manifest.permission.READ_PHONE_STATE) !=
//                                    PackageManager.PERMISSION_GRANTED) {
//                                // TODO: Consider calling
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
//                            //ime = Objects.requireNonNull(telephonyManager).getDeviceId();
//                            idp = Settings.Secure.getString(Objects.requireNonNull(SignUpActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
//                        } else {
//                            Snackbar.make(Objects.requireNonNull(SignUpActivity.this).findViewById(android.R.id.content), "Harap mengaktifkan izin Telepon",
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
////                            Snackbar.make(getWindow().getDecorView().getRootView(),
////                                    "Harap mengaktifkan izin Telepon",
////                                    Snackbar.LENGTH_INDEFINITE)
////                                    .setAction("OK", new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            Intent intent = new Intent();
////                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
////                                            intent.setData(uri);
////                                            startActivity(intent);
////                                        }
////                                    })
////                                    .show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        try {
            idp = Settings.Secure.getString(Objects.requireNonNull(SignUpActivity.this).getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Silakan isi alamat email Anda");
                    requestFocus(etEmail);
                } else if (!isValidEmail(etEmail.getText().toString())) {
                    etEmail.setError("Alamat email tidak valid");
                    requestFocus(etEmail);
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Silakan isi password Anda");
                    requestFocus(etPassword);
                } else if (etPassword.getText().length() < 6) {
                    etPassword.setError("Panjang password minimal 6 digit");
                    requestFocus(etPassword);
                } else if (etNama.getText().toString().isEmpty()) {
                    etNama.setError("Silakan isi nama Anda");
                    requestFocus(etNama);
                } else if (etNoKtp.getText().toString().isEmpty()) {
                    etNoKtp.setError("Silakan isi nomor KTP Anda");
                    requestFocus(etNoKtp);
                } else if (etNoHp.getText().toString().isEmpty()) {
                    etNoHp.setError("Silakan isi nomor Hp Anda");
                } else if (etAlamat.getText().toString().isEmpty()) {
                    etAlamat.setError("Silakan isi alamat Anda");
                    requestFocus(etAlamat);
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

        etNoHp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pDialog = new ProgressDialog(SignUpActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    phoneLogin();
                }
            }
        });

        etNoHp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    pDialog = new ProgressDialog(SignUpActivity.this);
                    pDialog.setMessage("Loading...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    phoneLogin();
                    return true;
                }
                return false;
            }
        });

//        ll_phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pDialog = new ProgressDialog(SignUpActivity.this);
//                pDialog.setMessage("Loading...");
//                pDialog.setIndeterminate(false);
//                pDialog.setCancelable(false);
//                pDialog.show();
//                phoneLogin();
//            }
//        });

    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.etEmail:
                    validateEmail();
                    break;
            }
        }
    }

    private void validateEmail() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            etEmail.setError("Alamat email tidak valid");
            requestFocus(etEmail);
        } else {
            etEmail.setError(null);
        }

    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void phoneLogin() {
        final Intent intent = new Intent(SignUpActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            pDialog.dismiss();
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
//            String toastMessage = "";
            if (loginResult.getError() != null) {
//                toastMessage = loginResult.getError().getErrorType().getMessage();
//                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), toastMessage,
//                        Snackbar.LENGTH_LONG).show();
//                showErrorActivity(loginResult.getError());
//            } else if (loginResult.wasCancelled()) {
//                toastMessage = "Login Cancelled";
//                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), toastMessage,
//                        Snackbar.LENGTH_LONG).show();
//                finish();
                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal verifikasi nomor Hp",
                        Snackbar.LENGTH_LONG).show();
            } else {
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        String phoneNumberString = "";
                        if (phoneNumber != null) {
                            phoneNumberString = phoneNumber.toString();
                        }
//                         Log.e("num", phoneNumberString);
                        etNoHp.setText(phoneNumberString);
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
//                         Log.e("error", accountKitError.toString());
//                         Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), accountKitError.toString(),
//                                Snackbar.LENGTH_LONG).show();
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal verifikasi nomor Hp",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
//                toastMessage = "Login Success";
//                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), toastMessage,
//                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ceknik() {
        if (etNoKtp.getText().length() == 16) {
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
                            signupspik();
                        } else if (success == 2) {
                            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "NIK sudah terdaftar",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
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
        } else {
            Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "NIK tidak Sesuai",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void signup(String idpengguna) {
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
                etAlamat.getText().toString().trim(), idp, idpengguna);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Akun berhasil terdaftar. Silakan login aplikasi", 3500).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3500);
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email sudah terdaftar. Silakan masuk aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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

    private void signupspik() {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUserspik())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<ValueAdd> call = api.signupspik(random, etEmail.getText().toString().trim(), etPassword.getText().toString().trim(),
                etNama.getText().toString().trim().toUpperCase(), etNoHp.getText().toString().trim());
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        signup(Objects.requireNonNull(response.body()).getMessage());
                    } else {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email sudah terdaftar",
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
    }
}
