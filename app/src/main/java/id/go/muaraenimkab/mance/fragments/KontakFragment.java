package id.go.muaraenimkab.mance.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.adapters.KontakViewAdapter;
import id.go.muaraenimkab.mance.models.Kontak;
import id.go.muaraenimkab.mance.models.Value;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KontakFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvKontak;
    RelativeLayout rl;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Kontak> mListKontak;
    LinearLayoutManager linearLayoutManagerkontak;
    TextView tv_cobalagi;

    public KontakFragment() {
        // Required empty public constructor
    }

    public static KontakFragment newInstance() {
        KontakFragment fragment = new KontakFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kontak, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        rl = v.findViewById(R.id.rl);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Kontak");
        }

        rvKontak = v.findViewById(R.id.rvKontak);

        if (MainActivity.kontaks.size() != 0) {
            linearLayoutManagerkontak = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rvKontak.setLayoutManager(linearLayoutManagerkontak);
            KontakViewAdapter kontakViewAdapter = new KontakViewAdapter(getContext(), (ArrayList<Kontak>) MainActivity.kontaks);
            rvKontak.setAdapter(kontakViewAdapter);
        } else
            getKontak();

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKontak();
            }
        });
        return v;
    }

    private void getKontak() {
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
        Call<Value<Kontak>> call = api.getKontak(random);
        call.enqueue(new Callback<Value<Kontak>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Kontak>> call, @NonNull Response<Value<Kontak>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListKontak = (ArrayList<Kontak>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.kontaks.clear();
                        MainActivity.kontaks = mListKontak;

                        linearLayoutManager = new LinearLayoutManager(getContext());
                        rvKontak.setLayoutManager(linearLayoutManager);
                        KontakViewAdapter kontakViewAdapter = new KontakViewAdapter(getContext(), mListKontak);
                        rvKontak.setAdapter(kontakViewAdapter);

                        rl.setVisibility(View.GONE);
                        pDialog.dismiss();
                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Kontak>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
