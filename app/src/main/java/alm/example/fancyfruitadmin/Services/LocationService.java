package alm.example.fancyfruitadmin.Services;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import alm.example.fancyfruitadmin.Activities.MainActivity;
import alm.example.fancyfruitadmin.Pojos.StateLog;
import alm.example.fancyfruitadmin.Providers.StateLogProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.Utils.Listeners.BatteryChangesListener;
import alm.example.fancyfruitadmin.Utils.Listeners.BatteryChangesNotifier;
import alm.example.fancyfruitadmin.Utils.Listeners.ConnectivityChangesListener;
import alm.example.fancyfruitadmin.Utils.Listeners.ConnectivityChangesNotifier;
import alm.example.fancyfruitadmin.Utils.Listeners.CustomLocationListener;
import alm.example.fancyfruitadmin.Utils.Point;

public final class LocationService extends Service implements BatteryChangesListener, ConnectivityChangesListener {

    public static final String CHANNEL_ID = "#0001N";

    private static final float[] TIME_INTERVALS = {60000 * 10, 60000 * 15, 60000 * 20}; // MINS TO MILLISECS (60000ms == 1min)
    private static long MIN_TIME_UPDATE = (long) TIME_INTERVALS[0];
    private static final long MIN_DISTANCE_UPDATE = 0; //METERS

    private static final Boolean spoofingIsEnabled = false;

    private LocationManager locationManager;
    private CustomLocationListener locationListener;
    private Point currentLocation;
    private Intent sendToSubscribers;
    private Double lastKnownDistance = Double.MAX_VALUE;
    private Integer currentBatteryPercentage = 100;
    private Boolean hasConnectivity = false;
    private FusedLocationProviderClient fusedLocationClient;
    private final Class<? extends Activity> activityClass = MainActivity.class;
    private StateLog log;
    private StateLogProvider stateLogProvider;
    private LocationCallback locationCallback;

    private static final String TAG = LocationService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "LOCATION SERVICE INITIALIZED");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new CustomLocationListener();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        BatteryChangesNotifier.addListener(this);
        ConnectivityChangesNotifier.addListener(this);
        currentLocation = new Point(0d, 0d);

        stateLogProvider = new StateLogProvider(this);

        refreshLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        /*
         *   EXPLICITLY DECLARE AN ACTION IN THIS CONTEXT TO BE EXECUTED IN ANOTHER
         */
        Intent explicitActivityIntent = new Intent(this, activityClass);

        // TOKEN
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, explicitActivityIntent, 0);

        // NOTIFICATION
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Tropical Fancy Fruit")
                .setContentText("La aplicación está funcionando")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location foreground service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        stopSelf();
        super.onDestroy();
    }

    private void refreshLocation() {

        // CHECK FOR PERMISSION, THEN UPDATE IF NECESSARY
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (!spoofingIsEnabled && locationListener.usingMockProvider()) {
                try {
                    Log.d(TAG, "Removing Test providers");
                    locationManager.removeTestProvider(LocationManager.GPS_PROVIDER);
                } catch (SecurityException ignored) {
                } catch (IllegalArgumentException iae) {
                    Log.d(TAG, "Got exception in removing test  provider");
                }
            } else {

                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setInterval((long) TIME_INTERVALS[0]);
                locationRequest.setFastestInterval(MIN_TIME_UPDATE);
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

                fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                if (locationResult == null) {
                                    return;
                                }
                                for (Location location : locationResult.getLocations()) {
                                    currentLocation = new Point(location.getLatitude(), location.getLongitude());

                                    prepareLog();
                                }
                            }
                        },
                        Looper.getMainLooper()
                );
            }

        }
    }

    private void prepareLog() {

        log = new StateLog(
                Build.MODEL,
                Helper.getCredentials(this)[2], // USER UUID
                currentBatteryPercentage,
                currentLocation.getLat() + ", " + currentLocation.getLon(),
                String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
        );

        stateLogProvider.addNewLog(log);
    }

    private void sendToSubscribingActivities() {
        if (currentLocation != null) {

            sendToSubscribers.putExtra("DISTANCE_FROM_STORE", lastKnownDistance);
            sendToSubscribers.putExtra("USER_POSITION", currentLocation);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendToSubscribers);
        }
    }

    @Override
    public void onBatteryChanges(int percentage) {
        currentBatteryPercentage = percentage;
        // REMOVE onCreate() LISTENER SO WE CAN SWAP TIME INTERVALS
        if(locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }

        // CHANGE LOCATION REFRESH INTERVAL DEPENDING ON BATTERY PERCENTAGE
        if (percentage < 60 && percentage > 30) {
            MIN_TIME_UPDATE = (long) TIME_INTERVALS[1];
        } else if (percentage < 30) {
            MIN_TIME_UPDATE = (long) TIME_INTERVALS[2];
        } else {
            MIN_TIME_UPDATE = (long) TIME_INTERVALS[0];
        }

        Log.e(TAG, "onBatteryChanges -> MIN_TIME_UPDATE: " + MIN_TIME_UPDATE / 60000 + "min");

        refreshLocation();
    }

    @Override
    public void onConnectivityChanges(boolean hasConnectivity) {
        this.hasConnectivity = hasConnectivity;

        if (hasConnectivity) {
            Log.e(TAG, "SE HA RESTABLECIDO LA CONEXION DEL TERMINAL");
            stateLogProvider.addNewLog(log);
        } else {
            Log.e(TAG, "SE HA PERDIDO LA CONEXION DEL TERMINAL");
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}