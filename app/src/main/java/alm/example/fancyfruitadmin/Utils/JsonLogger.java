package alm.example.fancyfruitadmin.Utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.io.StringWriter;

public class JsonLogger {

    private StringWriter stringWriter = null;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser jp = new JsonParser();

    private static final String TAG = JsonLogger.class.getSimpleName();

    public JsonLogger() {
        stringWriter = new StringWriter();
    }

    public void log(Serializable object) {
        try {
            String json = gson.toJson(object);
            stringWriter.write(json);
            Log.e(TAG, " \n" + stringWriter);
            stringWriter.getBuffer().setLength(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void log(Serializable object, String methodName) {
        try {
            String json = gson.toJson(object);
            stringWriter.write(json);
            Log.e(TAG, methodName + " \n" + stringWriter);
            stringWriter.getBuffer().setLength(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
