package alm.example.fancyfruitadmin.Utils.Listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.util.ArrayList;
import java.util.List;

public final class BatteryChangesNotifier {
    private static List<BatteryChangesListener> listeners = new ArrayList<>();

    public static void addListener(BatteryChangesListener listener) {
        listeners.add(listener);
    }

    public static void checkChanges(Context appContext) {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        appContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int newPercentage = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

                for(BatteryChangesListener l: listeners) {
                    l.onBatteryChanges(newPercentage);
                }
            }
        }, ifilter);

    }

}