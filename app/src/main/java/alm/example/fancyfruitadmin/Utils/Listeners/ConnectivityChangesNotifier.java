package alm.example.fancyfruitadmin.Utils.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

public class ConnectivityChangesNotifier {

    private static List<ConnectivityChangesListener> listeners = new ArrayList<>();

    public static void addListener(ConnectivityChangesListener listener) {
        listeners.add(listener);
    }

    public static void checkChanges(Context appContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        appContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean hasConnectivity = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                for (ConnectivityChangesListener l: listeners) {
                    l.onConnectivityChanges(hasConnectivity);
                }
            }
        }, ifilter);

    }
}
