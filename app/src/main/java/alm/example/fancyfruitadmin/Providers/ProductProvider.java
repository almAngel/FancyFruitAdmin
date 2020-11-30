package alm.example.fancyfruitadmin.Providers;

import android.content.Context;
import android.content.SharedPreferences;

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


    public ProductProvider(Context context) {
        this.context = context;
        resource = new Resource<>(ProductResource.class);
        logger = new JsonLogger();
        sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
    }

    public Product[] getProducts() {
        return new Await<Product[]>().get(resource, () -> {
            Product[] finalResponse = null;

            String[] credentials = Helper.getCredentials(context);

            // HACEMOS LLAMADA A RECURSO Y DEVOLVEMOS EL ELEMENTO DESEADO (validacion_imeiResponse)
            finalResponse = resource
                    .get(
                            context,
                            credentials[0],
                            credentials[1]
                    )
                    .getProducts()
                    .execute()
                    .body();

            // LOG
            logger.log(finalResponse, "getProducts");

            // FINALLY
            return finalResponse;
        });
    }

}
