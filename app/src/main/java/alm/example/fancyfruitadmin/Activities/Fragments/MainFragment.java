package alm.example.fancyfruitadmin.Activities.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import alm.example.fancyfruitadmin.Activities.LoginActivity;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.MainActivityBinding;
import alm.example.fancyfruitadmin.databinding.MainFragmentBinding;

public class MainFragment extends BaseFragment {

    MainActivityBinding parentBinding;
    MainFragmentBinding binding;

    private ProductFragment productFragment;
    private TagFragment tagFragment;
    
    private static final String TAG = MainFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);

        setEventListeners();

        return binding.getRoot();
    }

    @Override
    protected void onInitializeVariables() {
        productFragment = new ProductFragment();
        tagFragment = new TagFragment();
    }

    @Override
    protected void onViewBind() {

    }

    @Override
    protected void onServiceInit() {

    }

    private void setEventListeners() {
        onExitButton();
        onProductListButton();
        onTagListButton();
    }

    private void onExitButton() {
        binding.panelExit.setOnClickListener(v -> {
            Helper.logOut(getContext());
            Helper.sessionGuard(getActivity(), LoginActivity.class);
        });
    }

    private void onProductListButton() {
        binding.panelProductList.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, productFragment, "2").
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    show(productFragment).
                    commit();

            MaterialToolbar appBar = getActivity().findViewById(R.id.topBar);
            appBar.getMenu().clear();
            appBar.inflateMenu(R.menu.topbar_productmenu);
        });

    }

    private void onTagListButton() {
        binding.panelTagList.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentContainer, tagFragment, "3").
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    show(tagFragment).
                    commit();

            MaterialToolbar appBar = getActivity().findViewById(R.id.topBar);
            appBar.getMenu().clear();
            appBar.inflateMenu(R.menu.topbar_menu);

        });
    }
}
