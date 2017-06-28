package in.co.snapqa.clientapp0903.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;
import com.tuenti.smsradar.Sms;
import com.tuenti.smsradar.SmsListener;
import com.tuenti.smsradar.SmsRadar;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;

public class VerifyOTPActivity extends BaseActivity implements VerificationListener, ApiControllerListener {

    public String phone;
    Verification verification;
    EditText enterOTP;
    Button verifyOTP;
    TextView didntReceiveOTP;
    String TAG = VerifyOTPActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        enterOTP = (EditText) findViewById(R.id.enter_otp);
        verifyOTP = (Button) findViewById(R.id.verify_otp);
        didntReceiveOTP = (TextView) findViewById(R.id.verify_otp_didnt_receive_otp);
        phone = AppSharedPreferenceHelper.getString(AppSharedPreferenceHelper.PHONE, "notPresent");
        Log.d(TAG, "Phone Number:" + phone);

        SmsRadar.initializeSmsRadarService(getApplicationContext(), new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {

            }

            @Override
            public void onSmsReceived(Sms sms) {
                String str = sms.toString();
                str = str.replaceAll("\\D+", "");
                Log.d("sms : ", " " + str);
                enterOTP.setText(str);
            }
        });

        verification = SendOtpVerification.createSmsVerification(this, phone, this, "91");
        verification.initiate();


        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessageDialog("Just a sec!", "Logging you in");
                verification.verify(enterOTP.getText().toString());
            }
        });

        didntReceiveOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification.initiate();
            }
        });

    }

    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.e(TAG, "Verification initialization failed: " + paramException.getMessage());
    }

    @Override
    public void onVerified(String response) {
        new SignInController(this, this).callOtpVerify(phone);
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.e(TAG, "Verification failed:" + paramException.getMessage());
        Log.d(TAG, "Phone:" + phone);
        dismissDialog();
    }

    @Override
    public void onBackPressed() {
        Log.d("verify otp mein: ", " back dabaya");
    }

    @Override
    protected String getActivityTitle() {
        return "Verify Your OTP";
    }

    @Override
    public void onSuccessResult(Object response) {
        moveNextTo(SelectSubjectActivity.class);

    }

    @Override
    public void onFailureResult() {

    }
}
