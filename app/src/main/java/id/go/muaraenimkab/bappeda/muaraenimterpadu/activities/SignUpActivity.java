package id.go.muaraenimkab.bappeda.muaraenimterpadu.activities;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.ValueAdd;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    Button btnDaftar;
    EditText etNama, etNoKtp, etNoHp, etEmail, etAlamat, etPassword;

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

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Silahkan isi alamat email Anda");
                }else if (etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Silahkan isi password Anda");
                }else if (etNama.getText().toString().isEmpty()){
                    etNama.setError("Silahkan isi nama Anda");
                }else if (etNoKtp.getText().toString().isEmpty()){
                    etNoKtp.setError("Silahkan isi nomor KTP Anda");
                }else if (etNoHp.getText().toString().isEmpty()){
                    etNoHp.setError("Silahkan isi nomor HP Anda");
                }else if (etAlamat.getText().toString().isEmpty()){
                    etAlamat.setError("Silahkan isi alamat Anda");
                }else {
                    signup();
                }
            }
        });
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
                etNama.getText().toString().trim(), etNoKtp.getText().toString().trim(), etNoHp.getText().toString().trim(),
                etAlamat.getText().toString().trim());
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
                    } else  if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), "Alamat email sudah terdaftar. Silahkan masuk aplikasi",
                                Snackbar.LENGTH_LONG).show();
                    } else{
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
