package in.co.snapqa.clientapp0903.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.keiferstone.nonet.NoNet;

import in.co.snapqa.clientapp0903.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ${Rinkesh} on 27/06/17.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showMessageDialog(String title, String message) {
        progressDialog = ProgressDialog.show(this, title, message, true);

    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        NoNet.monitor(this)
                .poll()
                .snackbar()
                .start();

        super.onStart();
    }

    protected void moveNextTo(Class<?> nextActivity) {
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
    }
}
