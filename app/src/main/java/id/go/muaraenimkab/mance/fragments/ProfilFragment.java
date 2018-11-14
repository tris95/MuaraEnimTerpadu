package id.go.muaraenimkab.mance.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.gun0912.tedpermission.TedPermissionResult;
import com.squareup.picasso.Picasso;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.SignInActivity;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ProfilFragment extends Fragment {
    Toolbar toolbar;
    Button btnAction;
    CircularImageView imgProfil;
    LinearLayout imgEdit, profilmuaraenim;
    EditText etNama, etNoKtp, etEmail, etNoHp, etAlamat, etPassword;
    public static boolean flagback;
    boolean editmode;
    String foto, idp;
    private static final int CAMERA_REQUEST = 188, FILE_REQUES = 189;
    Bitmap imageBitmap;
    ProgressDialog pDialog;
    public static int APP_REQUEST_CODE = 99;

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

    @SuppressLint({"ClickableViewAccessibility", "HardwareIds"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_profil, container, false);
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
        profilmuaraenim = v.findViewById(R.id.profilmuaraenim);

        // imgProfil.setEnabled(false);
        etNama.setEnabled(false);
        etNoKtp.setEnabled(false);
        etNoHp.setEnabled(false);
        etEmail.setEnabled(false);
        etAlamat.setEnabled(false);
//        etPassword.setEnabled(false);

        flagback = false;
        editmode = false;
        foto = "";

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Profile");
        }

//        TedRx2Permission.with(Objects.requireNonNull(getContext()))
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
//                            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_PHONE_STATE) !=
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
//                            idp = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
//                        } else {
//                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Harap mengaktifkan izin Telepon",
//                                    Snackbar.LENGTH_INDEFINITE)
//                                    .setAction("OK", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Intent intent = new Intent();
//                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .show();
////                            Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),
////                                    "Harap mengaktifkan izin Telepon",
////                                    Snackbar.LENGTH_INDEFINITE)
////                                    .setAction("OK", new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View v) {
////                                            Intent intent = new Intent();
////                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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
            idp = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utilities.setLogin(getActivity(), Utilities.getUser(getActivity()).getEmail(), idp);
        if (!Utilities.isLogin(getContext())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            builder.setCancelable(true)
                    .setTitle("Pemberitahuan")
                    .setCancelable(false)
                    .setMessage("Untuk akses menu profil silakan login terlebih dahulu")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), SignInActivity.class));
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    })
                    .show();
        }
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (btnAction.getText().toString()) {
                    case "Masuk":
                        startActivity(new Intent(getContext(), SignInActivity.class));
                        break;
                    case "Keluar": {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder.setCancelable(true)
                                .setTitle("Konfirmasi")
                                .setMessage("Keluar dari akun?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        signout();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .show();
                        break;
                    }
                    case "Perbarui": {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                        builder.setCancelable(true)
                                .setTitle("Konfirmasi")
                                .setMessage("Perbarui data akun?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (etNama.getText().toString().isEmpty()) {
                                            etNama.setError("Silakan isi nama Anda");
                                        } else if (etNoKtp.getText().toString().isEmpty()) {
                                            etNoKtp.setError("Silakan isi nomor KTP Anda");
                                        } else if (etNoHp.getText().toString().isEmpty()) {
                                            etNoHp.setError("Silakan isi nomor HP Anda");
                                        } else if (etAlamat.getText().toString().isEmpty()) {
                                            etAlamat.setError("Silakan isi alamat Anda");
                                        } else {
                                            updateProfilData();
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
                        break;
                    }
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!editmode) {
                    if (Utilities.isLogin(getContext())) {
                        //imgProfil.setVisibility(View.GONE);
                        etNama.setEnabled(true);
//                        etNoKtp.setEnabled(true);
                        etNoHp.setEnabled(true);
//                        etEmail.setEnabled(true);
                        etAlamat.setEnabled(true);
                        editmode = true;
                        btnAction.setText("Perbarui");
                    } else {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                        getActivity().finish();
                    }
                } else {
                    //imgProfil.setEnabled(false);
                    etNama.setEnabled(false);
//                    etNoKtp.setEnabled(false);
                    etNoHp.setEnabled(false);
                    etEmail.setEnabled(false);
                    etAlamat.setEnabled(false);
//                    etPassword.setEnabled(false);
                    btnAction.setText("Keluar");
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

        profilmuaraenim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//                assert (getActivity()) != null;
//                dialog.setCancelable(true);
//                LayoutInflater inflater = getActivity().getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.dialogtab_profile_muara_enim, null);
//
//                dialog.setView(dialogView);
//                dialog.create();
//                dialog.show();
                AlertDialog.Builder builder;
                AlertDialog alertDialog;

                LayoutInflater inflater = (LayoutInflater)
                        getActivity().getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = Objects.requireNonNull(inflater).inflate(R.layout.dialogtab_profile_muara_enim,
                        (ViewGroup) v.findViewById(R.id.tabhost));

                TabHost tabs = layout.findViewById(R.id.tabhost);
                tabs.setup();

                TabHost.TabSpec tabpage1 = tabs.newTabSpec("Tentang");
                tabpage1.setContent(R.id.ScrollView01);
                tabpage1.setIndicator("Tentang");
                TabHost.TabSpec tabpage2 = tabs.newTabSpec("Visi dan Misi");
                tabpage2.setContent(R.id.ScrollView02);
                tabpage2.setIndicator("Visi dan Misi");
                tabs.addTab(tabpage1);
                tabs.addTab(tabpage2);

                for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
                    TextView tv = tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(getResources().getColor(R.color.colorDefault));
                }

                builder = new AlertDialog.Builder(getContext());

                builder.setView(layout);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (Utilities.isLogin(getActivity())) {
                        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.dialog_ganti_password, null);
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
                                if (strOldPass.isEmpty()) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password lama tidak boleh kosong",
                                            Snackbar.LENGTH_LONG).show();
                                } else if (strOldPass.length() < 6) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password lama minimal 6 digit",
                                            Snackbar.LENGTH_LONG).show();
                                } else if (strNewPass.isEmpty()) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password baru tidak boleh kosong",
                                            Snackbar.LENGTH_LONG).show();
                                } else if (strNewPass.length() < 6) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password baru minimal 6 digit",
                                            Snackbar.LENGTH_LONG).show();
                                } else if (strRePass.isEmpty()) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Konfirmasi password baru tidak boleh kosong",
                                            Snackbar.LENGTH_LONG).show();
                                } else if (strRePass.length() < 6) {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Konfirmasi password baru minimal 6 digit",
                                            Snackbar.LENGTH_LONG).show();
                                } else {
                                    if (strNewPass.equals(strRePass)) {
                                        User user = Utilities.getUser(getContext());
                                        ubahPassword(user.getEmail(), strOldPass, strNewPass);
                                    } else {
                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Konfirmasi password baru tidak sesuai",
                                                Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }).setView(dialogView);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.setCancelable(false);
                        Objects.requireNonNull(alertDialog.getWindow()).setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    } else {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                        getActivity().finish();
                    }
                    return true;
                }
                return false;
            }
        });

        etNoHp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pDialog = new ProgressDialog(getActivity());
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
                    pDialog = new ProgressDialog(getActivity());
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

        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAmbilGambar();
            }
        });

        return v;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();

        if (!flagback) {

            if (Utilities.isLogin(getActivity())) {
                btnAction.setText("Keluar");
                User users = Utilities.getUser(getActivity());
                Picasso.with(getActivity())
                        .load(Utilities.getBaseURLImageUser() + users.getGambar_user())
                        .fit()
                        .centerCrop()
                        .into(imgProfil);
                etAlamat.setText(users.getAlamat_user());
                etEmail.setText(users.getEmail());
                etNoHp.setText(users.getNo_hp_user());
                etNama.setText(users.getNama_user());
                etNoKtp.setText(users.getNo_ktp());
            } else {
                btnAction.setText("Masuk");
                Picasso.with(getActivity())
                        .load(Utilities.getBaseURLImageUser() + "default.png")
                        .fit()
                        .centerCrop()
                        .into(imgProfil);
            }
        } else {
            if (SignInActivity.flagsignin) {
                btnAction.setText("Keluar");
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

    public void phoneLogin() {
        final Intent intent = new Intent(getActivity(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void dialogAmbilGambar() {
        final CharSequence[] options = {"Kamera", "Galeri"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Ambil foto dari?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Kamera")) {
//                    TedRx2Permission.with(Objects.requireNonNull(getContext()))
//                            .setRationaleTitle("Izin Akses")
//                            .setRationaleMessage("Untuk mengakses fitur kamera harap izinkan kamera dan penyimpanan")
//                            .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                            .request()
//                            .subscribe(new Observer<TedPermissionResult>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(TedPermissionResult tedPermissionResult) {
//                                    if (tedPermissionResult.isGranted()) {
//                                        if (Build.VERSION.SDK_INT >= 24) {
//                                            try {
//                                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
//                                                m.invoke(null);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//                                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                                        startActivityForResult(intent, CAMERA_REQUEST);
//                                    } else {
//                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Harap mengaktifkan izin kamera dan penyimpanan",
//                                                Snackbar.LENGTH_INDEFINITE)
//                                                .setAction("OK", new View.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(View v) {
//                                                        Intent intent = new Intent();
//                                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                                                        intent.setData(uri);
//                                                        startActivity(intent);
//                                                    }
//                                                })
//                                                .show();
////                                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),
////                                                "Harap mengaktifkan izin kamera dan penyimpanan",
////                                                Snackbar.LENGTH_INDEFINITE)
////                                                .setAction("OK", new View.OnClickListener() {
////                                                    @Override
////                                                    public void onClick(View v) {
////                                                        Intent intent = new Intent();
////                                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
////                                                        intent.setData(uri);
////                                                        startActivity(intent);
////                                                    }
////                                                })
////                                                .show();
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });

                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                1);
                    }else {
                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                } else if (options[item].equals("Galeri")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, FILE_REQUES);
                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                Log.e("permisi", "case 1");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permisi", "yes kam");
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        Log.e("permisi", "no");

                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                2);
                    }else {
                        Log.e("permisi", "yes");

                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                }else {
                    Log.e("permisi", "no kam");
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            1);
                }
            }
            case 2: {
                Log.e("permisi", "case 2");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT>=24){
                        try{
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                return;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    imageBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    foto = Utilities.getArrayByteFromBitmap(imageBitmap);
                    User user = Utilities.getUser(getActivity());
                    updateImageProfile(user.getId_user(), user.getGambar_user());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == FILE_REQUES) {
                Uri imageUri = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(Objects.requireNonNull(imageUri));
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    OutputStream outFile;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    foto = Utilities.getArrayByteFromBitmap(imageBitmap);
                    User user = Utilities.getUser(getActivity());
                    updateImageProfile(user.getId_user(), user.getGambar_user());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (imageStream != null) {
                        try {
                            imageStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (requestCode == APP_REQUEST_CODE) {
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
                Snackbar.make(Objects.requireNonNull(getActivity().findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal memverifikasi nomor telepon",
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
                        Snackbar.make(Objects.requireNonNull(getActivity().findViewById(android.R.id.content)).findViewById(android.R.id.content), "Gagal memverifikasi nomor telepon",
                                Snackbar.LENGTH_LONG).show();
                    }
                });
//                toastMessage = "Login Success";
//                Snackbar.make(Objects.requireNonNull(findViewById(android.R.id.content)).findViewById(android.R.id.content), toastMessage,
//                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void updateImageProfile(final String id, final String oldFoto) {
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
        Call<Value<User>> call = api.updateprofilimage(random, id, oldFoto, foto);
        call.enqueue(new Callback<Value<User>>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                Log.e("boo", Objects.requireNonNull(response.body()).getMessage());
                Log.e("boo", id + " | " + oldFoto + " | " + foto);
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        foto = "";
                        imgProfil.setImageBitmap(imageBitmap);
//                        imgProfil.setEnabled(false);
                        etNama.setEnabled(false);
                        etNoKtp.setEnabled(false);
                        etNoHp.setEnabled(false);
                        etEmail.setEnabled(false);
                        etAlamat.setEnabled(false);
//                        etPassword.setEnabled(false);
                        btnAction.setText("Keluar");
                        for (User user : response.body().getData()) {
                            Utilities.setUser(getContext(), user);
                        }
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Foto berhasil diubah", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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


    private void updateProfilData() {
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
        Call<Value<User>> call = api.updateprofil(random, etEmail.getText().toString().trim(),
                etNama.getText().toString().trim(), etNoKtp.getText().toString().trim(), etNoHp.getText().toString().trim(),
                etAlamat.getText().toString().trim());
        call.enqueue(new Callback<Value<User>>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<User>> call, @NonNull Response<Value<User>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
//                        imgProfil.setEnabled(false);
                        etNama.setEnabled(false);
                        etNoKtp.setEnabled(false);
                        etNoHp.setEnabled(false);
                        etEmail.setEnabled(false);
                        etAlamat.setEnabled(false);
//                        etPassword.setEnabled(false);
                        btnAction.setText("Keluar");
                        for (User user : response.body().getData()) {
                            Utilities.setUser(getContext(), user);
                        }
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Akun berhasil diperbarui", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password Anda berhasil diubah", Snackbar.LENGTH_LONG).show();
                    } else if (success == 2) {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Password lama yang Anda masukkan salah", Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal menyimpan data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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

    private void signout() {
        final ProgressDialog pDialog = new ProgressDialog(getContext());
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
        Call<ValueAdd> call = api.signout(random, etEmail.getText().toString().trim());
        call.enqueue(new Callback<ValueAdd>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        btnAction.setText("Masuk");
                        Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + "default.png");
                        etAlamat.setText("");
                        etEmail.setText("");
                        etNoHp.setText("");
                        etNama.setText("");
                        etNoKtp.setText("");
                        Utilities.signOutUser(getContext());
                        Intent mIntent = new Intent(getContext(), SignInActivity.class);
                        startActivity(mIntent);
                        Objects.requireNonNull(getActivity()).finish();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
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
