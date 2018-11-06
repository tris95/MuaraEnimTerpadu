package id.go.muaraenimkab.mance.models;

public class KategoriBerita {

    private String id_kategori_berita, nama_kategori_berita, gambar_kategori_berita;

    public KategoriBerita(String id_kategori_berita, String nama_kategori_berita, String gambar_kategori_berita) {
        this.id_kategori_berita = id_kategori_berita;
        this.nama_kategori_berita = nama_kategori_berita;
        this.gambar_kategori_berita = gambar_kategori_berita;
    }

    public String getId_kategori_berita() {
        return id_kategori_berita;
    }

    public void setId_kategori_berita(String id_kategori_berita) {
        this.id_kategori_berita = id_kategori_berita;
    }

    public String getNama_kategori_berita() {
        return nama_kategori_berita;
    }

    public void setNama_kategori_berita(String nama_kategori_berita) {
        this.nama_kategori_berita = nama_kategori_berita;
    }

    public String getGambar_kategori_berita() {
        return gambar_kategori_berita;
    }

    public void setGambar_kategori_berita(String gambar_kategori_berita) {
        this.gambar_kategori_berita = gambar_kategori_berita;
    }
}
