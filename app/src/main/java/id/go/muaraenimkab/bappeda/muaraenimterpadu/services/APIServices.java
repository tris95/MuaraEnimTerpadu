package id.go.muaraenimkab.bappeda.muaraenimterpadu.services;


import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Ad;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.DetailPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Laporan;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Opd;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Pariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.TempatPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.ValueAdd;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIServices {


    @FormUrlEncoded
    @POST("getactivead.php")
    Call<Value<Ad>> getactivead(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("signin.php")
    Call<Value<User>> signin(@Field("xkey") String xkey,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("token") String token);

    @FormUrlEncoded
    @POST("signup.php")
    Call<ValueAdd> signup(@Field("xkey") String xkey,
                          @Field("email") String email,
                          @Field("password") String password,
                          @Field("nama") String nama,
                          @Field("noktp") String noktp,
                          @Field("nohp") String nohp,
                          @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("ubahpass.php")
    Call<ValueAdd> ubahpass(@Field("xkey") String xkey,
                            @Field("email") String email,
                            @Field("oldpass") String oldpass,
                            @Field("newpass") String newpass);

    @FormUrlEncoded
    @POST("updateprofil.php")
    Call<Value<User>> updateprofil(@Field("xkey") String xkey,
                                   @Field("email") String email,
                                   @Field("nama") String nama,
                                   @Field("noktp") String noktp,
                                   @Field("nohp") String nohp,
                                   @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("updateprofilimage.php")
    Call<Value<User>> updateprofilimage(@Field("xkey") String xkey,
                                        @Field("id") String id,
                                        @Field("oldfoto") String oldfoto,
                                        @Field("foto") String foto);

    @FormUrlEncoded
    @POST("getopd.php")
    Call<Value<Opd>> getopd(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getevent.php")
    Call<Value<Event>> getevent(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getcontent.php")
    Call<Value<Content>> getContent(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getkontak.php")
    Call<Value<Kontak>> getKontak(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getkategoriberita.php")
    Call<Value<KategoriBerita>> getKategoriBerita(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getberita.php")
    Call<Value<Berita>> getBerita(@Field("xkey") String xkey);

    @FormUrlEncoded
    @POST("getisiberita.php")
    Call<Value<Berita>> getIsiBerita(@Field("xkey") String xkey,
                                     @Field("idberita") String idberita);

    @FormUrlEncoded
    @POST("getberitaofkategori.php")
    Call<Value<Berita>> getBeritaOfKategori(@Field("xkey") String xkey,
                                            @Field("idkategori") String idkategori);

    @FormUrlEncoded
    @POST("getKategoripariwisata.php")
    Call<Value<Pariwisata>> getKategoripariwisata(@Field("xkey") String xkey,
                                                  @Field("idkategori") String idkategori,
                                                  @Field("jumlahtempat") String jumlahtempat);

    @FormUrlEncoded
    @POST("kirimlaporan.php")
    Call<ValueAdd> kirimlaporan(@Field("xkey") String xkey,
                                @Field("iduser") String iduser,
                                @Field("idopd") String idopd,
                                @Field("judul") String judul,
                                @Field("isi") String isi,
                                @Field("nohp") String nohp,
                                @Field("lokasi") String lokasi,
                                @Field("foto") String foto);

    @FormUrlEncoded
    @POST("getlaporan.php")
    Call<Value<Laporan>> getlaporan(@Field("xkey") String xkey,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("getpariwisata.php")
    Call<Value<DetailPariwisata>> getpariwisata(@Field("xkey") String xkey,
                                                @Field("idpariwisata") String idpariwisata);

    @FormUrlEncoded
    @POST("gettempatpariwisata.php")
    Call<Value<TempatPariwisata>> gettempatpariwisata(@Field("xkey") String xkey,
                                                      @Field("idpariwisata") String idpariwisata);

    @FormUrlEncoded
    @POST("cekime.php")
    Call<ValueAdd> cekime(@Field("xkey") String xkey,
                          @Field("ime") String ime,
                          @Field("idberita") String idberita);

    @FormUrlEncoded
    @POST("ceklike.php")
    Call<ValueAdd> ceklike(@Field("xkey") String xkey,
                           @Field("iduser") String iduser,
                           @Field("idberita") String idberita);

    @FormUrlEncoded
    @POST("setdataview.php")
    Call<ValueAdd> setDataView(@Field("xkey") String xkey,
                               @Field("ime") String ime,
                               @Field("idberita") String idberita);

    @FormUrlEncoded
    @POST("setdatalike.php")
    Call<ValueAdd> setDataLike(@Field("xkey") String xkey,
                               @Field("iduser") String iduser,
                               @Field("idberita") String idberita,
                               @Field("like") String like);

    @FormUrlEncoded
    @POST("logout.php")
    Call<ValueAdd> signout(@Field("xkey") String xkey,
                           @Field("email") String email);

    @FormUrlEncoded
    @POST("setlogin.php")
    Call<ValueAdd> setlogindb(@Field("xkey") String xkey,
                              @Field("email") String email);

    @FormUrlEncoded
    @POST("ceknik.php")
    Call<ValueAdd> ceknik(@Field("xkey") String xkey,
                          @Field("nik") String nik);

    @FormUrlEncoded
    @POST("setperangkat.php")
    Call<ValueAdd> setperangkat(@Field("xkey") String xkey,
                                @Field("perangkat") String perangkat,
                                @Field("token") String token);

    @FormUrlEncoded
    @POST("getberitanotif.php")
    Call<Value<Berita>> getBeritanotif(@Field("xkey") String xkey,
                                       @Field("idberita") String idberita);
}