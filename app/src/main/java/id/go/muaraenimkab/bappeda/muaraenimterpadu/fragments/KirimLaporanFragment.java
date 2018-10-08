package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignInActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.EventViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Laporan;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Opd;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.ValueAdd;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class KirimLaporanFragment extends Fragment {
    EditText txtJudul, txtIsi, txtLokasi, txtNoHp;
    Button btnKirim;
    CircularImageView imgLaporan;
    String foto;
    Spinner spOpd;
    List<String> idOpd = new ArrayList<>();

    public KirimLaporanFragment() {
        // Required empty public constructor
    }

    public static KirimLaporanFragment newInstance() {
        KirimLaporanFragment fragment = new KirimLaporanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_kirim_laporan, container, false);
        txtIsi = v.findViewById(R.id.txtIsi);
        txtJudul = v.findViewById(R.id.txtJudul);
        btnKirim = v.findViewById(R.id.btnKirim);
        txtLokasi = v.findViewById(R.id.txtLokasi);
        txtNoHp = v.findViewById(R.id.txtNoHp);
        imgLaporan = v.findViewById(R.id.imgLaporan);
        spOpd = v.findViewById(R.id.spOpd);

        foto="";

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                if (txtJudul.getText().toString().isEmpty()){
                    txtJudul.setError("Silahkan isi judul laporan Anda");
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan isi judul laporan Anda",
                            Snackbar.LENGTH_LONG).show();
                }else if (txtIsi.getText().toString().isEmpty()){
                    txtIsi.setError("Silahkan isi laporan Anda");
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan isi laporan Anda",
                            Snackbar.LENGTH_LONG).show();
                }else if (txtNoHp.getText().toString().isEmpty()){
                    txtNoHp.setError("Silahkan isi No. Hp Anda");
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan isi No. Hp Anda",
                            Snackbar.LENGTH_LONG).show();
                }else if (spOpd.getSelectedItemPosition() == 0){
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan pilih Organisasi Perangkat Daerah Tujuan",
                            Snackbar.LENGTH_LONG).show();
                }else {
                    if (Utilities.isLogin(getContext())) {
                        User users = Utilities.getUser(getContext());
                        laporan(users.getId_user(), idOpd.get(spOpd.getSelectedItemPosition()), txtNoHp.getText().toString().trim(), txtJudul.getText().toString().trim(), txtIsi.getText().toString().trim(), txtLokasi.getText().toString());
                    }else {
                        startActivity(new Intent(getContext(), SignInActivity.class));
                    }
                }
            }
        });

        imgLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAmbilGambar();
            }
        });

        txtNoHp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int DRAWABLE_RIGHT = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX() >= (txtNoHp.getRight()-txtNoHp.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        User user = Utilities.getUser(getContext());
                        txtNoHp.setText(user.getNo_hp_user());
                    }
                }
                return false;
            }
        });

//        spOpd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                Toast.makeText(getContext(), spOpd.getSelectedItemPosition() + " " + spOpd.getSelectedItem(), Toast.LENGTH_SHORT).show();
//                if (spOpd.getSelectedItemPosition() != 0)
//                    Toast.makeText(getContext(), idOpd.get(spOpd.getSelectedItemPosition())+"", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        if (Utilities.isLogin(getContext())) {
            if (MainActivity.opds.size() != 0){
                idOpd.clear();
                List<String> arr = new ArrayList<>();
                for (int a=0; a<MainActivity.opds.size(); a++){
                    if(a==0){
                        idOpd.add("-");
                        arr.add("Silahkan pilih Organisasi Perangkat Daerah");
                    }else {
                        idOpd.add(MainActivity.opds.get(a-1).getId_opd());
                        arr.add(MainActivity.opds.get(a-1).getNama_opd());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr);
                spOpd.setAdapter(adapter);
            }else {
                getOpd();
            }
        }else {
            startActivity(new Intent(getContext(), SignInActivity.class));
            getActivity().finish();
        }

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getOpd() {
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
        Call<Value<Opd>> call = api.getopd(random);
        call.enqueue(new Callback<Value<Opd>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Opd>> call, @NonNull Response<Value<Opd>> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        idOpd.clear();
                        List<String> arr = new ArrayList<>();
                        MainActivity.opds = response.body().getData();
                        for (int a=0; a<response.body().getData().size(); a++){
                            if(a==0){
                                idOpd.add("-");
                                arr.add("Silahkan pilih Organisasi Perangkat Daerah");
                            }else {
                                idOpd.add(response.body().getData().get(a-1).getId_opd());
                                arr.add(response.body().getData().get(a-1).getNama_opd());
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr);
                        spOpd.setAdapter(adapter);
                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Opd>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void laporan(final String id, final String idopd, final String hp, final String judul, final String isi, final String lokasi) {
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
        Call<ValueAdd> call = api.kirimlaporan(random, id, idopd, judul, isi, hp, lokasi, foto);
        call.enqueue(new Callback<ValueAdd>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ValueAdd> call, @NonNull Response<ValueAdd> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    Log.e("boo", response.body().getMessage()+" "+id+" "+idopd+" "+judul+" "+isi+" "+hp+" "+lokasi+" "+foto);
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        txtIsi.setText("");
                        txtJudul.setText("");
                        imgLaporan.setImageDrawable(getResources().getDrawable(R.drawable.add_image));
                        txtLokasi.setText("");
                        txtNoHp.setText("");
                        foto="";
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Laporan berhasil dikirim", Snackbar.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengirim laporan. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengirim data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<ValueAdd> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void dialogAmbilGambar() {
        final CharSequence[] options = { "Camera", "Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ambil foto dari ?");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Camera"))
                {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                1);
                    }else {
                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                }
                else if (options[item].equals("Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                2);
                    }else {
                        if(Build.VERSION.SDK_INT>=24){
                            try{
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    }
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(Build.VERSION.SDK_INT>=24){
                        try{
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap imageBitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    imageBitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    foto = Utilities.getArrayByteFromBitmap(imageBitmap);
                    imgLaporan.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri imageUri = data.getData();
                InputStream imageStream = null;
                Bitmap imageBitmap;
                try{
                    imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    imageBitmap = BitmapFactory.decodeStream(imageStream);
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    int w = imageBitmap.getWidth();
//                    int h = imageBitmap.getHeight();
//                    w=w/2;h=h/2;
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, 480, 480, true);
                    foto = Utilities.getArrayByteFromBitmap(imageBitmap);
                    imgLaporan.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finally {
                    if (imageStream!=null){
                        try{
                            imageStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
