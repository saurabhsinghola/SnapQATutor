package in.co.snapqa.clientapp0903.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import in.co.snapqa.clientapp0903.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestServiceGenerator {
    private static int HTTP_CONNECTION_TIMEOUT = 15; // Seconds
    private static int HTTP_READ_TIMEOUT = 15; // Seconds

    private RestServiceGenerator() {
    }

    public static <T> T createRestApiService(Class<T> serviceClass, String baseUrl) {

        Gson gson = new GsonBuilder().setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ")
                //To parse null string values as ""
                .registerTypeAdapterFactory(new JSONParseHelper.NullStringToEmptyAdapterFactory())
                .create();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        // Add StethoInterceptor for OkHttpClient for intercepting network calls
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());

            // Show log for debug build
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpBuilder.addInterceptor(logging);
        }

        okHttpBuilder.connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return builder.create(serviceClass);
    }


}