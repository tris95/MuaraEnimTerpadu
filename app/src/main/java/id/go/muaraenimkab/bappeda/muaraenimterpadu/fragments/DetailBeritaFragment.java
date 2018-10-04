package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
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


public class DetailBeritaFragment extends Fragment {
    private static final String ARG_idberita = "idberita", ARG_judulberita = "judulberita", ARG_tanggalberita = "tanggalberita",
            ARG_likeberita = "likeberita", ARG_viewberita = "viewberita", ARG_gambar = "gambar";
    String idberita, judulberita, tanggalberita, likeberita, viewberita, gambar;
    Toolbar toolbar;
    TextView tv_cobalagi, lblLikeUnlike, lblJudulBerita, lbltanggalBerita, lblLikeBerita, lblViewBerita;
    DocumentView lblIsiBerita;
    ImageView imgDetaiBerita;
    ArrayList<Berita> mListBerita;
    RelativeLayout rl, rlket;
    String ime = "";

    public DetailBeritaFragment() {
        // Required empty public constructor
    }

    public static DetailBeritaFragment newInstance(String idberita, String judulberita, String tanggalberita,
                                                   String likeberita, String viewberita, String gambar) {
        DetailBeritaFragment fragment = new DetailBeritaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idberita, idberita);
        args.putString(ARG_judulberita, judulberita);
        args.putString(ARG_tanggalberita, tanggalberita);
        args.putString(ARG_likeberita, likeberita);
        args.putString(ARG_viewberita, viewberita);
        args.putString(ARG_gambar, gambar);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idberita = getArguments().getString(ARG_idberita);
            judulberita = getArguments().getString(ARG_judulberita);
            tanggalberita = getArguments().getString(ARG_tanggalberita);
            likeberita = getArguments().getString(ARG_likeberita);
            viewberita = getArguments().getString(ARG_viewberita);
            gambar = getArguments().getString(ARG_gambar);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_berita, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
        lblJudulBerita = v.findViewById(R.id.lblJudulBerita);
        lbltanggalBerita = v.findViewById(R.id.lbltanggalBerita);
        lblLikeBerita = v.findViewById(R.id.lblLikeBerita);
        lblViewBerita = v.findViewById(R.id.lblViewBerita);
        lblIsiBerita = v.findViewById(R.id.lblIsiBerita);
        imgDetaiBerita = v.findViewById(R.id.imgDetaiBerita);
        rl = v.findViewById(R.id.rl);
        rlket = v.findViewById(R.id.rlket);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Detail Berita");
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE}, 1);
        }
        cekIME();

        lblLikeUnlike = v.findViewById(R.id.lblLikeUnlike);
        lblLikeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lblLikeUnlike.setTextColor(Color.parseColor("#41c300"));
            }
        });

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekIME();
            }
        });

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getisiBerita(final ProgressDialog pDialog) {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Berita>> call = api.getIsiBerita(random, idberita);
        call.enqueue(new Callback<Value<Berita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Berita>> call, @NonNull Response<Value<Berita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListBerita = (ArrayList<Berita>) Objects.requireNonNull(response.body()).getData();

                        lblJudulBerita.setText(judulberita);
                        lbltanggalBerita.setText(tanggalberita);
                        lblLikeBerita.setText(likeberita);
                        lblViewBerita.setText(viewberita);

                        Picasso.with(getContext())
                                .load(gambar)
                                .into(imgDetaiBerita);

                        lblIsiBerita.setText(mListBerita.get(0).getIsi_berita());

                        rl.setVisibility(View.GONE);
                        lblLikeUnlike.setVisibility(View.VISIBLE);
                        rlket.setVisibility(View.VISIBLE);

                        pDialog.dismiss();

                    } else {
                        rl.setVisibility(View.VISIBLE);
                        lblLikeUnlike.setVisibility(View.GONE);
                        rlket.setVisibility(View.GONE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl.setVisibility(View.VISIBLE);
                    lblLikeUnlike.setVisibility(View.GONE);
                    rlket.setVisibility(View.GONE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Berita>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                rl.setVisibility(View.VISIBLE);
                lblLikeUnlike.setVisibility(View.GONE);
                rlket.setVisibility(View.GONE);
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("HardwareIds")
    private void cekIME() {
        TelephonyManager telephonyManager = (TelephonyManager) Objects.requireNonNull(getContext()).getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        ime = Objects.requireNonNull(telephonyManager).getDeviceId();

        if (!ime.equals("")) {

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
            Call<ValueAdd> call = api.cekime(random, ime, idberita);
            call.enqueue(new Callback<ValueAdd>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                    if (response.body() != null) {
                        int success = Objects.requireNonNull(response.body()).getSuccess();
                        if (success == 1) {
                            String data = Objects.requireNonNull(response.body()).getMessage();
                            if (!data.equals("ada")) {
                                setDataView();
                            }
                            getisiBerita(pDialog);
                        } else {
                            rl.setVisibility(View.VISIBLE);
                            lblLikeUnlike.setVisibility(View.GONE);
                            rlket.setVisibility(View.GONE);
                            pDialog.dismiss();
                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                    Snackbar.LENGTH_LONG).show();
                            rl.setVisibility(View.VISIBLE);
                        }
                    } else {
                        rl.setVisibility(View.VISIBLE);
                        lblLikeUnlike.setVisibility(View.GONE);
                        rlket.setVisibility(View.GONE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                    System.out.println("Retrofit Error:" + t.getMessage());
                    rl.setVisibility(View.VISIBLE);
                    lblLikeUnlike.setVisibility(View.GONE);
                    rlket.setVisibility(View.GONE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setDataView() {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<ValueAdd> call = api.setDataView(random, ime, idberita);
        call.enqueue(new Callback<ValueAdd>() {
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {

            }

            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                            Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                            1);
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("permission", "DENIED");
                        return;
                    } else {
                        Log.e("permission", "GRANTED");
                    }
                    cekIME();
                }
            }
        }
    }
}

