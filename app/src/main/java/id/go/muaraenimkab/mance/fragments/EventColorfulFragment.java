package id.go.muaraenimkab.mance.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.adapters.EventColorfulViewAdapter;
import id.go.muaraenimkab.mance.adapters.EventViewAdapter;
import id.go.muaraenimkab.mance.models.Event;
import id.go.muaraenimkab.mance.models.Value;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


public class EventColorfulFragment extends Fragment {
    private static final String ARG_flag = "flag";
    private static final String ARG_flag2 = "flag2";
    String flag, flag2;
    Toolbar toolbar;
    RecyclerView rvEvent;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Event> mListEvent;
    RelativeLayout relativeLayout, rl_none;
//    TextView tv_cobalagi;
//    SwipeRefreshLayout swipeRefresh;
//    ScrollingPagerIndicator recyclerIndicator;


    public static EventColorfulFragment newInstance() {
        EventColorfulFragment fragment = new EventColorfulFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static EventColorfulFragment newInstance(String flag, String flag2) {
        EventColorfulFragment fragment = new EventColorfulFragment();
        Bundle args = new Bundle();
        args.putString(ARG_flag, flag);
        args.putString(ARG_flag2, flag2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getString(ARG_flag);
            flag2 = getArguments().getString(ARG_flag2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        relativeLayout = v.findViewById(R.id.rl);
        rvEvent = v.findViewById(R.id.rvEvent);
//        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
//        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        rl_none = v.findViewById(R.id.rl_none);

//        recyclerIndicator = v.findViewById(R.id.indicator);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            if(flag.equals("01")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Januari");
//            }else if (flag.equals("02")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Februari");
//            }else if (flag.equals("03")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Maret");
//            }else if (flag.equals("04")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("April");
//            }else if (flag.equals("05")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Mei");
//            }else if (flag.equals("06")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Juni");
//            }else if (flag.equals("07")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Juli");
//            }else if (flag.equals("08")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Agustus");
//            }else if (flag.equals("09")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("September");
//            }else if (flag.equals("10")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Oktober");
//            }else if (flag.equals("11")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("November");
//            }else if (flag.equals("12")){
//                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Desember");
//            }
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(flag);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        }

//        if (flag != null) {
//            if (flag.equals("3")) {
//                getEvent();
//            }
//        }

        if (MainActivity.events.size() != 0) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            mListEvent = new ArrayList<>();
            for (int a=0; a<MainActivity.events.size(); a++){
                try {
                    Date date = format.parse(MainActivity.events.get(a).getTanggal_pembukaan());
                    String monthNumber  = (String) DateFormat.format("MMMM", date);
//                    Log.e("date parse", ""+date+" | "+monthNumber+" | "+flag);
                    if (monthNumber.equals(flag) || monthNumber.equals(flag2)){
                        mListEvent.add(new Event(
                                MainActivity.events.get(a).getId_event(), MainActivity.events.get(a).getNama_event(),
                                MainActivity.events.get(a).getDeskripsi(), MainActivity.events.get(a).getAlamat(),
                                MainActivity.events.get(a).getLat(), MainActivity.events.get(a).getLng(),
                                MainActivity.events.get(a).getTanggal_pembukaan(), MainActivity.events.get(a).getTanggal_penutupan(),
                                MainActivity.events.get(a).getGambar_event()
                        ));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
//                    Log.e("date parse error", ""+e);
                }
            }

            if (mListEvent != null) {
                if (mListEvent.size() != 0) {
                    relativeLayout.setVisibility(View.GONE);
                    rl_none.setVisibility(View.GONE);

                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rvEvent.setLayoutManager(linearLayoutManager);
                    EventColorfulViewAdapter eventViewAdapter = new EventColorfulViewAdapter(getContext(), mListEvent);
                    rvEvent.setAdapter(eventViewAdapter);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    rl_none.setVisibility(View.VISIBLE);
                }
            }else {
                relativeLayout.setVisibility(View.GONE);
                rl_none.setVisibility(View.VISIBLE);
            }
        } else {
            relativeLayout.setVisibility(View.GONE);
            rl_none.setVisibility(View.VISIBLE);
        }

//        recyclerIndicator.setSelectedDotColor(Color.GREEN);

//        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getEvent();
//            }
//        });

//            EventViewAdapter eventViewAdapter = new EventViewAdapter(getContext(), (ArrayList<Event>) MainActivity.events);
//            rvEvent.setAdapter(eventViewAdapter);
            //rvEvent.addItemDecoration(new LinePagerIndicatorDecoration());
//            recyclerIndicator.attachToRecyclerView(rvEvent);

//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getEvent();
//            }
//        });

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
}


