package alm.example.fancyfruitadmin.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onViewBind();
        onInitializeVariables();
        onServiceInit();

    }

    protected abstract void onViewBind();
    protected abstract void onServiceInit();
    protected abstract void onInitializeVariables();
}