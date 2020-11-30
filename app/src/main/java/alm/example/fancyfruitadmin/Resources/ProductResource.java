package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface ProductResource {

    @GET("/product")
    Call<Product[]> getProducts();

}
