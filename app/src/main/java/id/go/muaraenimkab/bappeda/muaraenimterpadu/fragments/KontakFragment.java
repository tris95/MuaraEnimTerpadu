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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.KontakViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;

public class KontakFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvKontak;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Kontak> mListKontak;

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
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Kontak");
        }

        rvKontak=v.findViewById(R.id.rvKontak);
        linearLayoutManager=new LinearLayoutManager(getContext());
        rvKontak.setLayoutManager(linearLayoutManager);
        KontakViewAdapter kontakViewAdapter=new KontakViewAdapter(getContext(),mListKontak);
        rvKontak.setAdapter(kontakViewAdapter);
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
