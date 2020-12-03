package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class ApiResponse implements Serializable {

    @SerializedName(value = "message")
    private String message;
    @SerializedName(value = "code")
    private Integer code;

    public ApiResponse() {
    }

    public ApiResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
