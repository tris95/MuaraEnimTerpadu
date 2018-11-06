package id.go.muaraenimkab.mance.models;

public class Event {
    private String id_event, nama_event, deskripsi, alamat, lat, lng, tanggal_pembukaan, tanggal_penutupan, gambar_event;

    public Event(String id_event, String nama_event, String deskripsi, String alamat, String lat, String lng, String tanggal_pembukaan, String tanggal_penutupan, String gambar_event) {
        this.id_event = id_event;
        this.nama_event = nama_event;
        this.deskripsi = deskripsi;
        this.alamat = alamat;
        this.lat = lat;
        this.lng = lng;
        this.tanggal_pembukaan = tanggal_pembukaan;
        this.tanggal_penutupan = tanggal_penutupan;
        this.gambar_event = gambar_event;
    }

    public String getId_event() {
        return id_event;
    }

    public void setId_event(String id_event) {
        this.id_event = id_event;
    }

    public String getNama_event() {
        return nama_event;
    }

    public void setNama_event(String nama_event) {
        this.nama_event = nama_event;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
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

    public String getTanggal_pembukaan() {
        return tanggal_pembukaan;
    }

    public void setTanggal_pembukaan(String tanggal_pembukaan) {
        this.tanggal_pembukaan = tanggal_pembukaan;
    }

    public String getTanggal_penutupan() {
        return tanggal_penutupan;
    }

    public void setTanggal_penutupan(String tanggal_penutupan) {
        this.tanggal_penutupan = tanggal_penutupan;
    }

    public String getGambar_event() {
        return gambar_event;
    }

    public void setGambar_event(String gambar_event) {
        this.gambar_event = gambar_event;
    }
}
