package in.co.snapqa.clientapp0903;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.ForgotPasswordRequest;
import in.co.snapqa.clientapp0903.models.ForgotPasswordResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button submit;
    EditText phone, email;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        submit = (Button) findViewById(R.id.forgot_password_button);
        phone = (EditText) findViewById(R.id.forgto_password_phone);

        getSupportActionBar().setTitle("Forgot Password?");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        final ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest("", "");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgotPasswordRequest.setEmail(email.getText().toString());
                forgotPasswordRequest.setPhone(phone.getText().toString());

                Call<ForgotPasswordResponse> call = service.forgotPassword(forgotPasswordRequest);
                call.enqueue(new Callback<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                        Log.d("response forpass", "   " + response.body().getMessage());
                        if(response.body().getMessage().equals("Successful")){
                            Intent intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                    }
                });

            }
        });


    }
}
