package alm.example.fancyfruitadmin.Utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Point implements Parcelable {
    private LatLng pos = null;

    public Point(Double lat, Double lon) {
        pos = new LatLng(lat, lon);
    }

    public Point(String p) {
        String[] parts = p.split(",");
        pos = new LatLng(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
    }

    protected Point(Parcel in) {
        pos = in.readParcelable(LatLng.class.getClassLoader());
    }

    public LatLng getWrapperInstace() {
        return pos;
    }

    public Double getLat() {
        return pos.latitude;
    }

    public Double getLon() {
        return pos.longitude;
    }

    private boolean isMeasuredInRads() {
        return getLat().intValue() == 0 &&
                getLon().intValue() == 0;
    }

    private boolean isMeasuredInDegs() {
        return getLat().intValue() != 0 &&
                getLon().intValue() != 0;
    }

    public Point convertToRads() {
        if (isMeasuredInDegs()) {
            pos = new LatLng((Math.PI / 180) * pos.latitude,
                    (Math.PI / 180) * pos.longitude);
        }
        return this;
    }

    public Point convertToDegs() {
        if (isMeasuredInRads()) {
            pos = new LatLng((180 / Math.PI) * pos.latitude,
                    (180 / Math.PI) * pos.longitude);
        }
        return this;
    }

    public LatLng getPos() {
        return pos;
    }

    public void setPos(LatLng pos) {
        this.pos = pos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[] { pos });
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    @Override
    public String toString() {
        return "Point{" +
                "pos=" + pos +
                '}';
    }

}