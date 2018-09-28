package id.go.muaraenimkab.bappeda.muaraenimterpadu.models;

public class Laporan {
    private String id_laporan, judul_laporan, isi_laporan, alamat, no_hp, tanggal_laporan, status_tanggapan, foto;

    public Laporan(String id_laporan, String judul_laporan, String isi_laporan, String alamat, String no_hp, String tanggal_laporan, String status_tanggapan, String foto){
        this.alamat = alamat;
        this.foto = foto;
        this.id_laporan = id_laporan;
        this.judul_laporan = judul_laporan;
        this.isi_laporan = isi_laporan;
        this.no_hp = no_hp;
        this.tanggal_laporan = tanggal_laporan;
        this.status_tanggapan = status_tanggapan;
    }

    public String getId_laporan() {
        return id_laporan;
    }

    public void setId_laporan(String id_laporan) {
        this.id_laporan = id_laporan;
    }

    public String getJudul_laporan() {
        return judul_laporan;
    }

    public void setJudul_laporan(String judul_laporan) {
        this.judul_laporan = judul_laporan;
    }

    public String getIsi_laporan() {
        return isi_laporan;
    }

    public void setIsi_laporan(String isi_laporan) {
        this.isi_laporan = isi_laporan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getTanggal_laporan() {
        return tanggal_laporan;
    }

    public void setTanggal_laporan(String tanggal_laporan) {
        this.tanggal_laporan = tanggal_laporan;
    }

    public String getStatus_tanggapan() {
        return status_tanggapan;
    }

    public void setStatus_tanggapan(String status_tanggapan) {
        this.status_tanggapan = status_tanggapan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
