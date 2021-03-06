package alm.example.fancyfruitadmin.Activities.Fragments;

import android.content.Intent;
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

import alm.example.fancyfruitadmin.Activities.AddProductActivity;
import alm.example.fancyfruitadmin.Activities.AddTagActivity;
import alm.example.fancyfruitadmin.Activities.CustomViews.ItemAdapter;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.databinding.MainActivityBinding;
import alm.example.fancyfruitadmin.databinding.TagFragmentBinding;

public class TagFragment extends BaseFragment {

    MainActivityBinding parentBinding;
    TagFragmentBinding binding;

    private ItemAdapter<Tag> tagItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Tag> tagCollection;
    private TagProvider tagProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TagFragmentBinding.inflate(inflater, container, false);

        setEventListeners();

        refreshEverything();

        binding.itemList.setLayoutManager(layoutManager);
        binding.itemList.setAdapter(tagItemAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {

        refreshEverything();
        super.onResume();
    }

    @Override
    protected void onInitializeVariables() {
        tagItemAdapter = new ItemAdapter<>(getContext());
        layoutManager = new LinearLayoutManager(getContext());
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
    }

    private void onSwipeDown() {
        // CUANDO SE HACE SWIPE HACIA ABAJO EN EL RECYCLER VIEW
        binding.swiper.setOnRefreshListener(
                () -> {
                    // NUEVO THREAD PARA DELEGAR TRABAJO
                    Executors.newFixedThreadPool(1).execute(() -> {
                        tagCollection = new ArrayList<>(Arrays.asList(tagProvider.getTags()));

                        // UNA VEZ SE RECARGUE LA COLECCION, EJECUTAR EN EL HILO PRINCIPAL
                        getActivity().runOnUiThread(() -> {
                            tagItemAdapter.setCollection(tagCollection);
                            binding.swiper.setRefreshing(false);
                        });
                    });

                }
        );

    }

    private void onEfabClick() {
        binding.efabAddTag.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), AddTagActivity.class);
            startActivity(i);
        });
    }

    private void refreshEverything() {
        binding.swiper.setRefreshing(true);
        Executors.newFixedThreadPool(2).execute(() -> {
            tagCollection = new ArrayList<>(
                    Arrays.asList(tagProvider.getTags())
            );

            getActivity().runOnUiThread(() -> {
                tagItemAdapter.setCollection(tagCollection);

                binding.swiper.setRefreshing(false);
            });
        });
    }

}
