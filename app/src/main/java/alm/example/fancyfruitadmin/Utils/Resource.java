package alm.example.fancyfruitadmin.Utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Resources.GenericResource;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Resource<T> {

    /**
     * SERVICE INSTANCES
     */
    protected Class<T> entityClass;
    private HttpLoggingInterceptor interceptor = null;
    private OkHttpClient httpClient = null;
    private Retrofit retrofit = null;

    public Resource() {
    }

    public Resource(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T get(Context context) {

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Building HttpClient instance
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();

        // Creating a parser for body responses
        Gson gson = new GsonBuilder().serializeNulls().create();

        //Building retrofit instance
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(context.getResources().getString(R.string.api_url))
                .client(httpClient)
                .build();

        return retrofit.create(entityClass);
    }

    public T get(Context context, String username, String password) {

        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Building HttpClient instance
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuthInterceptor(username, password))
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();

        // Creating a parser for body responses
        Gson gson = new GsonBuilder().create();

        //Building retrofit instance
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(context.getResources().getString(R.string.api_url))
                .client(httpClient)
                .build();

        return retrofit.create(entityClass);
    }

}