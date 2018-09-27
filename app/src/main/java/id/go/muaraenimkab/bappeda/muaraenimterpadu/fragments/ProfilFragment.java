package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignInActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignUpActivity;
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


public class ProfilFragment extends Fragment {
    Toolbar toolbar;
    Button btnAction;
    CircularImageView imgProfil;
    ImageView imgEdit;
    EditText etNama, etNoKtp, etEmail, etNoHp, etAlamat, etPassword;
    public static boolean flagback;
    boolean editmode;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v=inflater.inflate(R.layout.fragment_profil, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        btnAction = v.findViewById(R.id.btnAction);
        imgProfil = v.findViewById(R.id.imgProfil);
        etNama = v.findViewById(R.id.etNama);
        etNoKtp = v.findViewById(R.id.etNoKtp);
        etNoHp = v.findViewById(R.id.etNoHp);
        etEmail = v.findViewById(R.id.etEmail);
        etAlamat = v.findViewById(R.id.etAlamat);
        etPassword = v.findViewById(R.id.etPassword);
        imgEdit = v.findViewById(R.id.imgEdit);

        imgProfil.setEnabled(false);
        etNama.setEnabled(false);
        etNoKtp.setEnabled(false);
        etNoHp.setEnabled(false);
        etEmail.setEnabled(false);
        etAlamat.setEnabled(false);
//        etPassword.setEnabled(false);

        flagback = false;
        editmode = false;

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Profile");
        }

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAction.getText().toString().equals("SignIn")){
                    startActivity(new Intent(getContext(), SignInActivity.class));
                }else if (btnAction.getText().toString().equals("SignOut")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true)
                            .setTitle("Konfirmasi")
                            .setMessage("Keluar dari akun ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btnAction.setText("SignIn");
                                    Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser()+"default.png");
                                    etAlamat.setText("");
                                    etEmail.setText("");
                                    etNoHp.setText("");
                                    etNama.setText("");
                                    etNoKtp.setText("");
                                    Utilities.signOutUser(getContext());
                                    Intent mIntent = new Intent(getContext(), SignInActivity.class);
                                    startActivity(mIntent);
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }else if(btnAction.getText().toString().equals("Update Akun")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true)
                            .setTitle("Konfirmasi")
                            .setMessage("Update data akun ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (etNama.getText().toString().isEmpty()){
                                        etNama.setError("Silahkan isi nama Anda");
                                    }else if (etNoKtp.getText().toString().isEmpty()){
                                        etNoKtp.setError("Silahkan isi nomor KTP Anda");
                                    }else if (etNoHp.getText().toString().isEmpty()){
                                        etNoHp.setError("Silahkan isi nomor HP Anda");
                                    }else if (etAlamat.getText().toString().isEmpty()){
                                        etAlamat.setError("Silahkan isi alamat Anda");
                                    }else {
                                        update();
                                    }
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editmode){
                    if (Utilities.isLogin(getContext())) {
                        imgProfil.setEnabled(true);
                        etNama.setEnabled(true);
                        etNoKtp.setEnabled(true);
                        etNoHp.setEnabled(true);
//                        etEmail.setEnabled(true);
                        etAlamat.setEnabled(true);
                        editmode = true;
                        btnAction.setText("Update Akun");
                    }else {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                    }
                }else {
                    imgProfil.setEnabled(false);
                    etNama.setEnabled(false);
                    etNoKtp.setEnabled(false);
                    etNoHp.setEnabled(false);
                    etEmail.setEnabled(false);
                    etAlamat.setEnabled(false);
//                    etPassword.setEnabled(false);
                    btnAction.setText("SignOut");
                    User users = Utilities.getUser(getActivity());
                    Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + users.getGambar_user()).into(imgProfil);
                    etAlamat.setText(users.getAlamat_user());
                    etEmail.setText(users.getEmail());
                    etNoHp.setText(users.getNo_hp_user());
                    etNama.setText(users.getNama_user());
                    etNoKtp.setText(users.getNo_ktp());
                    editmode = false;
                }
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(Utilities.isLogin(getActivity())) {
                        View dialogView = inflater.inflate(R.layout.dialog_ganti_password, null);
                        final EditText oldPas = dialogView.findViewById(R.id.et_old_password);
                        final EditText newPas = dialogView.findViewById(R.id.et_new_password);
                        final EditText rePass = dialogView.findViewById(R.id.et_repassword);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).
                                setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strOldPass = oldPas.getText().toString();
                                String strNewPass = newPas.getText().toString();
                                String strRePass = rePass.getText().toString();
                                if (strNewPass.equals(strRePass)) {
                                    User user = Utilities.getUser(getContext());
                                    ubahPassword(user.getEmail(), strOldPass, strNewPass);
                                } else {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password baru tidak sesuai",
                                            Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }).setView(dialogView);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setCancelable(false);
                        alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }else {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                    }
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!flagback) {
            if (Utilities.isLogin(getActivity())) {
                btnAction.setText("SignOut");
                User users = Utilities.getUser(getActivity());
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + users.getGambar_user()).into(imgProfil);
                etAlamat.setText(users.getAlamat_user());
                etEmail.setText(users.getEmail());
                etNoHp.setText(users.getNo_hp_user());
                etNama.setText(users.getNama_user());
                etNoKtp.setText(users.getNo_ktp());
            } else {
                btnAction.setText("SignIn");
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + "default.png").into(imgProfil);
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        }else {
            if (SignInActivity.flagsignin){
                btnAction.setText("SignOut");
                User users = Utilities.getUser(getActivity());
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + users.getGambar_user()).into(imgProfil);
                etAlamat.setText(users.getAlamat_user());
                etEmail.setText(users.getEmail());
                etNoHp.setText(users.getNo_hp_user());
                etNama.setText(users.getNama_user());
                etNoKtp.setText(users.getNo_ktp());
            }
        }
    }

    //    public void onButtonPressed(Uri uri) {
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }

    private void update() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
        Call<Value<User>> call = api.update(random, etEmail.getText().toString().trim(),
                etNama.getText().toString().trim(), etNoKtp.getText().toString().trim(), etNoHp.getText().toString().trim(),
                etAlamat.getText().toString().trim());
        call.enqueue(new Callback<Value<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        imgProfil.setEnabled(false);
                        etNama.setEnabled(false);
                        etNoKtp.setEnabled(false);
                        etNoHp.setEnabled(false);
                        etEmail.setEnabled(false);
                        etAlamat.setEnabled(false);
//                        etPassword.setEnabled(false);
                        btnAction.setText("SignOut");
                        for (User user : response.body().getData()) {
                            Utilities.setUser(getContext(), user);
                        }
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Akun berhasil di update", Snackbar.LENGTH_LONG).show();
                    } else{
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<User>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void ubahPassword(String email, String oldPass, String newPass) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
        Call<ValueAdd> call = api.ubahpass(random, email, oldPass, newPass);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password berhasil di ubah", Snackbar.LENGTH_LONG).show();
                    }else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password lama tidak sesuai", Snackbar.LENGTH_LONG).show();
                    } else{
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
