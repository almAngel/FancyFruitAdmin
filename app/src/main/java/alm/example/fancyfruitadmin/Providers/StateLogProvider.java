package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.ApiResponseBadRequest;
import alm.example.fancyfruitadmin.Pojos.PassVerification;
import alm.example.fancyfruitadmin.Pojos.StateLog;
import alm.example.fancyfruitadmin.Resources.AuthResource;
import alm.example.fancyfruitadmin.Resources.StateLogResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;
import retrofit2.Response;

public class StateLogProvider implements BaseProvider {

    private final Context context;
    private final Resource<StateLogResource> resource;
    private final JsonLogger logger;
    private final SharedPreferences sharedPreferences;

    private static final String TAG = AuthProvider.class.getSimpleName();

    public StateLogProvider(Context context) {
        this.context = context;
        resource = new Resource<>(StateLogResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public StateLog[] getAllByUser(String uuid) {
        return new Await<StateLog[]>().get(() -> {
            Response response;
            StateLog[] finalResponse = null;

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(context)
                    .getAllByUser(uuid)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getAllByUser");

            // FINALLY
            if(finalResponse != null) {
                return finalResponse;
            }

            // FINALLY
            return null;
        });
    }

    public StateLog addNewLog(StateLog log) {
        return new Await<StateLog>().get(() -> {
            Response response;
            StateLog finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .add(log)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "addNewLog");

            // FINALLY
            if(finalResponse != null) {
                return finalResponse;
            }

            // FINALLY
            return null;
        });
    }


}
