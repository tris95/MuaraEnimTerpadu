package id.go.muaraenimkab.bappeda.muaraenimterpadu.models;

public class Opd {
    String id_opd, nama_opd, alamat, no_telp;
    public Opd(String id_opd, String nama_opd, String alamat, String no_telp){
        this.id_opd = id_opd;
        this.nama_opd = nama_opd;
        this.alamat = alamat;
        this.no_telp = no_telp;
    }

    public String getId_opd() {
        return id_opd;
    }

    public void setId_opd(String id_opd) {
        this.id_opd = id_opd;
    }

    public String getNama_opd() {
        return nama_opd;
    }

    public void setNama_opd(String nama_opd) {
        this.nama_opd = nama_opd;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }
}
