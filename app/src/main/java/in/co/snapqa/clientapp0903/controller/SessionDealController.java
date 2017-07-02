package in.co.snapqa.clientapp0903.controller;

import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import in.co.snapqa.clientapp0903.network.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionDealController {
    private final BaseActivity activity;
    private final ApiControllerListener apiControllerListener;

    public SessionDealController(BaseActivity activity,
            ApiControllerListener apiControllerListener) {

        this.activity = activity;
        this.apiControllerListener = apiControllerListener;
    }

    public void callNewDeal(AuthRequest authRequest) {
        activity.showMessageDialog("Just a sec!", "Finding Deals");
        APIClient.getClient().newDeals(authRequest).enqueue(new Callback<NewDealResponses>() {
            @Override
            public void onResponse(Call<NewDealResponses> call,
                    Response<NewDealResponses> response) {
                apiControllerListener.onSuccessResult(response);
            }

            @Override
            public void onFailure(Call<NewDealResponses> call, Throwable t) {
                activity.dismissDialog();
                apiControllerListener.onFailureResult();
            }
        });
    }

    public void callLiveDeals(final AuthRequest authRequest) {
        activity.showMessageDialog("Just a sec!", "Loading Accepted Deals");
        APIClient.getClient().liveDeals(authRequest).enqueue(new Callback<NewDealResponses>() {
            @Override
            public void onResponse(Call<NewDealResponses> call,
                    Response<NewDealResponses> response) {
                activity.dismissDialog();
                apiControllerListener.onSuccessResult(response);
            }

            @Override
            public void onFailure(Call<NewDealResponses> call, Throwable t) {
                activity.dismissDialog();
                apiControllerListener.onFailureResult();
            }
        });
    }

    public void callHWDeals(AuthRequest authRequest) {
        activity.showMessageDialog("Just a sec!", "Loading Accepted Deals");
        APIClient.getClient().hwDeals(authRequest).enqueue(new Callback<NewDealResponses>() {
            @Override
            public void onResponse(Call<NewDealResponses> call,
                    Response<NewDealResponses> response) {
                activity.dismissDialog();
                apiControllerListener.onSuccessResult(response);
            }

            @Override
            public void onFailure(Call<NewDealResponses> call, Throwable t) {
                activity.dismissDialog();
                apiControllerListener.onFailureResult();
            }
        });
    }
}
