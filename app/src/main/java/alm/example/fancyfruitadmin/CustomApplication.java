package alm.example.fancyfruitadmin;

import android.app.Application;

import alm.example.fancyfruitadmin.Utils.Listeners.BatteryChangesNotifier;
import alm.example.fancyfruitadmin.Utils.Listeners.ConnectivityChangesNotifier;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BatteryChangesNotifier.checkChanges(this);
    }
}
