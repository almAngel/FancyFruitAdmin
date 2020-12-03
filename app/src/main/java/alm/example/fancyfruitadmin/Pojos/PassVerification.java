package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PassVerification implements Serializable {

    @SerializedName("password")
    private String password;
    @SerializedName("hash")
    private String hash;

    public PassVerification() {
    }

    public PassVerification(String password, String hash) {
        this.password = password;
        this.hash = hash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "PassVerification{" +
                "password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

}
