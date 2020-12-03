package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.ApiResponseBadRequest;
import alm.example.fancyfruitadmin.Pojos.PassVerification;
import alm.example.fancyfruitadmin.Resources.AuthResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;
import retrofit2.Response;

public class AuthProvider implements BaseProvider {

    private final Context context;
    private final Resource<AuthResource> resource;
    private final JsonLogger logger;
    private final SharedPreferences sharedPreferences;

    private static final String TAG = AuthProvider.class.getSimpleName();

    public AuthProvider(Context context) {
        this.context = context;
        resource = new Resource<>(AuthResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public boolean validatePassword(String pass, String hash) {
        return new Await<Boolean>().get(() -> {
            Response response;
            Serializable finalResponse = null;

            PassVerification pv = new PassVerification(pass, hash);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            response = resource
                    .get(context)
                    .validate(
                            pv
                    )
                    .execute();
            
            if(response.code() == 400) {
                ApiResponseBadRequest badRequest = new Gson().fromJson(response.errorBody().string(), ApiResponseBadRequest.class);
                // LOG
                logger.log(badRequest, "validatePassword");
            } else if (response.code() == 201) {
                ApiResponse isValid = (ApiResponse) response.body();

                // LOG
                logger.log(isValid, "validatePassword");

                return Boolean.parseBoolean(isValid.getMessage());
            }

            // FINALLY
            return false;
        });
    }

}
