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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.TempatViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.TempatPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TempatFragment extends Fragment {
    Toolbar toolbar;
    private static final String ARG_idpariwisata = "idpariwisata";
    String idpariwisata;
    RelativeLayout rl;
    RecyclerView rvTempat;
    LinearLayoutManager linearLayoutManager;
    ArrayList<TempatPariwisata> mListtempatPariwisata;

    public TempatFragment() {
        // Required empty public constructor
    }

    public static TempatFragment newInstance(String idpariwisata) {
        TempatFragment fragment = new TempatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idpariwisata, idpariwisata);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idpariwisata = getArguments().getString(ARG_idpariwisata);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_tempat, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        rvTempat = v.findViewById(R.id.rvtempat);
        rl = v.findViewById(R.id.rl);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Daftar Toko");
        }
        if(MainActivity.tempatPariwisatas.size() != 0){
            linearLayoutManager = new LinearLayoutManager(getContext());
            rvTempat.setLayoutManager(linearLayoutManager);
            TempatViewAdapter tempatViewAdapter = new TempatViewAdapter(getContext(), (ArrayList<TempatPariwisata>)MainActivity.tempatPariwisatas);
            rvTempat.setAdapter(tempatViewAdapter);
            rl.setVisibility(View.GONE);
            rvTempat.setVisibility(View.VISIBLE);
        }
        else
            gettempatPariwisata();

        return v;
    }

    public void gettempatPariwisata() {
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
        Call<Value<TempatPariwisata>> call = api.gettempatpariwisata(random,idpariwisata);
        call.enqueue(new Callback<Value<TempatPariwisata>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<TempatPariwisata>> call, @NonNull Response<Value<TempatPariwisata>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListtempatPariwisata = (ArrayList<TempatPariwisata>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.tempatPariwisatas.clear();
                        MainActivity.tempatPariwisatas=mListtempatPariwisata;

                        if (mListtempatPariwisata.size()!=0) {
                            linearLayoutManager = new LinearLayoutManager(getContext());
                            rvTempat.setLayoutManager(linearLayoutManager);
                            TempatViewAdapter tempatViewAdapter = new TempatViewAdapter(getContext(), mListtempatPariwisata);
                            rvTempat.setAdapter(tempatViewAdapter);
                            rl.setVisibility(View.GONE);
                            rvTempat.setVisibility(View.VISIBLE);
                        }
                        else {
                            rl.setVisibility(View.VISIBLE);
                            rvTempat.setVisibility(View.GONE);
                        }
                        rl.setVisibility(View.GONE);
                        pDialog.dismiss();

                    } else {
                        rl.setVisibility(View.VISIBLE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl.setVisibility(View.VISIBLE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<TempatPariwisata>> call, @NonNull Throwable t) {
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
