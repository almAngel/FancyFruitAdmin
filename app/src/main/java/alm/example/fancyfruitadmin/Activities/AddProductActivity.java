package alm.example.fancyfruitadmin.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.AddproductActivityBinding;

public class AddProductActivity extends BaseActivity {

    private AddproductActivityBinding binding;

    private ProductProvider productProvider;
    private TagProvider tagProvider;

    private ArrayList<String> inputValues = new ArrayList<String>(){
        {
            add("");
            add("");
            add("");
            add("");
        }
    };

    private Tag[] productTags = new Tag[0];

    private static final String TAG = AddProductActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onViewBind() {
        binding = AddproductActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setEventListeners();
    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onInitializeVariables() {
        productProvider = new ProductProvider(this);
        tagProvider = new TagProvider(this);
    }

    private void setEventListeners() {
        onBackButtonClick();
        onInputTextListener();
        onSubmitProduct();
        onAddTagsButton();
    }

    private void onBackButtonClick() {
        binding.topBar.setNavigationOnClickListener(v -> {
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
        });
    }

    private void onInputTextListener() {
        binding.productNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValues.set(0, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.productSlugInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValues.set(1, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.productRefInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValues.set(2, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.productQuantityInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputValues.set(3, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onSubmitProduct() {
        binding.buttonAddProduct.setOnClickListener(v -> {

            Product response = productProvider.addProduct(
                    new Product(
                            inputValues.get(2),
                            inputValues.get(0),
                            inputValues.get(1),
                            Integer.parseInt(inputValues.get(3)),
                            productTags
                    )
            );

            if(response.getName() != null) {
                Snackbar.make(v, "Producto creado con éxito. Refresca para ver los cambios", BaseTransientBottomBar.LENGTH_SHORT).show();
                Log.e(TAG, "onSubmitProduct: Creado con exito" );
            } else {
                Snackbar.make(v, "El producto no se ha podido crear porque faltan datos", BaseTransientBottomBar.LENGTH_SHORT).show();
            }

        });
    }

    private void onAddTagsButton() {
        binding.buttonAddProducTags.setOnClickListener(v -> {
            makeMultiSelectDialog();
        });

    }

    private void makeMultiSelectDialog() {
        boolean[] checkedItems;
        ArrayList<Integer> selectedTagsIndexes = new ArrayList<>();

        Tag[] tags = tagProvider.getTags();

        CharSequence[] listItems = new CharSequence[tags.length];

        for (int i = 0; i < tags.length; i++) {
            listItems[i] = tags[i].getName();
        }

        checkedItems = new boolean[listItems.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Selecciona las etiquetas");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {

                if (isChecked) {
                    selectedTagsIndexes.add(position);
                } else {
                    selectedTagsIndexes.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Seleccionar", (dialogInterface, which) -> {
            productTags = new Tag[selectedTagsIndexes.size()];

            for (int i = 0; i < selectedTagsIndexes.size(); i++) {
                productTags[i] = tags[selectedTagsIndexes.get(i)];
            }

        });

        mBuilder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

}
