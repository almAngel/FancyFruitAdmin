package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Resources.ProductResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;

public class ProductProvider implements BaseProvider {

    private final Context context;
    private final Resource<ProductResource> resource;
    private final JsonLogger logger;
    private final SharedPreferences sharedPreferences;

    private static final String TAG = ProductProvider.class.getSimpleName();
    
    public ProductProvider(Context context) {
        this.context = context;
        resource = new Resource<>(ProductResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    /**
     *  Funcion para obtener todos los productos disponibles
     *
     * @return Product[]
     */
    public Product[] getProducts() {
        return new Await<Product[]>().get(() -> {
            Product[] finalResponse = null;

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
            logger.log(finalResponse, "getProducts");

            // FINALLY
            return finalResponse;
        });
    }

    public ApiResponse addProduct(Product product) {
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
                    .add(product)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "addProduct");

            Log.e(TAG, finalResponse.toString());

            // FINALLY
            if(finalResponse != null) {
                return finalResponse;
            }

            return null;
        });
    }

    /**
     * Función para eliminar un producto desde la API
     *
     * @param uuid
     * @return boolean
     */
    public boolean deleteProduct(String uuid) {
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
            logger.log(finalResponse, "deleteProduct");

            Log.e(TAG, finalResponse.toString());

            // FINALLY
            if(finalResponse != null) {
                return finalResponse.getCode() == 200;
            }


            return false;
        });
    }

}
