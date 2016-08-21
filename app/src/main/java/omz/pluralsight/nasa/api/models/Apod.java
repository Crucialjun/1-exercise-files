package omz.pluralsight.nasa.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by omrierez on 25/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)

public class Apod implements Parcelable{


    @JsonProperty("title")
    private String mTitle;

    @JsonProperty("url")
    private String mUrl;

    @JsonProperty("explanation")
    private String mExplanation;

    @JsonProperty("date")
    private String mDate;
    @JsonProperty("copyright")
    private String mCopyright;

    @JsonProperty("media_type")
    private String mMediaType;


    public Apod()
    {
    }

    public Apod(Parcel in) {

        mTitle=in.readString();
        mUrl=in.readString();
        mExplanation=in.readString();
        mDate=in.readString();
        mCopyright=in.readString();
        mMediaType=in.readString();

    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public String getDate() {
        return mDate;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public String getCopyright() {
        return mCopyright;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mUrl);
        dest.writeString(mExplanation);
        dest.writeString(mDate);
        dest.writeString(mCopyright);
        dest.writeString(mMediaType);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Apod{" +
                "mTitle='" + mTitle + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mExplanation='" + mExplanation + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mCopyright='" + mCopyright + '\'' +
                ", mMediaType='" + mMediaType + '\'' +
                '}';
    }



    public static final Parcelable.Creator<Apod> CREATOR = new Parcelable.Creator<Apod>(){
        @Override
        public Apod createFromParcel(Parcel source){
            return new Apod(source);
        }

        @Override
        public Apod[] newArray(int size){
            return new Apod[size];
        }
    };
}
