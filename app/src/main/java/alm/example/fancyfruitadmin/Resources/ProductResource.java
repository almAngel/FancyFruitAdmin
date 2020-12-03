package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductResource {

    @GET("/product")
    Call<Product[]> getAll();

    @POST("/product")
    Call<ApiResponse> add(@Body Product product);

    @DELETE("/product/{uuid}")
    Call<ApiResponse> delete(@Path("uuid") String uuid);

}
