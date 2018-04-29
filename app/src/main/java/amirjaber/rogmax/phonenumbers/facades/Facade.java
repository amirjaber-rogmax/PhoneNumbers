package amirjaber.rogmax.phonenumbers.facades;

import amirjaber.rogmax.phonenumbers.models.Model;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Facade {

    @FormUrlEncoded
    @POST("insert.php")
    Call<Model> insert(@Field("country") String country,
                       @Field("service") String service,
                       @Field("number") String number);

    @GET("view.php")
    Call<Model> view();
}
