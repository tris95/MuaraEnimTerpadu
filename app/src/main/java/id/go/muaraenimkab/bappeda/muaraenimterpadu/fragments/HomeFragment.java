package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.BeritaViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.ContentViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;


public class HomeFragment extends Fragment {
    Toolbar toolbar;
    SliderLayout mSlider;
    //ViewPager viewPager;
    //LinearLayout slideDots;
    //int dotCount, count = 0, slidecount;
    //ImageView[] dots;
    TextView lblBeritaselengkapnya;
    RecyclerView rvContent,rvBerita;
    ArrayList<Content> mListContent;
    ArrayList<Berita> mListBerita;
    LinearLayoutManager linearLayoutManagercontent,linearLayoutManagerberita;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Muara Enim Terpadu");
        }

        mSlider = v.findViewById(R.id.slider);
        //int images[]={R.drawable.jlntol,R.drawable.jalan};
//        viewPager = v.findViewById(R.id.vpSlideshow);
//        slideDots = v.findViewById(R.id.slidedot);
//
//        SlideAdapter slideAdapter = new SlideAdapter(getContext());
//        viewPager.setAdapter(slideAdapter);
//        dotCount = slideAdapter.getCount();
//        dots = new ImageView[dotCount];
//        slidecount = dotCount - 1;
//
//        for (int i = 0; i < dotCount; i++) {
//            dots[i] = new ImageView(getContext());
//            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(8, 0, 8, 0);
//            slideDots.addView(dots[i], params);
//        }
//        dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                for (int i = 0; i < dotCount; i++) {
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
//                }
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //Timer timer = new Timer();
        //timer.scheduleAtFixedRate(new myTimerTask(), 4000, 4000);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int more = viewPagerWidth/6;
        int viewPagerHeight = viewPagerWidth/4+more;

        mSlider.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));

        //List<Ad> listDataAd = MainActivity.ads;
        //HashMap<String,String> url_maps = new HashMap<String, String>();

//        for (int a = 0; a < listDataAd.size(); a++) {
//            url_maps.put(listDataAd.get(a).getJuduliklan(), Utilities.getURLImageIklan()+listDataAd.get(a).getGambar());
//        }

//        for(String name : url_maps.keySet()){
//            TextSliderView textSliderView = new TextSliderView(getContext());
//            textSliderView
//                    .image(url_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            mSlider.addSlider(textSliderView);
//        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setDuration(4000);

        rvContent=v.findViewById(R.id.rvContent);
        linearLayoutManagercontent=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvContent.setLayoutManager(linearLayoutManagercontent);
        ContentViewAdapter contentViewAdapter=new ContentViewAdapter(getContext(),mListContent);
        rvContent.setAdapter(contentViewAdapter);

        lblBeritaselengkapnya=v.findViewById(R.id.lblBeritaselengkapnya);
        lblBeritaselengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(KategoriBeritaFragment.newInstance(), 5);
            }
        });

        rvBerita=v.findViewById(R.id.rvBerita);
        linearLayoutManagerberita=new LinearLayoutManager(getContext());
        rvBerita.setLayoutManager(linearLayoutManagerberita);
        BeritaViewAdapter beritaViewAdapter=new BeritaViewAdapter(getContext(),mListBerita);
        rvBerita.setAdapter(beritaViewAdapter);

        return v;
    }

//    class myTimerTask extends TimerTask {
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        public void run() {
//            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (viewPager.getCurrentItem() == count) {
//                        if (count < slidecount)
//                            viewPager.setCurrentItem(count + 1);
//                        else
//                            count = 0;
//                    }
//                    else
//                        viewPager.setCurrentItem(count);
//
//                }
//            });
//
//        }
//
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
}
