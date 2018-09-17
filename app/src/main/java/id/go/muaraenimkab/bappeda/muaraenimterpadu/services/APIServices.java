package id.go.muaraenimkab.bappeda.muaraenimterpadu.services;


import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Ad;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
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


}