package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.content.Context;
import android.net.Uri;
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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.KategoriBeritaViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;


public class KategoriBeritaFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvKategoriBerita;
    LinearLayoutManager linearLayoutManager;
    ArrayList<KategoriBerita> mListKategoriBerita;
    public KategoriBeritaFragment() {
        // Required empty public constructor
    }

    public static KategoriBeritaFragment newInstance() {
        KategoriBeritaFragment fragment = new KategoriBeritaFragment();
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
        View v = inflater.inflate(R.layout.fragment_kategori_berita, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Kategori Berita");
        }
        rvKategoriBerita=v.findViewById(R.id.rvKategoriBerita);
        linearLayoutManager=new LinearLayoutManager(getContext());
        rvKategoriBerita.setLayoutManager(linearLayoutManager);
        KategoriBeritaViewAdapter kategoriberitaViewAdapter=new KategoriBeritaViewAdapter(getContext(),mListKategoriBerita);
        rvKategoriBerita.setAdapter(kategoriberitaViewAdapter);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
