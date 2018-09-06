package id.go.muaraenimkab.bappeda.muaraenimterpadu.models;

public class Berita {
    private String id_berita,id_kategori_berita,judul_berita,isi_berita,gambar_berita,tanggal_post,status_aktif,id_admin;

    public Berita(String id_berita, String id_kategori_berita, String judul_berita, String isi_berita, String gambar_berita,
                  String tanggal_post, String status_aktif, String id_admin) {
        this.id_berita = id_berita;
        this.id_kategori_berita = id_kategori_berita;
        this.judul_berita = judul_berita;
        this.isi_berita = isi_berita;
        this.gambar_berita = gambar_berita;
        this.tanggal_post = tanggal_post;
        this.status_aktif = status_aktif;
        this.id_admin = id_admin;
    }

    public String getId_berita() {
        return id_berita;
    }

    public void setId_berita(String id_berita) {
        this.id_berita = id_berita;
    }

    public String getId_kategori_berita() {
        return id_kategori_berita;
    }

    public void setId_kategori_berita(String id_kategori_berita) {
        this.id_kategori_berita = id_kategori_berita;
    }

    public String getJudul_berita() {
        return judul_berita;
    }

    public void setJudul_berita(String judul_berita) {
        this.judul_berita = judul_berita;
    }

    public String getIsi_berita() {
        return isi_berita;
    }

    public void setIsi_berita(String isi_berita) {
        this.isi_berita = isi_berita;
    }

    public String getGambar_berita() {
        return gambar_berita;
    }

    public void setGambar_berita(String gambar_berita) {
        this.gambar_berita = gambar_berita;
    }

    public String getTanggal_post() {
        return tanggal_post;
    }

    public void setTanggal_post(String tanggal_post) {
        this.tanggal_post = tanggal_post;
    }

    public String getStatus_aktif() {
        return status_aktif;
    }

    public void setStatus_aktif(String status_aktif) {
        this.status_aktif = status_aktif;
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }
}
