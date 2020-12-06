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

import alm.example.fancyfruitadmin.Pojos.ApiResponse;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.EditproductActivityBinding;

public class EditProductActivity extends BaseActivity {

    private EditproductActivityBinding binding;

    private ProductProvider productProvider;
    private TagProvider tagProvider;

    private Product currentProduct;
    private String productUuid;

    private ArrayList<String> inputValues = new ArrayList<String>() {
        {
            add(""); // Nombre
            add(""); // Slug
            add(""); // Referencia
        }
    };

    private Tag[] productTags = new Tag[0];

    private static final String TAG = EditProductActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productUuid = getIntent().getStringExtra("UUID");

        currentProduct = productProvider.getProductById(productUuid);

        Log.e(TAG, "onCreate: " + currentProduct.toString() );
        inputValues.set(0, currentProduct.getName());
        inputValues.set(1, currentProduct.getSlug());
        inputValues.set(2, currentProduct.getRef());
        productTags = currentProduct.getTags();

        setEventListeners();

    }

    @Override
    protected void onViewBind() {
        binding = EditproductActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.productNameInput.setText(inputValues.get(0));
        binding.productNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.productNameInput.hasFocus()) {
                    inputValues.set(0, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.productSlugInput.setText(inputValues.get(1));
        binding.productSlugInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.productSlugInput.hasFocus()) {
                    inputValues.set(1, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.productRefInput.setText(inputValues.get(2));
        binding.productRefInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.productRefInput.hasFocus()) {
                    inputValues.set(2, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void onSubmitProduct() {
        binding.buttonAddProduct.setOnClickListener(v -> {

            ApiResponse response = productProvider.updateProduct(
                    productUuid,
                    new Product(
                            inputValues.get(2),
                            inputValues.get(0),
                            inputValues.get(1),
                            productTags
                    )
            );

            if (response.getCode() == 200) {
                Snackbar.make(v, "Producto actualizado con éxito. Refresca para ver los cambios", BaseTransientBottomBar.LENGTH_SHORT).show();
                Log.e(TAG, "onSubmitProduct: Actualizado con exito");
            } else {
                Snackbar.make(v, "El producto no se ha podido actualizar porque faltan datos", BaseTransientBottomBar.LENGTH_SHORT).show();
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

        for (int i = 0; i < productTags.length; i++) {
            int index = Arrays.asList(listItems).indexOf(productTags[i].getName());
            checkedItems[index] = true;
            selectedTagsIndexes.add(index);
        }

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
