package id.go.muaraenimkab.mance.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.models.DetailPariwisata;
import id.go.muaraenimkab.mance.models.TempatPariwisata;
import id.go.muaraenimkab.mance.models.Value;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailKulinerFragment extends Fragment {
    Toolbar toolbar;
    Button btnToko;
    ArrayList<DetailPariwisata> mListPariwisata;
    String idpariwisata, namapariwisata, deskripsi, gambar, toko;
    RelativeLayout rl, rLayout;
    TextView lbljudulpariwisata;
    DocumentView lbldeskripsipariwisata;
    ImageView imgDetaiKuliner;
    private static final String ARG_idpariwisata = "idpariwisata", ARG_namapariwisata = "namapariwisata",
            ARG_deskripsi = "deskripsi", ARG_gambar = "gambar", ARG_toko = "toko";

    public DetailKulinerFragment() {
        // Required empty public constructor
    }

    public static DetailKulinerFragment newInstance(String idpariwisata, String namapariwisata, String deskripsi, String gambar, String toko) {
        DetailKulinerFragment fragment = new DetailKulinerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idpariwisata, idpariwisata);
        args.putString(ARG_namapariwisata, namapariwisata);
        args.putString(ARG_deskripsi, deskripsi);
        args.putString(ARG_gambar, gambar);
        args.putString(ARG_toko, toko);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idpariwisata = getArguments().getString(ARG_idpariwisata);
            namapariwisata = getArguments().getString(ARG_namapariwisata);
            deskripsi = getArguments().getString(ARG_deskripsi);
            gambar = getArguments().getString(ARG_gambar);
            toko = getArguments().getString(ARG_toko);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_kuliner, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        btnToko = v.findViewById(R.id.btnToko);
        rl = v.findViewById(R.id.rl);
        imgDetaiKuliner = v.findViewById(R.id.imgDetaiKuliner);
        lbljudulpariwisata = v.findViewById(R.id.lbljudulpariwisata);
        lbldeskripsipariwisata = v.findViewById(R.id.lbldeskripsipariwisata);
        rLayout = v.findViewById(R.id.rLayout);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Detail Kuliner");
        }

        if (toko.equals("1"))
            btnToko.setVisibility(View.VISIBLE);
        else
            btnToko.setVisibility(View.GONE);

        lbljudulpariwisata.setText(namapariwisata);
        Picasso.with(getContext())
                .load(gambar)
                .into(imgDetaiKuliner);
        lbldeskripsipariwisata.setText(deskripsi);

        btnToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(TempatFragment.newInstance(idpariwisata), 7);
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPariwisata();
            }
        });

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int more = viewPagerWidth / 4;
        int viewPagerHeight = (Math.round(outMetrics.widthPixels) / 2) + more;

        rLayout.setLayoutParams(new LinearLayout.LayoutParams(viewPagerWidth, viewPagerHeight));

        return v;
    }


    public void getPariwisata() {
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
        Call<Value<DetailPariwisata>> call = api.getpariwisata(random, idpariwisata);
        call.enqueue(new Callback<Value<DetailPariwisata>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<DetailPariwisata>> call, @NonNull Response<Value<DetailPariwisata>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListPariwisata = (ArrayList<DetailPariwisata>) Objects.requireNonNull(response.body()).getData();

                        lbljudulpariwisata.setText(namapariwisata);
                        Picasso.with(getContext())
                                .load(gambar)
                                .into(imgDetaiKuliner);
                        lbldeskripsipariwisata.setText(mListPariwisata.get(0).getDeskripsi_pariwisata());
                        rl.setVisibility(View.GONE);
                        pDialog.dismiss();

                    } else {
                        rl.setVisibility(View.VISIBLE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<DetailPariwisata>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                rl.setVisibility(View.VISIBLE);
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }
}
