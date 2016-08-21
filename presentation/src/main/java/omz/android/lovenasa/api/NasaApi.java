package omz.android.lovenasa.api;

import omz.android.lovenasa.api.models.Apod;
import omz.android.lovenasa.api.models.PatentList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by omrierez on 25/02/16.
 */
public interface NasaApi {


    //For fetching image of the day
    @GET("planetary/apod")
    Call<Apod> fetchApod();
    //For fetching a list of patents
    @GET("patents/content")
    Call<PatentList> fetchPatents(@Query("query") String query, @Query("limit") int limit);

}
