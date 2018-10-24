package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


public class EventFragment extends Fragment {
    private static final String ARG_idevent = "idevent", ARG_flag = "flag";
    String idevent, flag;
    Toolbar toolbar;
    RecyclerView rvEvent;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Event> mListEvent;
    RelativeLayout relativeLayout, rl_none;
    TextView tv_cobalagi;
    SwipeRefreshLayout swipeRefresh;
    ScrollingPagerIndicator recyclerIndicator;


    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static EventFragment newInstance(String idevent, String flag) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idevent, idevent);
        args.putString(ARG_flag, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idevent = getArguments().getString(ARG_idevent);
            flag = getArguments().getString(ARG_flag);
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
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
        swipeRefresh = v.findViewById(R.id.swipeRefresh);
        rl_none = v.findViewById(R.id.rl_none);

        recyclerIndicator = v.findViewById(R.id.indicator);


        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Event");
        }

        if (flag != null) {
            if (flag.equals("3")) {
                getEvent();
            }
        }

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvEvent.setLayoutManager(linearLayoutManager);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvEvent);

        recyclerIndicator.setSelectedDotColor(Color.GREEN);

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEvent();
            }
        });

        if (MainActivity.events.size() != 0) {
            relativeLayout.setVisibility(View.GONE);
            rl_none.setVisibility(View.GONE);
            EventViewAdapter eventViewAdapter = new EventViewAdapter(getContext(), (ArrayList<Event>) MainActivity.events);
            rvEvent.setAdapter(eventViewAdapter);
            //rvEvent.addItemDecoration(new LinePagerIndicatorDecoration());
            recyclerIndicator.attachToRecyclerView(rvEvent);
        } else {
            getEvent();
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEvent();
            }
        });

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
                swipeRefresh.setRefreshing(false);
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListEvent = (ArrayList<Event>) Objects.requireNonNull(response.body()).getData();

                        if (mListEvent.size() == 0) {
                            rl_none.setVisibility(View.VISIBLE);
                        } else {
                            relativeLayout.setVisibility(View.GONE);
                            rl_none.setVisibility(View.GONE);
                            MainActivity.events.clear();
                            MainActivity.events = mListEvent;

                            EventViewAdapter eventViewAdapter = new EventViewAdapter(getContext(), mListEvent);
                            rvEvent.setAdapter(eventViewAdapter);
                            //rvEvent.addItemDecoration(new LinePagerIndicatorDecoration());
                            recyclerIndicator.attachToRecyclerView(rvEvent);
                        }
                    } else {
                        rl_none.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    rl_none.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Event>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                swipeRefresh.setRefreshing(false);
                pDialog.dismiss();
                rl_none.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

//
//    public class LinePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
//        private final float DP = Resources.getSystem().getDisplayMetrics().density;
//        /**
//         * Height of the space the indicator takes up at the bottom of the view.
//         */
//        private final int mIndicatorHeight = (int) (DP * 16);
//        /**
//         * Indicator stroke width.
//         */
//        private final float mIndicatorStrokeWidth = DP * 2;
//        /**
//         * Indicator width.
//         */
//        private final float mIndicatorItemLength = DP * 16;
//        /**
//         * Padding between indicators.
//         */
//        private final float mIndicatorItemPadding = DP * 4;
//        /**
//         * Some more natural animation interpolation
//         */
//        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
//        private final Paint mPaint = new Paint();
//
//        LinePagerIndicatorDecoration() {
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            mPaint.setStrokeWidth(mIndicatorStrokeWidth);
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setAntiAlias(true);
//        }
//
//        @Override
//        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDrawOver(c, parent, state);
//            int itemCount = parent.getAdapter().getItemCount();
//            // center horizontally, calculate width and subtract half from center
//            float totalLength = mIndicatorItemLength * itemCount;
//            float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
//            float indicatorTotalWidth = totalLength + paddingBetweenItems;
//            float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;
//            // center vertically in the allotted space
//            float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;
//            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);
//
//            // find active page (which should be highlighted)
//            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
//            int activePosition = layoutManager.findFirstVisibleItemPosition();
//            if (activePosition == RecyclerView.NO_POSITION) {
//                return;
//            }
//
//            // find offset of active page (if the user is scrolling)
//            final View activeChild = layoutManager.findViewByPosition(activePosition);
//            int left = activeChild.getLeft();
//            int width = activeChild.getWidth();
//            // on swipe the active item will be positioned from [-width, 0]
//            // interpolate offset for smooth animation
//            float progress = mInterpolator.getInterpolation(left * -1 / (float) width);
//            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount);
//        }
//
//        private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
//            mPaint.setColor(Color.GRAY);
//            // width of item indicator including padding
//            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;
//            float start = indicatorStartX;
//
//            for (int i = 0; i < itemCount; i++) {
//                // draw the line for every item
//                c.drawLine(start, indicatorPosY, start + mIndicatorItemLength, indicatorPosY, mPaint);
//                start += itemWidth;
//            }
//        }
//
//
//        private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
//
//                                    int highlightPosition, float progress, int itemCount) {
//            mPaint.setColor(Color.GREEN);
//            // width of item indicator including padding
//            final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;
//
//            if (progress == 0F) {
//                // no swipe, draw a normal indicator
//                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
//                c.drawLine(highlightStart, indicatorPosY,
//                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
//            } else {
//                float highlightStart = indicatorStartX + itemWidth * highlightPosition;
//                // calculate partial highlight
//                float partialLength = mIndicatorItemLength * progress;
//
//                // draw the cut off highlight
//                c.drawLine(highlightStart + partialLength, indicatorPosY,
//                        highlightStart + mIndicatorItemLength, indicatorPosY, mPaint);
//                // draw the highlight overlapping to the next item as well
//                if (highlightPosition < itemCount - 1) {
//                    highlightStart += itemWidth;
//                    c.drawLine(highlightStart, indicatorPosY,
//                            highlightStart + partialLength, indicatorPosY, mPaint);
//                }
//            }
//        }
//    }

//        @Override
//
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//
//            super.getItemOffsets(outRect, view, parent, state);
//
//            outRect.bottom = mIndicatorHeight;
//
//        }

}


