package in.co.snapqa.clientapp0903;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;

import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText signUpPhone, signUpPassword, signUpEmail, signUpName;
    Button signUpButton;
    Verification mVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (Button) findViewById(R.id.sugnupbutton);
        signUpEmail = (EditText) findViewById(R.id.signupemail);
        signUpName = (EditText) findViewById(R.id.signupname);
        signUpPassword = (EditText) findViewById(R.id.signuppassword);
        signUpPhone = (EditText) findViewById(R.id.signupphone);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**************************************************
                 *
                 *
                 *************************************************/

                Intent abcd = new Intent(SignUpActivity.this, SelectSubjectActivity.class);
                startActivity(abcd);

                /**************************************************
                 *
                 *
                 *************************************************/

                SignUpRequest signUpRequest = new SignUpRequest();

                signUpRequest.setPhone(signUpPhone.getText().toString());
                signUpRequest.setPassword(signUpPassword.getText().toString());
                signUpRequest.setEmail(signUpEmail.getText().toString());
                signUpRequest.setName(signUpName.getText().toString());

                Call<SignUpResponse> signUpResponseCall = service.signUp(signUpRequest);

                signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
                    @Override
                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                        SignUpResponse signUpResponse = response.body();
                        int statusCode = response.code();
                        if(signUpResponse.message.equals("Successful")){
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Log.d("status code: ", "" + statusCode);

                            //OTP Verification


                        } else if(signUpResponse.message.equals("Unsuccessful")){
                            Toast.makeText(SignUpActivity.this, "already existing", Toast.LENGTH_LONG).show();
                            Log.d("status code: ", "" + statusCode);
                        }

                    }

                    @Override
                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }


}
