package in.co.snapqa.clientapp0903.models;

/**
 * Created by OLA on 10/03/17.
 */

public class NewDealFragmentResponse {

    public String subject;
    public String type;
    public String timing;
    public String price;

    public NewDealFragmentResponse(String subject, String type, String timing, String price) {
        this.subject = subject;
        this.type = type;
        this.timing = timing;
        this.price = price;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
