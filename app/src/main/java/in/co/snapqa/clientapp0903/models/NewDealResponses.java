package in.co.snapqa.clientapp0903.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OLA on 25/03/17.
 */

public class NewDealResponses {

    @SerializedName("responses")
    @Expose
    List<NewDealFragmentResponse> responses;

    public NewDealResponses(List<NewDealFragmentResponse> responses) {
        this.responses = responses;
    }

    public NewDealResponses() {
        this.responses = new ArrayList();
    }

    public List<NewDealFragmentResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<NewDealFragmentResponse> responses) {
        this.responses = responses;
    }
}
