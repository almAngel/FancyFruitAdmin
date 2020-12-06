package alm.example.fancyfruitadmin.Utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.function.Function;

import alm.example.fancyfruitadmin.Activities.LoginActivity;
import alm.example.fancyfruitadmin.R;

public class Helper {

    public static void storeCredentials(Context context, String uuid, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
        sharedPreferences.edit().putString("uuid", uuid).apply();
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
        sharedPreferences.edit().remove("uuid").apply();
        sharedPreferences.edit().remove("password").apply();
    }

    public static String[] getCredentials(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        return new String[]{
                sharedPreferences.getString("username", ""),
                sharedPreferences.getString("password", ""),
                sharedPreferences.getString("uuid", "")
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

    public static boolean getLocationPermission(Context context) {
        boolean result = true;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false;
        }
        return result;
    }

    public static String[] getPermissions(Context context){
        ArrayList<String> list=new ArrayList<>();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        String[] strings = new String[list.size()];
        strings = list.toArray(strings);
        return strings;
    }

    public static boolean checkInternet(Context context) {
        try {
            ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return wifi.isConnected() || mobile.isConnected();
        } catch (Exception e){
            return false;
        }
    }
}
