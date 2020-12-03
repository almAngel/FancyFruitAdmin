package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TagResource {

    @GET("/tag")
    Call<Tag[]> getAll();

    @POST("/tag")
    Call<ApiResponse> add(@Body Tag tag);

    @DELETE("/tag/{uuid}")
    Call<ApiResponse> delete(@Path("uuid") String uuid);

}
