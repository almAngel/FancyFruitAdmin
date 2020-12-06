package alm.example.fancyfruitadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.AddtagActivityBinding;

public class AddTagActivity extends BaseActivity {

    AddtagActivityBinding binding;

    TagProvider tagProvider;

    String inputValue = "";

    private static final String TAG = AddProductActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onViewBind() {
        binding = AddtagActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEventListeners();
    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onInitializeVariables() {
        tagProvider = new TagProvider(this);
    }

    private void setEventListeners() {
        onInputTextListener();
        onSubmitTag();
        onBackButtonClick();
    }

    private void onBackButtonClick() {
        binding.topBar.setNavigationOnClickListener(v -> {
            if (inputValue.equals("")) {
                super.onBackPressed();
            } else {
                Helper.showMessageAlert(
                        "Volver",
                        "¿Está seguro de que quiere volver a la actividad anterior?\n\nSe perderán los datos no enviados",
                        this,
                        true,
                        () -> {
                            super.onBackPressed();
                            return null;
                        }
                );
            }
        });
    }

    private void onInputTextListener() {
        binding.tagNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValue = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onSubmitTag() {
        binding.buttonAddTag.setOnClickListener(v -> {

            ApiResponse response = tagProvider.addTag(
                    new Tag(inputValue)
            );

            if(response.getCode() == 201) {
                Snackbar.make(v, "Etiqueta creada con éxito. Refresca para ver los cambios", BaseTransientBottomBar.LENGTH_SHORT).show();
                Log.e(TAG, "onSubmitTag: Creado con exito" );
            } else {
                Snackbar.make(v, "La etiqueta no se ha podido crear porque faltan datos", BaseTransientBottomBar.LENGTH_SHORT).show();
            }

        });
    }
}

