package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.StateLog;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StateLogResource {

    @GET("/statelog/{id}")
    Call<StateLog[]> getAllByUser(@Path("id") String uuid);

    @POST("/statelog/statelog")
    Call<StateLog> add(@Body StateLog log);
}
