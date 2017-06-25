package in.co.snapqa.clientapp0903;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.ChangePasswordRequest;
import in.co.snapqa.clientapp0903.models.ChangePasswordResponse;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends AppCompatActivity {

    PasswordView password, confirm;
    Button submit;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phone";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        password = (PasswordView) findViewById(R.id.change_password_password);
        confirm = (PasswordView) findViewById(R.id.change_password_confirm);

        submit = (Button) findViewById(R.id.change_password_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getSupportActionBar().setTitle("Enter New Password");

        final API service = retrofit.create(API.class);

        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("", "");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordRequest.setPassword(password.getText().toString());
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String phone = sharedpreferences.getString(Phone, "notPresent");
                Log.d("log asdasd", " phonr number :    " + phone);
                changePasswordRequest.setPhone(phone);
                Call<ChangePasswordResponse> call = service.changePassword(changePasswordRequest);

                call.enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

                        ChangePasswordResponse changePasswordResponse = response.body();

                        if(changePasswordResponse == null) {
                            Log.d("tag", "sjhdjkasd :  ");
                        }else if(changePasswordResponse.getMessage().equals("PasswordResetSuccessfully")){
                            Intent intent = new Intent(ChangePasswordActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        Log.d("dfsd t error", " dekh lo" + t.getMessage());
                    }
                });

            }
        });
    }
}
