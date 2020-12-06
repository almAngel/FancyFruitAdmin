package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateLog implements Serializable {

    @SerializedName(value = "uuid")
    private String uuid;
    @SerializedName(value = "device")
    private String device;
    @SerializedName(value = "user") // USER UUID
    private String user;
    @SerializedName(value = "batteryPercentage")
    private Integer batteryPercentage;
    @SerializedName(value = "position") // Latitud y logintud
    private String position;
    @SerializedName(value = "timestamp") // Fecha y hora actual
    private String timestamp;

    public StateLog() {
    }

    public StateLog(String uuid, String device, String user, Integer batteryPercentage, String position, String timestamp) {
        this.uuid = uuid;
        this.device = device;
        this.user = user;
        this.batteryPercentage = batteryPercentage;
        this.position = position;
        this.timestamp = timestamp;
    }

    public StateLog(String device, String user, Integer batteryPercentage, String position, String timestamp) {
        this.device = device;
        this.user = user;
        this.batteryPercentage = batteryPercentage;
        this.position = position;
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StateLog{" +
                "uuid='" + uuid + '\'' +
                ", device='" + device + '\'' +
                ", user='" + user + '\'' +
                ", batteryPercentage=" + batteryPercentage +
                ", position='" + position + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
