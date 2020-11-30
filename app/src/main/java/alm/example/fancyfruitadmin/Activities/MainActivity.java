package alm.example.fancyfruitadmin.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Providers.ProductProvider;

public class MainActivity extends BaseActivity {

    private ProductProvider productProvider;

    private static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Product[] products = productProvider.getProducts();
    }

    @Override
    protected void onViewBind() {

    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onInitializeVariables() {
        productProvider = new ProductProvider(this);
    }

}
