package in.co.snapqa.clientapp0903.controller;


import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.PaymentDetailsRequest;
import in.co.snapqa.clientapp0903.models.PaymentDetailsResponse;
import in.co.snapqa.clientapp0903.network.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentController {
    private final BaseActivity activity;
    private final ApiControllerListener apiControllerListener;

    public PaymentController(BaseActivity activity,
            ApiControllerListener apiControllerListener) {

        this.activity = activity;
        this.apiControllerListener = apiControllerListener;
    }

    public void callPaymentDetail(PaymentDetailsRequest paymentDetailsRequest) {
        activity.showMessageDialog("Just a sec!", "Saving Your Details");
        APIClient.getClient().paymentDetails(paymentDetailsRequest).enqueue(
                new Callback<PaymentDetailsResponse>() {
                    @Override
                    public void onResponse(Call<PaymentDetailsResponse> call,
                            Response<PaymentDetailsResponse> response) {
                        activity.dismissDialog();
                        if (response != null) {
                            apiControllerListener.onSuccessResult(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentDetailsResponse> call, Throwable t) {
                        activity.dismissDialog();
                        apiControllerListener.onFailureResult();

                    }
                });

    }
}
