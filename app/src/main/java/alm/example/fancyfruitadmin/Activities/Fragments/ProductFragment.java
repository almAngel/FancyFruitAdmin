package alm.example.fancyfruitadmin.Activities.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

import alm.example.fancyfruitadmin.Activities.AddProductActivity;
import alm.example.fancyfruitadmin.Activities.CustomViews.ItemAdapter;
import alm.example.fancyfruitadmin.Activities.LoginActivity;
import alm.example.fancyfruitadmin.Activities.MainActivity;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.MainActivityBinding;
import alm.example.fancyfruitadmin.databinding.MainFragmentBinding;
import alm.example.fancyfruitadmin.databinding.ProductFragmentBinding;

public class ProductFragment extends BaseFragment {

    MainActivityBinding parentBinding;
    ProductFragmentBinding binding;

    private ItemAdapter<Product> productItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> productCollection;
    private ProductProvider productProvider;
    private TagProvider tagProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductFragmentBinding.inflate(inflater, container, false);

        setEventListeners();

        refreshEverything();

        binding.itemList.setLayoutManager(layoutManager);
        binding.itemList.setAdapter(productItemAdapter);

        binding.swiper.setRefreshing(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onInitializeVariables() {
        productItemAdapter = new ItemAdapter<>(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        productProvider = new ProductProvider(getContext());
        tagProvider = new TagProvider(getContext());

    }

    @Override
    protected void onViewBind() {

    }

    @Override
    protected void onServiceInit() {

    }

    private void setEventListeners() {
        onSwipeDown();
        onEfabClick();
        setOnFilterButton();
    }

    @Override
    public void onResume() {
        refreshEverything();
        super.onResume();
    }

    private void onSwipeDown() {
        // CUANDO SE HACE SWIPE HACIA ABAJO EN EL RECYCLER VIEW
        binding.swiper.setOnRefreshListener(
                () -> {
                    // NUEVO THREAD PARA DELEGAR TRABAJO
                    Executors.newFixedThreadPool(1).execute(() -> {
                        productCollection = new ArrayList<>(Arrays.asList(productProvider.getProducts()));

                        // UNA VEZ SE RECARGUE LA COLECCION, EJECUTAR EN EL HILO PRINCIPAL
                        getActivity().runOnUiThread(() -> {
                            productItemAdapter.setCollection(productCollection);
                            binding.swiper.setRefreshing(false);
                        });
                    });

                }
        );

    }

    private void onEfabClick() {
        binding.efabAddProduct.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), AddProductActivity.class);
            startActivity(i);
        });
    }

    private void refreshEverything() {
        binding.swiper.setRefreshing(true);
        Executors.newFixedThreadPool(1).execute(() -> {
            productCollection = new ArrayList<>(
                    Arrays.asList(productProvider.getProducts())
            );

            getActivity().runOnUiThread(() -> {
                productItemAdapter.setCollection(productCollection);
                binding.swiper.setRefreshing(false);
            });
        });
    }

    private void setOnFilterButton() {

        MaterialToolbar topBar = getActivity().findViewById(R.id.topBar);

        topBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.filter) {
                makeMultiSelectDialog();
            }

            return false;
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

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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
            Tag[] selectedTags = new Tag[selectedTagsIndexes.size()];

            for (int i = 0; i < selectedTagsIndexes.size(); i++) {
                selectedTags[i] = tags[selectedTagsIndexes.get(i)];
            }

            Product[] products = productProvider.getProductsByTags(selectedTags);

            productItemAdapter.setCollection(
                    new ArrayList<>(Arrays.asList(products))
            );
        });

        mBuilder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

}
