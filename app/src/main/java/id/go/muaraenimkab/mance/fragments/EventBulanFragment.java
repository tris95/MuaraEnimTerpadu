package id.go.muaraenimkab.mance.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
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

public class EventBulanFragment extends Fragment {
    PagerIndicator indicator;
    RelativeLayout rlslider;
//    SliderLayout mSlider;
    ArrayList<Event> mListEvent;
    ImageView iv_iklan;
//    Toolbar toolbar;
    TextView tvJan, tvFeb, tvMar, tvApr, tvMei, tvJun, tvJul, tvAgs, tvSep, tvOkt, tvNov, tvDes;

    public EventBulanFragment() {
        // Required empty public constructor
    }

    public static EventBulanFragment newInstance() {
        EventBulanFragment fragment = new EventBulanFragment();
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
        View v = inflater.inflate(R.layout.fragment_bulan_event, container, false);
        indicator = v.findViewById(R.id.custom_indicator);
//        mSlider = v.findViewById(R.id.slider);
        rlslider = v.findViewById(R.id.rlslider);

        iv_iklan = v.findViewById(R.id.iv_iklan);

        tvJan = v.findViewById(R.id.tv_jan);
        tvFeb = v.findViewById(R.id.tv_feb);
        tvMar = v.findViewById(R.id.tv_mar);
        tvApr = v.findViewById(R.id.tv_apr);
        tvMei = v.findViewById(R.id.tv_mei);
        tvJun = v.findViewById(R.id.tv_jun);
        tvJul = v.findViewById(R.id.tv_jul);
        tvAgs = v.findViewById(R.id.tv_ags);
        tvSep = v.findViewById(R.id.tv_sep);
        tvOkt = v.findViewById(R.id.tv_okt);
        tvNov = v.findViewById(R.id.tv_nov);
        tvDes = v.findViewById(R.id.tv_des);

//        toolbar = v.findViewById(R.id.toolbar);

//        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
//
//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Event");
//        }

        Display display = ((Activity) Objects.requireNonNull(getContext())).getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int viewPagerHeight = viewPagerWidth / 2;

        rlslider.setLayoutParams(new LinearLayout.LayoutParams(viewPagerWidth, viewPagerHeight));

        if (MainActivity.ads.size() != 0) {
//            mListEvent = (ArrayList<Event>)MainActivity.events;
//            HashMap<String, String> url_maps = new HashMap<>();
//
//            for (int a = 0; a < mListEvent.size(); a++) {
//                url_maps.put(mListEvent.get(a).getNama_event(), Utilities.getBaseURLImageEvent() + mListEvent.get(a).getGambar_event());
//            }
//
//            for (String name : url_maps.keySet()) {
//                TextSliderView textSliderView = new TextSliderView(getContext());
//                textSliderView
//                        .description(name)
//                        .image(url_maps.get(name))
//                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);
//
//                textSliderView.bundle(new Bundle());
//                textSliderView.getBundle()
//                        .putString("extra", name);
//
//                mSlider.addSlider(textSliderView);
//            }
//
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//            mSlider.setCustomIndicator(indicator);
//            mSlider.setDuration(5000);

            int flag=99;
            for (int a=0; a<MainActivity.ads.size(); a++) {
                if (MainActivity.ads.get(a).getId_iklan().equals("13")){
                    flag = a;
                }
//                else {
////                    Picasso.with(getContext()).load(R.drawable.ic_me_colorful).into(iv_iklan);
//                    iv_iklan.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_me_colorful));
//                    rlslider.setVisibility(View.VISIBLE);
////                    rlslider.setVisibility(View.GONE);
//                }
            }

            if (flag!=99){
                Picasso.with(getContext()).load(Utilities.getURLImageIklan()+MainActivity.ads.get(flag).getGambar_iklan()).into(iv_iklan);
                rlslider.setVisibility(View.VISIBLE);
            }else {
                iv_iklan.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_me_colorful));
                rlslider.setVisibility(View.VISIBLE);
            }
        } else {
//            Picasso.with(getContext()).load(R.drawable.ic_me_colorful).into(iv_iklan);
            iv_iklan.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_me_colorful));
            rlslider.setVisibility(View.VISIBLE);
//            rlslider.setVisibility(View.GONE);
//            getEvent();
        }

        if (MainActivity.events.size()==0){
            getEvent();
        }

        tvJan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Januari", "January"), 5);
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("01"), 5);
            }
        });

        tvFeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("02"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Februari", "February"), 5);
            }
        });

        tvMar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("03"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Maret", "March"), 5);
            }
        });

        tvApr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("04"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("April", "April"), 5);
            }
        });

        tvMei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("05"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Mei", "May"), 5);
            }
        });

        tvJun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("06"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Juni", "June"), 5);
            }
        });

        tvJul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("07"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Juli", "July"), 5);
            }
        });

        tvAgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("08"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Agustus", "August"), 5);
            }
        });

        tvSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("09"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("September", "September"), 5);
            }
        });

        tvOkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("10"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Oktober", "October"), 5);
            }
        });

        tvNov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("11"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("November", "November"), 5);
            }
        });

        tvDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MainActivity.replaceFragment(EventColorfulFragment.newInstance("12"), 5);
                MainActivity.replaceFragment(EventColorfulFragment.newInstance("Desember", "December"), 5);
            }
        });

        return v;
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
                        mListEvent = (ArrayList<Event>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.events.clear();
                        MainActivity.events = mListEvent;

//                        if (mListEvent.size() == 0) {
//                            rlslider.setVisibility(View.GONE);
//                            pDialog.dismiss();
//                        } else {
//                            MainActivity.events.clear();
//                            MainActivity.events = mListEvent;
//
//                            HashMap<String, String> url_maps = new HashMap<>();
//
//                            for (int a = 0; a < mListEvent.size(); a++) {
//                                url_maps.put(mListEvent.get(a).getNama_event(), Utilities.getBaseURLImageEvent() + mListEvent.get(a).getGambar_event());
//                            }
//
//                            for (String name : url_maps.keySet()) {
//                                TextSliderView textSliderView = new TextSliderView(getContext());
//                                textSliderView
//                                        .description(name)
//                                        .image(url_maps.get(name))
//                                        .setScaleType(BaseSliderView.ScaleType.Fit);
//
//                                textSliderView.bundle(new Bundle());
//                                textSliderView.getBundle()
//                                        .putString("extra", name);
//
//                                mSlider.addSlider(textSliderView);
//                            }
//
//                            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//                            mSlider.setCustomIndicator(indicator);
//                            mSlider.setDuration(5000);
//
//                            rlslider.setVisibility(View.VISIBLE);
//                        }

//                        pDialog.dismiss();
                    } else {
//                        rlslider.setVisibility(View.GONE);
//                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
//                    rlslider.setVisibility(View.GONE);
//                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Event>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
//                rlslider.setVisibility(View.GONE);
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
