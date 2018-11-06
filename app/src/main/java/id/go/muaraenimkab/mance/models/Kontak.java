package id.go.muaraenimkab.mance.models;

public class Kontak {

    private String id_kantor, nama_kantor, alamat, lat, lng, no_tlp, gambar_kantor;

    public Kontak(String id_kantor, String nama_kantor, String alamat, String lat, String lng, String no_tlp, String gambar_kantor) {
        this.id_kantor = id_kantor;
        this.nama_kantor = nama_kantor;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.no_tlp = no_tlp;
        this.gambar_kantor = gambar_kantor;
    }

    public String getId_kantor() {
        return id_kantor;
    }

    public void setId_kantor(String id_kantor) {
        this.id_kantor = id_kantor;
    }

    public String getNama_kantor() {
        return nama_kantor;
    }

    public void setNama_kantor(String nama_kantor) {
        this.nama_kantor = nama_kantor;
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

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getGambar_kantor() {
        return gambar_kantor;
    }

    public void setGambar_kantor(String gambar_kantor) {
        this.gambar_kantor = gambar_kantor;
    }
}
