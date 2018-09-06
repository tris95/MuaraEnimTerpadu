package id.go.muaraenimkab.bappeda.muaraenimterpadu.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marhadi Wijaya on 8/20/2017.
 */

public class Ad{
    private String id_iklan, judul_iklan, gambar_iklan,tanggal_mulai,tanggal_berakhir;

    public Ad(String id_iklan, String judul_iklan, String gambar_iklan, String tanggal_mulai, String tanggal_berakhir) {
        this.id_iklan = id_iklan;
        this.judul_iklan = judul_iklan;
        this.gambar_iklan = gambar_iklan;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_berakhir = tanggal_berakhir;
    }

    public String getId_iklan() {
        return id_iklan;
    }

    public void setId_iklan(String id_iklan) {
        this.id_iklan = id_iklan;
    }

    public String getJudul_iklan() {
        return judul_iklan;
    }

    public void setJudul_iklan(String judul_iklan) {
        this.judul_iklan = judul_iklan;
    }

    public String getGambar_iklan() {
        return gambar_iklan;
    }

    public void setGambar_iklan(String gambar_iklan) {
        this.gambar_iklan = gambar_iklan;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_berakhir() {
        return tanggal_berakhir;
    }

    public void setTanggal_berakhir(String tanggal_berakhir) {
        this.tanggal_berakhir = tanggal_berakhir;
    }
}
