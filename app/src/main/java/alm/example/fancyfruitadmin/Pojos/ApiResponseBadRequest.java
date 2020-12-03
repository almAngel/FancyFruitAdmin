package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiResponseBadRequest implements Serializable {

    @SerializedName(value = "statusCode")
    private Integer statusCode;
    @SerializedName(value = "message")
    private List<String> message;
    @SerializedName(value = "error")
    private String error;

    public ApiResponseBadRequest() {
    }

    public ApiResponseBadRequest(Integer statusCode, List<String> message, String error) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ApiResponseBadRequest{" +
                "statusCode=" + statusCode +
                ", message=" + message +
                ", error='" + error + '\'' +
                '}';
    }
}
