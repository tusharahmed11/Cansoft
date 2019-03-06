package com.cansoft.app.network;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dimitrovskif.smartcache.BasicCaching;
import dimitrovskif.smartcache.SmartCallFactory;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final long TIMEOUT_SECOND = 120;
    private static final long WRITE_TIMEOUT_SECOND = 120;
    private static final long READ_TIMEOUT_SECOND = 120;

    private static RestClient restClient = null;
    private OkHttpClient client;

    private RestClient() {

    }

    /**
     * Get instance of the network client
     *
     * @return instance of the network client
     */
    @NonNull
    public static RestClient getInstance() {
        if (restClient == null) {
            synchronized (RestClient.class) {
                if (restClient == null) {
                    restClient = new RestClient();
                }
            }
        }
        return restClient;
    }

    /**
     * Get Base url from the shared Pref
     *
     * @return base_url for the network calls
     */
    @NonNull
    private static String getBaseURL() {
        return "https://cansoft.com/wp-json/wp/v2/";
    }

    /**
     * API service object
     *
     * @return ApiService to call the API's
     */
    public ApiService callRetrofit(Context context) {
        /*int cacheSize = 10 * 1024 * 1024;
        File cacheDir = new File(context.getCacheDir(),"HttpCache");
        Cache cache = new Cache(cacheDir,cacheSize);*/
        int cacheSize = 20 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        try {
                            return chain.proceed(chain.request());
                        } catch (Exception e) {
                            Request offlineRequest = chain.request().newBuilder()
                                    .header("Cache-Control", "public, only-if-cached," +
                                            "max-stale=" + 60 * 60 * 24)
                                    .build();
                            return chain.proceed(offlineRequest);
                        }
                    }
                })
                .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .build();
        SmartCallFactory smartFactory = new SmartCallFactory(BasicCaching.fromCtx(context));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .addCallAdapterFactory(smartFactory)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ApiService.class);
    }

    /**
     * Cancel all pending network calls in queue
     */

    public void cancelRequest() {
        client.dispatcher().cancelAll();
    }
}
