package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;

import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.User;
import alm.example.fancyfruitadmin.Resources.ProductResource;
import alm.example.fancyfruitadmin.Resources.UserResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;

public class UserProvider implements BaseProvider {

    private final Context context;
    private final Resource<UserResource> resource;
    private final JsonLogger logger;
    private final SharedPreferences sharedPreferences;


    public UserProvider(Context context) {
        this.context = context;
        resource = new Resource<>(UserResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public User getByEmail(String email) {
        return new Await<User>().get(() -> {
            User finalResponse = null;

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO (validacion_imeiResponse)
            finalResponse = resource
                    .get(
                            context
                    )
                    .getByEmail(email)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getByEmail");

            // FINALLY
            return finalResponse;
        });
    }
}
