package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName(value = "uuid")
    private String uuid;
    @SerializedName(value = "email")
    private String email;
    @SerializedName(value = "password")
    private String password;
    @SerializedName(value = "username")
    private String username;
    @SerializedName(value = "firstName")
    private String firstName;
    @SerializedName(value = "lastName")
    private String lastName;
    @SerializedName(value = "nif")
    private String nif;

    public User(String uuid, String email, String password, String username, String firstName, String lastName, String nif) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nif = nif;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}
