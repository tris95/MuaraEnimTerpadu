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
import android.widget.Button;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;


public class DetailKulinerFragment extends Fragment {
    Toolbar toolbar;
    Button btnToko;
    String idpariwisata,namapariwisata;
    private static final String ARG_idpariwisata = "idpariwisata",ARG_namapariwisata = "namapariwisata";

    public DetailKulinerFragment() {
        // Required empty public constructor
    }

    public static DetailKulinerFragment newInstance(String idpariwisata, String namapariwisata) {
        DetailKulinerFragment fragment = new DetailKulinerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idpariwisata, idpariwisata);
        args.putString(ARG_namapariwisata, namapariwisata);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idpariwisata = getArguments().getString(ARG_idpariwisata);
            namapariwisata = getArguments().getString(ARG_namapariwisata);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_kuliner, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        btnToko = v.findViewById(R.id.btnToko);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("");
        }

        btnToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(DetailTokoFragment.newInstance(idpariwisata), 7);
            }
        });
        return v;
    }


}
