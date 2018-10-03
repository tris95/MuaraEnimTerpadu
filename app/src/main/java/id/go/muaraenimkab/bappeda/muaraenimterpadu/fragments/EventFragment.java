package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

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

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.EventViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.KontakViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvEvent;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Event> mListEvent;
    RelativeLayout relativeLayout;
    TextView tv_cobalagi;

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
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
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        relativeLayout = v.findViewById(R.id.rl);
        rvEvent = v.findViewById(R.id.rvEvent);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Event");
        }

//        linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        rvEvent.setLayoutManager(linearLayoutManager);
//        EventViewAdapter eventViewAdapter=new EventViewAdapter(getContext(),mListEvent);
//        rvEvent.setAdapter(eventViewAdapter);

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEvent();
            }
        });

        getEvent();

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getEvent() {
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
        Call<Value<Event>> call = api.getevent(random);
        call.enqueue(new Callback<Value<Event>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Event>> call, @NonNull Response<Value<Event>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        relativeLayout.setVisibility(View.GONE);
                        mListEvent = (ArrayList<Event>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.events = mListEvent;

                        linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        rvEvent.setLayoutManager(linearLayoutManager);
                        EventViewAdapter eventViewAdapter=new EventViewAdapter(getContext(),mListEvent);
                        rvEvent.setAdapter(eventViewAdapter);
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Event>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                relativeLayout.setVisibility(View.VISIBLE);
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}


