package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;


public class DetailWisataFragment extends Fragment {
    Toolbar toolbar;
    RelativeLayout rlup,rlback;
    private BottomSheetBehavior bottomSheetBehavior;
    View bottomsheet;
    ImageView imgup,imgback,imgdetailwisata;
    MainActivity mainActivity =new MainActivity();
    String idparawisata, namapariwisata,gambarpariwisata;
    private static final String ARG_idparawisata = "idparawisata",ARG_namapariwisata = "namapariwisata",ARG_gambarpariwisata = "gambarpariwisata";

    public DetailWisataFragment() {
        // Required empty public constructor
    }

    public static DetailWisataFragment newInstance(String idpariwisata,String namapariwisata,String gambarpariwisata) {
        DetailWisataFragment fragment = new DetailWisataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idparawisata, idpariwisata);
        args.putString(ARG_namapariwisata, namapariwisata);
        args.putString(ARG_gambarpariwisata, gambarpariwisata);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idparawisata = getArguments().getString(ARG_idparawisata);
            namapariwisata = getArguments().getString(ARG_namapariwisata);
            gambarpariwisata = getArguments().getString(ARG_gambarpariwisata);
         }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_wisata, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        bottomsheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        imgup = v.findViewById(R.id.imgup);
        imgback = v.findViewById(R.id.imgback);
        imgdetailwisata=v.findViewById(R.id.imgdetailwisata);
        rlup = v.findViewById(R.id.rlup);
        rlback=v.findViewById(R.id.rlback);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(false);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(namapariwisata);
        }

        Picasso.with(getContext())
                .load(gambarpariwisata)
                .into(imgdetailwisata);

        imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.Back();
            }
        });
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
                        }
                        toolbar.setBackgroundColor(Color.parseColor("#41c300"));
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        rlup.setVisibility(View.VISIBLE);
                        rlback.setVisibility(View.VISIBLE);

                        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
                            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(false);
                        }
                        toolbar.setBackgroundColor(Color.parseColor("#80000000"));
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                rlup.setVisibility(View.GONE);
                rlback.setVisibility(View.GONE);

            }
        });
        return v;
    }

    public void onButtonPressed(Uri uri) {
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
