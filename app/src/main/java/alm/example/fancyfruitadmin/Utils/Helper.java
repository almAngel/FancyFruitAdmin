package alm.example.fancyfruitadmin.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Function;

import alm.example.fancyfruitadmin.Activities.LoginActivity;
import alm.example.fancyfruitadmin.R;

public class Helper {

    public static void storeCredentials(Context context, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
        sharedPreferences.edit().putString("password", password).apply();
    }

    public static void sessionGuard(FragmentActivity current, Class<? extends AppCompatActivity> redirect) {
        Arrays.asList(Helper.getCredentials(current)).forEach(c -> {
            if (c.equalsIgnoreCase("")) {
                Intent i = new Intent(current, redirect);
                current.startActivity(i);
                current.finish();
            }
        });
    }

    public static void logOut(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("username").apply();
        sharedPreferences.edit().remove("password").apply();
    }

    public static String[] getCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        return new String[]{
                sharedPreferences.getString("username", ""),
                sharedPreferences.getString("password", "")
        };
    }

    public static void showMessageAlert(String title, String text, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = null;
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setCancelable(false);

        builder.setPositiveButton("Aceptar", (dialog, id) -> {
            dialog.dismiss();
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showMessageAlert(String title, String text, Context context, boolean cancelable, Callable<Void> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = null;
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setCancelable(cancelable);

        builder.setPositiveButton("Aceptar", (dialog, id) -> {
            dialog.dismiss();
            try {
                callback.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showInputAlert(String title, Context context, ViewGroup viewGroup, Function<String, Void> callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.input_alert, viewGroup, false);
        final EditText input = viewInflated.findViewById(R.id.innerInput);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            try {
                callback.apply(input.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public static AlertDialog.Builder loadingAlertBuilder(String title, Context context, ViewGroup viewGroup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.loading_alert, viewGroup, false);
        //final EditText input = (EditText) viewInflated.findViewById(R.id.innerInput);

        builder.setView(viewInflated);

        return builder;
    }

}
