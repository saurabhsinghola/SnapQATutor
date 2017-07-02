package in.co.snapqa.clientapp0903.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.PaymentController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.PaymentDetailsRequest;
import in.co.snapqa.clientapp0903.models.PaymentDetailsResponse;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentDetailsActivity extends BaseActivity implements ApiControllerListener {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Key = "key";
    EditText bankAccountNumber, ifscCode, panNumber;
    Button submit, skip;
    String acNo, ifsc, pan;
    //    PaymentDetailsRequest paymentDetailsRequest;
    ProgressDialog progressDialog;
    String token;
    SharedPreferences sharedpreferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected String getActivityTitle() {
        return "Enter Your Payment Details";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        bankAccountNumber = (EditText) findViewById(R.id.payment_details_account_number);
        ifscCode = (EditText) findViewById(R.id.payment_details_ifsc_code);
        panNumber = (EditText) findViewById(R.id.payment_details_pan_number);

        submit = (Button) findViewById(R.id.payment_details_submit_button);
        skip = (Button) findViewById(R.id.payment_details_skip_button);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        token = sharedpreferences.getString(Key, "notPresent");


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(" error kahan hai", "" + panNumber.getText().toString());

                callPaymentDetails();

            }
        });

    }

    private void callPaymentDetails() {
        PaymentDetailsRequest paymentDetailsRequest = new PaymentDetailsRequest("", " ", " ", " ");
        paymentDetailsRequest.setToken(token);
        paymentDetailsRequest.setPanNumber(panNumber.getText().toString().toUpperCase());
        paymentDetailsRequest.setAccountNumber(bankAccountNumber.getText().toString());
        paymentDetailsRequest.setIfscCode(ifscCode.getText().toString().toUpperCase());
        new PaymentController(this, this).callPaymentDetail(paymentDetailsRequest);

    }

    @Override
    public void onBackPressed() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String subjects = sharedpreferences.getString("Bank", "notPresent");
        Log.d("chjsdgs:  ", " + " + subjects);
        if (subjects.equals("Bank")) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("Bank");
            editor.commit();
            super.onBackPressed();
        }
    }

    @Override
    public void onSuccessResult(Object response) {
        dismissDialog();
        String res = ((PaymentDetailsResponse) response).getMessage();

        Log.d("res:   ", "" + res);

        if (res.equals("Successful !! Bank Details Added !!")) {
            moveNextTo(MainActivity.class);
        }
    }

    @Override
    public void onFailureResult() {
        dismissDialog();
    }
}
