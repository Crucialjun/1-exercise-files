package omz.pluralsight.nasa.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by omrierez on 25/02/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)

public class PatentList {

    @JsonProperty("results")
    private List<Patent> mPatents;

    public List<Patent> getPatents() {
        return mPatents;
    }
}
