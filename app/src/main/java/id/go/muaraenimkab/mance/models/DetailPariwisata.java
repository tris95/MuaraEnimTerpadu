package id.go.muaraenimkab.mance.models;

public class DetailPariwisata {
    private String id_pariwisata, id_kategori_pariwisata, nama_pariwisata, deskripsi_pariwisata, gambar_pariwisata;

    public DetailPariwisata(String id_pariwisata, String id_kategori_pariwisata,
                            String nama_pariwisata, String deskripsi_pariwisata, String gambar_pariwisata) {
        this.id_pariwisata = id_pariwisata;
        this.id_kategori_pariwisata = id_kategori_pariwisata;
        this.nama_pariwisata = nama_pariwisata;
        this.deskripsi_pariwisata = deskripsi_pariwisata;
        this.gambar_pariwisata = gambar_pariwisata;
    }

    public String getId_pariwisata() {
        return id_pariwisata;
    }

    public void setId_pariwisata(String id_pariwisata) {
        this.id_pariwisata = id_pariwisata;
    }

    public String getId_kategori_pariwisata() {
        return id_kategori_pariwisata;
    }

    public void setId_kategori_pariwisata(String id_kategori_pariwisata) {
        this.id_kategori_pariwisata = id_kategori_pariwisata;
    }

    public String getNama_pariwisata() {
        return nama_pariwisata;
    }

    public void setNama_pariwisata(String nama_pariwisata) {
        this.nama_pariwisata = nama_pariwisata;
    }

    public String getDeskripsi_pariwisata() {
        return deskripsi_pariwisata;
    }

    public void setDeskripsi_pariwisata(String deskripsi_pariwisata) {
        this.deskripsi_pariwisata = deskripsi_pariwisata;
    }

    public String getGambar_pariwisata() {
        return gambar_pariwisata;
    }

    public void setGambar_pariwisata(String gambar_pariwisata) {
        this.gambar_pariwisata = gambar_pariwisata;
    }
}
