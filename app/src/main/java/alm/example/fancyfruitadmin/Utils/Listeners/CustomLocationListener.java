package alm.example.fancyfruitadmin.Utils.Listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;


import alm.example.fancyfruitadmin.Utils.Point;

public class CustomLocationListener implements LocationListener {

    private boolean isFromMockProvider = false;
    private boolean hasConnectivity = true;
    private ObservableField<Point> point = new ObservableField<>();

    public CustomLocationListener() {

    }

    public ObservableField<Point> getPoint() {
        return point;
    }

    public void setPoint(ObservableField<Point> point) {
        this.point = point;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Point coords = new Point(
                location.getLatitude(),
                location.getLongitude()
        );

        isFromMockProvider = location.isFromMockProvider();

        point.set(coords);
        point.notifyChange();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public boolean usingMockProvider() {
        return isFromMockProvider;
    }

}