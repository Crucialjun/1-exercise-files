package omz.android.lovenasa.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by omrierez on 25/02/16.
 */
public final class RestClient {

    String API_KEY="LnOnYosxVnQjRJ0pad0tqHkMNQ98kml2cMaZPnJb";
    private static final String BASE_API_URL="https://api.nasa.gov";
    private static RestClient mInstance;
    private NasaApi mApi;



    private RestClient()
    {

    }
    //Singleton as we need only one instance
    public static RestClient getInstance()
    {
        if (mInstance==null) {
            mInstance = new RestClient();
            mInstance.init();
        }
        return mInstance;
    }

    private void init()
    {
        OkHttpClient client=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                //Adding the api_key parameter to all API requests
                HttpUrl url = request.url().newBuilder().addQueryParameter("api_key",API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(JacksonConverterFactory.create()).client(client)
                .build();
        mApi= mRetrofit.create(NasaApi.class);
    }


    public NasaApi getClient()
    {
        return mApi;
    }

}
