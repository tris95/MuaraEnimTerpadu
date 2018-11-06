package id.go.muaraenimkab.mance.models;

public class TempatPariwisata {
    private String id_tempat_pariwisata, id_pariwisata, nama_tempat_pariwisata,
            deskripsi_tempat_pariwisata, no_hp, alamat, lat, lng, jam_buka, jam_tutup, gambar_lokasi;

    public TempatPariwisata(String id_tempat_pariwisata, String id_pariwisata, String nama_tempat_pariwisata, String deskripsi_tempat_pariwisata,
                            String no_hp, String alamat, String lat, String lng, String jam_buka, String jam_tutup, String gambar_lokasi) {
        this.id_tempat_pariwisata = id_tempat_pariwisata;
        this.id_pariwisata = id_pariwisata;
        this.nama_tempat_pariwisata = nama_tempat_pariwisata;
        this.deskripsi_tempat_pariwisata = deskripsi_tempat_pariwisata;
        this.no_hp = no_hp;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.jam_buka = jam_buka;
        this.jam_tutup = jam_tutup;
        this.gambar_lokasi = gambar_lokasi;
    }

    public String getId_tempat_pariwisata() {
        return id_tempat_pariwisata;
    }

    public void setId_tempat_pariwisata(String id_tempat_pariwisata) {
        this.id_tempat_pariwisata = id_tempat_pariwisata;
    }

    public String getId_pariwisata() {
        return id_pariwisata;
    }

    public void setId_pariwisata(String id_pariwisata) {
        this.id_pariwisata = id_pariwisata;
    }

    public String getNama_tempat_pariwisata() {
        return nama_tempat_pariwisata;
    }

    public void setNama_tempat_pariwisata(String nama_tempat_pariwisata) {
        this.nama_tempat_pariwisata = nama_tempat_pariwisata;
    }

    public String getDeskripsi_tempat_pariwisata() {
        return deskripsi_tempat_pariwisata;
    }

    public void setDeskripsi_tempat_pariwisata(String deskripsi_tempat_pariwisata) {
        this.deskripsi_tempat_pariwisata = deskripsi_tempat_pariwisata;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public String getJam_tutup() {
        return jam_tutup;
    }

    public void setJam_tutup(String jam_tutup) {
        this.jam_tutup = jam_tutup;
    }

    public String getGambar_lokasi() {
        return gambar_lokasi;
    }

    public void setGambar_lokasi(String gambar_lokasi) {
        this.gambar_lokasi = gambar_lokasi;
    }
}
