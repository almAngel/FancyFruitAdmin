package alm.example.fancyfruitadmin.Resources;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.ApiResponseBadRequest;
import alm.example.fancyfruitadmin.Pojos.PassVerification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthResource {

    @POST("/auth/password")
    Call<ApiResponse> validate(@Body PassVerification passVerification);

}
