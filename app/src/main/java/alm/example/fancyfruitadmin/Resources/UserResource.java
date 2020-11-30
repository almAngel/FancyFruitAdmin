package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserResource {

    @GET("/user/email/{email}")
    Call<User> getByEmail(@Path("email") String email);
}
