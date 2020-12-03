package alm.example.fancyfruitadmin.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.databinding.AdditemActivityBinding;

public class AddItemActivity extends BaseActivity {

    AdditemActivityBinding binding;

    ProductProvider productProvider;
    TagProvider tagProvider;

    Bundle b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewBind() {
        binding = AdditemActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onInitializeVariables() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
