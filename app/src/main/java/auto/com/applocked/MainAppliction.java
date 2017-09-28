package auto.com.applocked;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiaomi.mistatistic.sdk.MiStatInterface;

/**
 * Created by lebang on 2017/9/28.
 */

public class MainAppliction extends Application {
    /**
     * 小米统计帐号
     * AppID：
     * AppKey：
     */
    private static final String appID = "2882303761517621633";
    private static final String appKey = "5531762170633";

    private static final String TAG = "MainAppliction";
    private static Context sContext;
    private static String sAppChannel;

    public static Context getAppContext() {
        return sContext;
    }

    protected static void init(Context context) {
        sContext = context;
        sAppChannel = sContext.getResources().getString(R.string.app_channel);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init(getApplicationContext());

        try {
            MiStatInterface.initialize(sContext, appID, appKey, sAppChannel);
        } catch (Exception e) {
            Log.d(TAG, "application init:", e);
        }
    }
}
