package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignInActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;


public class ProfilFragment extends Fragment {
    Toolbar toolbar;
    Button btnAction;
    CircularImageView imgProfil;
    EditText etNama, etNoKtp, etEmail, etNoHp, etAlamat;
    public static boolean flagback;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
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
        View v=inflater.inflate(R.layout.fragment_profil, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        btnAction = v.findViewById(R.id.btnAction);
        imgProfil = v.findViewById(R.id.imgProfil);
        etNama = v.findViewById(R.id.etNama);
        etNoKtp = v.findViewById(R.id.etNoKtp);
        etNoHp = v.findViewById(R.id.etNoHp);
        etEmail = v.findViewById(R.id.etEmail);
        etAlamat = v.findViewById(R.id.etAlamat);

        flagback = false;

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Profile");
        }

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnAction.getText().toString().equals("SignIn")){
                    startActivity(new Intent(getContext(), SignInActivity.class));
                }else if (btnAction.getText().toString().equals("SignOut")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true)
                            .setTitle("Konfirmasi")
                            .setMessage("Keluar dari akun ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btnAction.setText("SignIn");
                                    Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser()+"default.png");
                                    etAlamat.setText("");
                                    etEmail.setText("");
                                    etNoHp.setText("");
                                    etNama.setText("");
                                    etNoKtp.setText("");
                                    Utilities.signOutUser(getContext());
                                    Intent mIntent = new Intent(getContext(), SignInActivity.class);
                                    startActivity(mIntent);
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!flagback) {
            if (Utilities.isLogin(getActivity())) {
                btnAction.setText("SignOut");
                User users = Utilities.getUser(getActivity());
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + users.getGambar_user()).into(imgProfil);
                etAlamat.setText(users.getAlamat_user());
                etEmail.setText(users.getEmail());
                etNoHp.setText(users.getNo_hp_user());
                etNama.setText(users.getNama_user());
                etNoKtp.setText(users.getNo_ktp());
            } else {
                btnAction.setText("SignIn");
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + "default.png").into(imgProfil);
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        }else {
            if (SignInActivity.flagsignin){
                btnAction.setText("SignOut");
                User users = Utilities.getUser(getActivity());
                Picasso.with(getActivity()).load(Utilities.getBaseURLImageUser() + users.getGambar_user()).into(imgProfil);
                etAlamat.setText(users.getAlamat_user());
                etEmail.setText(users.getEmail());
                etNoHp.setText(users.getNo_hp_user());
                etNama.setText(users.getNama_user());
                etNoKtp.setText(users.getNo_ktp());
            }
        }
    }

    //    public void onButtonPressed(Uri uri) {
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
