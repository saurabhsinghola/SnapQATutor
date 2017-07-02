package in.co.snapqa.clientapp0903;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ${Rinkesh} on 27/06/17.
 */

public class SnapApplication extends Application {
    private static Context mContext;
    private static Thread.UncaughtExceptionHandler mDefaultUEH;
    private static Thread.UncaughtExceptionHandler mCaughtExceptionHandler =
            new Thread.UncaughtExceptionHandler() {

                @Override

                public void uncaughtException(Thread thread, Throwable ex) {
//TODO Create One Activity for Error Handling
//            Intent intent = new Intent(mContext, ErrorHandlingActivity_.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
//            mContext.startActivity(intent);
                    System.exit(1); // kill off the crashed app
                    mDefaultUEH.uncaughtException(thread, ex);
                }

            };

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            initStetho();
        } else {
            initCalligraphy();
            setupDefaultAppCrashHandler();
        }

    }

    private void setupDefaultAppCrashHandler() {
        mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mCaughtExceptionHandler);

    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(uk.co.chrisjenx.calligraphy.R.attr.fontPath)
                .build());
    }

}
