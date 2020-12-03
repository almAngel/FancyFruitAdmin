package alm.example.fancyfruitadmin.Activities.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

import alm.example.fancyfruitadmin.Activities.CustomViews.ItemAdapter;
import alm.example.fancyfruitadmin.Activities.LoginActivity;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.ProductFragmentBinding;

public class ProductFragment extends BaseFragment {

    ProductFragmentBinding binding;

    private ItemAdapter<Product> productItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> productCollection;
    private ProductProvider productProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductFragmentBinding.inflate(inflater, container, false);

        setEventListeners();

        Executors.newFixedThreadPool(1).execute(() -> {
            productCollection = new ArrayList<>(
                    Arrays.asList(productProvider.getProducts())
            );

            getActivity().runOnUiThread(() -> productItemAdapter.setCollection(productCollection));
        });

        binding.itemList.setLayoutManager(layoutManager);
        binding.itemList.setAdapter(productItemAdapter);

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

    }

    @Override
    protected void onViewBind() {

    }

    @Override
    protected void onServiceInit() {

    }

    private void setEventListeners() {
        onSwipeDown();
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


}
