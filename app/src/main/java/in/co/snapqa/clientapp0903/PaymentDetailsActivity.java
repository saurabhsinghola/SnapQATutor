package in.co.snapqa.clientapp0903;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.PaymentDetailsRequest;
import in.co.snapqa.clientapp0903.models.PaymentDetailsResponse;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentDetailsActivity extends AppCompatActivity {

    EditText bankAccountNumber, ifscCode, panNumber;
    Button submit;
    String acNo, ifsc, pan;
    PaymentDetailsRequest paymentDetailsRequest;


    String token;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Key = "key";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        bankAccountNumber = (EditText) findViewById(R.id.payment_details_account_number);
        ifscCode = (EditText) findViewById(R.id.payment_details_ifsc_code);
        panNumber = (EditText) findViewById(R.id.payment_details_pan_number);

        submit = (Button) findViewById(R.id.payment_details_submit_button);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString(Key, "notPresent");

        getSupportActionBar().setTitle("Enter Your Payment Details");



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentDetailsRequest = new PaymentDetailsRequest(""," ", " ", " ");
                paymentDetailsRequest.setToken(token);
                paymentDetailsRequest.setPanNumber(panNumber.getText().toString().toUpperCase());
                paymentDetailsRequest.setAccountNumber(bankAccountNumber.getText().toString());
                paymentDetailsRequest.setIfscCode(ifscCode.getText().toString().toUpperCase());

                Log.d(" error kahan hai", "" + panNumber.getText().toString());


                Call<PaymentDetailsResponse> call = service.paymentDetails(paymentDetailsRequest);
                call.enqueue(new Callback<PaymentDetailsResponse>() {
                    @Override
                    public void onResponse(Call<PaymentDetailsResponse> call, Response<PaymentDetailsResponse> response) {

                        String res = response.body().getMessage();

                        Log.d("res:   ", "" + res);

                        if(res.equals("Successful !! Bank Details Added !!")){

                            Intent intent = new Intent(PaymentDetailsActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentDetailsResponse> call, Throwable t) {
                        Log.d("Payment details error", "   " + t.getMessage());
                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
