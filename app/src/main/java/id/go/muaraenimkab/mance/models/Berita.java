package id.go.muaraenimkab.mance.models;

public class Berita {
    private String id_berita, judul_berita, isi_berita, gambar_berita, tanggal_post, jumlahlike, jumlahview;

    public Berita(String id_berita, String judul_berita, String isi_berita, String gambar_berita, String tanggal_post, String jumlahlike, String jumlahview) {
        this.id_berita = id_berita;
        this.judul_berita = judul_berita;
        this.isi_berita = isi_berita;
        this.gambar_berita = gambar_berita;
        this.tanggal_post = tanggal_post;
        this.jumlahlike = jumlahlike;
        this.jumlahview = jumlahview;
    }

    public String getId_berita() {
        return id_berita;
    }

    public void setId_berita(String id_berita) {
        this.id_berita = id_berita;
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

    public void setTanggal_post(String tanggl_post) {
        this.tanggal_post = tanggl_post;
    }

    public String getJumlahlike() {
        return jumlahlike;
    }

    public void setJumlahlike(String jumlahlike) {
        this.jumlahlike = jumlahlike;
    }

    public String getJumlahview() {
        return jumlahview;
    }

    public void setJumlahview(String jumlahview) {
        this.jumlahview = jumlahview;
    }
}
