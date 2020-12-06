package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Resources.ProductResource;
import alm.example.fancyfruitadmin.Resources.TagResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;

public class TagProvider implements BaseProvider {

    private final Context context;
    private final Resource<TagResource> resource;
    private final JsonLogger logger;
    private final SharedPreferences sharedPreferences;

    private static final String TAG = TagProvider.class.getSimpleName();
    
    public TagProvider(Context context) {
        this.context = context;
        resource = new Resource<>(TagResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    /**
     *  Funcion para obtener todas las etiquetas disponibles
     *
     * @return Tag[]
     */
    public Tag[] getTags() {
        return new Await<Tag[]>().get(() -> {
            Tag[] finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .getAll()
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getTags");

            // FINALLY
            return finalResponse;
        });
    }

    public ApiResponse addTag(Tag tag) {
        return new Await<ApiResponse>().get(() -> {
            ApiResponse finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .add(tag)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "addProduct");

            // FINALLY
            if(finalResponse != null) {
                return finalResponse;
            }

            return null;
        });
    }


    public boolean deleteTag(String uuid) {
        return new Await<Boolean>().get(() -> {
            ApiResponse finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .delete(uuid)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "deleteTag");

            // FINALLY
            if(finalResponse != null) {
                return finalResponse.getCode() == 200;
            }
            return false;
        });
    }
}
