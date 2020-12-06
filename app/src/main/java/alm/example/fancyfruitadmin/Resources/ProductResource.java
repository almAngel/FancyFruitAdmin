package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductResource {

    @GET("/product/all")
    Call<Product[]> getAll();

    @GET("/product/{id}")
    Call<Product> getById(@Path("id") String uuid);

    @POST("/product")
    Call<Product> add(@Body Product product);

    @POST("/product/tag")
    Call<Product[]> getByTags(@Body Tag[] tags);

    @PUT("/product/{id}")
    Call<ApiResponse> update(@Path("id") String id, @Body Product tags);

    @DELETE("/product/{uuid}")
    Call<ApiResponse> delete(@Path("uuid") String uuid);

}
