package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Resources.ProductResource;
import alm.example.fancyfruitadmin.Utils.Await;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.JsonLogger;
import alm.example.fancyfruitadmin.Utils.Resource;
import retrofit2.Response;

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

    public Product getProductById(String uuid) {
        return new Await<Product>().get(() -> {
            Product finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .getById(uuid)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getProductById");

            // FINALLY
            return finalResponse;
        });
    }

    public Product[] getProductsByTags(Tag[] tags) {
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
                    .getByTags(tags)
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getProductsByTags");

            // FINALLY
            return finalResponse;
        });
    }

    public Product addProduct(Product product) {
        return new Await<Product>().get(() -> {
            Response response;
            Product finalResponse = null;

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

            // FINALLY
            if(finalResponse != null) {
                return finalResponse;
            }

            return null;
        });
    }

    public ApiResponse updateProduct(String uuid, Product product) {
        return new Await<ApiResponse>().get(() -> {
            Response response;
            ApiResponse finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            product.setUuid("");

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO
            response = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .update(uuid, product)
                    .execute();

            // LOG
            logger.log(finalResponse, "updateProduct");

            // FINALLY
            if(response.body() != null) {

                finalResponse = new ApiResponse(
                        "Row updated correctly",
                        200
                );

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
