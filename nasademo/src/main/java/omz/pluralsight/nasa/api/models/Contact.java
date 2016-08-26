package omz.pluralsight.nasa.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by omrierez on 25/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)

public class Contact {

    @JsonProperty("name")
    private String mName;

    @JsonProperty("office")
    private String mOffice;

    @JsonProperty("phone")
    private String mPhone;


    public String getName() {
        return mName;
    }

    public String getOffice() {
        return mOffice;
    }

    public String getPhone() {
        return mPhone;
    }
}
