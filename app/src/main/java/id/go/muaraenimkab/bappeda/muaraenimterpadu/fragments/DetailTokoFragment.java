package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;


public class DetailTokoFragment extends Fragment {
    Toolbar toolbar;
    private static final String ARG_idpariwisata = "idpariwisata";
    String idpariwisata;
    public DetailTokoFragment() {
        // Required empty public constructor
    }

    public static DetailTokoFragment newInstance(String idpariwisata) {
        DetailTokoFragment fragment = new DetailTokoFragment();
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
        View v=inflater.inflate(R.layout.fragment_detail_toko, container, false);
        toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Daftar Toko");
        }

        return v;
    }


}
