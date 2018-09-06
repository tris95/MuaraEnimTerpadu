package id.go.muaraenimkab.bappeda.muaraenimterpadu.services;


import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Ad;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
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
    @POST("getberita.php")
    Call<Value<Berita>> getBerita(@Field("xkey") String xkey);

}