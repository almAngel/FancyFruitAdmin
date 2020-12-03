package alm.example.fancyfruitadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;

import alm.example.fancyfruitadmin.Activities.CustomViews.ItemAdapter;
import alm.example.fancyfruitadmin.Activities.Fragments.MainFragment;
import alm.example.fancyfruitadmin.Activities.Fragments.ProductFragment;
import alm.example.fancyfruitadmin.Activities.Fragments.TagFragment;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.MainActivityBinding;

public class MainActivity extends BaseActivity {

    private MainActivityBinding binding;

    // FRAGMENTS
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment;

    private MainFragment mainFragment;
    private ProductFragment productFragment;
    private TagFragment tagFragment;

    // SET UP DRAWER
    private DrawerLayout drawerLayout;
    private NavigationView drawerNavigationView;
    private View drawerHeader;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.sessionGuard(this, LoginActivity.class);

        selectActiveFragment();
        setEventListeners();

    }

    @Override
    protected void onViewBind() {
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setBarMenu();
    }

    @Override
    protected void onServiceInit() {

    }

    @Override
    protected void onInitializeVariables() {
        mainFragment = new MainFragment();
        productFragment = new ProductFragment();
        tagFragment = new TagFragment();

        drawerLayout = binding.drawerLayout.drawerLayout;
        drawerNavigationView = binding.drawerLayout.drawerNavigationView;
    }

    private void setEventListeners() {
        setBarMenu();
        setDrawerMenu();
        setNavigationMenu();
    }

    private void selectActiveFragment() {
        activeFragment = mainFragment;

        fragmentManager.beginTransaction().add(R.id.fragmentContainer, tagFragment, "3").hide(tagFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, productFragment, "2").hide(productFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, mainFragment, "1").commit();

        drawerNavigationView.setCheckedItem(R.id.nav_home);

    }

    private void setNavigationMenu() {

        drawerNavigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragmentManager.
                            beginTransaction().
                            replace(R.id.fragmentContainer, mainFragment, "1").
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            show(mainFragment).
                            commit();
                    activeFragment = mainFragment;
                    drawerLayout.closeDrawers();
                    return true;

                case R.id.nav_product_list:
                    fragmentManager.
                            beginTransaction().
                            replace(R.id.fragmentContainer, productFragment, "2").
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            show(productFragment).
                            commit();
                    activeFragment = productFragment;
                    drawerLayout.closeDrawers();
                    return true;

                case R.id.nav_tag_list:
                    fragmentManager.
                            beginTransaction().
                            replace(R.id.fragmentContainer, tagFragment, "3").
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            show(tagFragment).
                            commit();
                    activeFragment = tagFragment;
                    drawerLayout.closeDrawers();
                    return true;
            }

            return false;
        });
    }

    private void setBarMenu() {
        binding.topBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logout) {
                logOut();
            }
            return false;
        });

        binding.topBar.setNavigationOnClickListener(v -> drawerToggle());
    }

    private void setDrawerMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerNavigationView = drawerLayout.findViewById(R.id.drawer_navigationView);
        drawerHeader = drawerNavigationView.getHeaderView(0);
    }

    private void drawerToggle() {
        if (drawerLayout != null) {
            if (!drawerLayout.isDrawerOpen(drawerNavigationView)) {
                drawerLayout.openDrawer(drawerNavigationView);
            } else {
                drawerLayout.closeDrawer(drawerNavigationView);
            }
        }
    }

    private void logOut() {
        Helper.logOut(this);
        Helper.sessionGuard(this, LoginActivity.class);
    }

}
