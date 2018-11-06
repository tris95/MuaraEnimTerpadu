package id.go.muaraenimkab.mance.models;

public class User {
    String id_user, nama_user, no_ktp, email, password, no_hp_user, alamat_user, gambar_user, status_aktif, token, id_refuser_spikm;

    public User() {
    }

    public User(String id_user, String nama_user, String no_ktp, String email, String password, String no_hp_user,
                String alamat_user, String gambar_user, String status_aktif, String token, String id_refuser_spikm) {
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.no_hp_user = no_hp_user;
        this.no_ktp = no_ktp;
        this.email = email;
        this.password = password;
        this.alamat_user = alamat_user;
        this.gambar_user = gambar_user;
        this.status_aktif = status_aktif;
        this.token = token;
        this.id_refuser_spikm = id_refuser_spikm;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_hp_user() {
        return no_hp_user;
    }

    public void setNo_hp_user(String no_hp_user) {
        this.no_hp_user = no_hp_user;
    }

    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    public String getGambar_user() {
        return gambar_user;
    }

    public void setGambar_user(String gambar_user) {
        this.gambar_user = gambar_user;
    }

    public String getStatus_aktif() {
        return status_aktif;
    }

    public void setStatus_aktif(String status_aktif) {
        this.status_aktif = status_aktif;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId_refuser_spikm() {
        return id_refuser_spikm;
    }

    public void setId_refuser_spikm(String id_refuser_spikm) {
        this.id_refuser_spikm = id_refuser_spikm;
    }
}
