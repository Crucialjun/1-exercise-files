package omz.pluralsight.nasa.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by omrierez on 25/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)

public class Patent {

    @JsonProperty("title")
    private String mTitle;

    @JsonProperty("serial_number")
    private String mSerialNumber;

    @JsonProperty("abstract")
    private String mAbst;

    @JsonProperty("contact")
    private Contact mContact;

    public String getTitle() {
        return mTitle;
    }

    public String getSerialNumber() {
        return mSerialNumber;
    }

    public String getAbst() {
        return mAbst;
    }

    public Contact getContact() {
        return mContact;
    }
}
