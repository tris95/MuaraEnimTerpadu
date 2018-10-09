package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.PariwisataViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Pariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PariwisataFragment extends Fragment {
    Toolbar toolbar;
    private static final String ARG_idKategori_pariwisata = "idkategoripariwisata", ARG_namaKategori_pariwisata = "namakategoripariwisata",
            ARG_jumlahtempat = "jumlahtempat";
    RecyclerView rvPariwisata;
    TextView tv_cobalagi;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Pariwisata> mListPariwisata;
    String idkategoripariwisata, namakategoripariwisata,jumlahtempat;
    RelativeLayout rl, rlcontentkosong;

    public PariwisataFragment() {
        // Required empty public constructor
    }

    public static PariwisataFragment newInstance(String idkategoripariwisata, String namakategoripariwisata, String jumlahtempat) {
        PariwisataFragment fragment = new PariwisataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idKategori_pariwisata, idkategoripariwisata);
        args.putString(ARG_namaKategori_pariwisata, namakategoripariwisata);
        args.putString(ARG_jumlahtempat, jumlahtempat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idkategoripariwisata = getArguments().getString(ARG_idKategori_pariwisata);
            namakategoripariwisata = getArguments().getString(ARG_namaKategori_pariwisata);
            jumlahtempat = getArguments().getString(ARG_jumlahtempat);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pariwisata, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        rl = v.findViewById(R.id.rl);
        rlcontentkosong = v.findViewById(R.id.rlcontentkosong);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(namakategoripariwisata);
        }
        rvPariwisata = v.findViewById(R.id.rvPariwisata);

        getPariwisata();

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPariwisata();
            }
        });
        return v;
    }

    private void getPariwisata() {
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
        Call<Value<Pariwisata>> call = api.getKategoripariwisata(random, idkategoripariwisata,jumlahtempat);
        call.enqueue(new Callback<Value<Pariwisata>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Pariwisata>> call, @NonNull Response<Value<Pariwisata>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListPariwisata = (ArrayList<Pariwisata>) Objects.requireNonNull(response.body()).getData();

                        if (mListPariwisata.size() != 0) {
                            linearLayoutManager = new LinearLayoutManager(getContext());
                            rvPariwisata.setLayoutManager(linearLayoutManager);
                            PariwisataViewAdapter pariwisataViewAdapter = new PariwisataViewAdapter(getContext(), mListPariwisata,jumlahtempat);
                            rvPariwisata.setAdapter(pariwisataViewAdapter);
                            rlcontentkosong.setVisibility(View.GONE);
                            rvPariwisata.setVisibility(View.VISIBLE);
                        } else {
                            rlcontentkosong.setVisibility(View.VISIBLE);
                            rvPariwisata.setVisibility(View.GONE);
                        }
                        rl.setVisibility(View.GONE);
                        pDialog.dismiss();

                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                        rlcontentkosong.setVisibility(View.GONE);
                        rvPariwisata.setVisibility(View.GONE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                    rlcontentkosong.setVisibility(View.GONE);
                    rvPariwisata.setVisibility(View.GONE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Pariwisata>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
                rlcontentkosong.setVisibility(View.GONE);
                rvPariwisata.setVisibility(View.GONE);
            }
        });
    }
}
