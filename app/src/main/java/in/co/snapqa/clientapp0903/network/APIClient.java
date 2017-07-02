package in.co.snapqa.clientapp0903.network;

import in.co.snapqa.clientapp0903.interfaces.API;
import retrofit2.Retrofit;

/**
 * Created by OLA on 10/03/17.
 */

public class APIClient {
    public static Retrofit retrofit = null;
    private static API appUpdaterAPIContract = null;
    private static APIClient instance = null;
    private static String URL = "http://ec2-52-54-173-224.compute-1.amazonaws.com:7200/api/snapQA/";
//    private static String url = "http://ec2-52-54-173-224.compute-1.amazonaws.com:7000/api/chat/";


    private APIClient() {
    }


    //    AppUpdaterAPIContract
    public static API getClient() {
        initialize();
        return appUpdaterAPIContract;
    }

    private static void initialize() {
        if (instance == null || appUpdaterAPIContract == null) {
            instance = new APIClient();
            appUpdaterAPIContract = RestServiceGenerator.createRestApiService(API.class, URL);
        }
    }
}
