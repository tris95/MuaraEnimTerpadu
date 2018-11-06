package id.go.muaraenimkab.mance.models;

public class Content {
    private String id_kategori_pariwisata, nama_kategori_pariwisata, jumlah_tempat, gambar_kategori_pariwisata;

    public Content(String id_kategori_pariwisata, String nama_kategori_pariwisata, String jumlah_tempat, String gambar_kategori_pariwisata) {
        this.id_kategori_pariwisata = id_kategori_pariwisata;
        this.nama_kategori_pariwisata = nama_kategori_pariwisata;
        this.jumlah_tempat = jumlah_tempat;
        this.gambar_kategori_pariwisata = gambar_kategori_pariwisata;
    }

    public String getId_kategori_pariwisata() {
        return id_kategori_pariwisata;
    }

    public void setId_kategori_pariwisata(String id_kategori_pariwisata) {
        this.id_kategori_pariwisata = id_kategori_pariwisata;
    }

    public String getNama_kategori_pariwisata() {
        return nama_kategori_pariwisata;
    }

    public void setNama_kategori_pariwisata(String nama_kategori_pariwisata) {
        this.nama_kategori_pariwisata = nama_kategori_pariwisata;
    }

    public String getJumlah_tempat() {
        return jumlah_tempat;
    }

    public void setJumlah_tempat(String jumlah_tempat) {
        this.jumlah_tempat = jumlah_tempat;
    }

    public String getGambar_kategori_pariwisata() {
        return gambar_kategori_pariwisata;
    }

    public void setGambar_kategori_pariwisata(String gambar_kategori_pariwisata) {
        this.gambar_kategori_pariwisata = gambar_kategori_pariwisata;
    }
}
