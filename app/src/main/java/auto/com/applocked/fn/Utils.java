package auto.com.applocked.fn;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class Utils {
    private static final String TAG = "Utils";
    public static void enableApp(String appPackageName){
        try {
            Process localProcess= Runtime.getRuntime().exec("su");
            String cmd="pm enable "+appPackageName+"\n";
            DataOutputStream dataOutputStream=new DataOutputStream(localProcess.getOutputStream());
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
        } catch (IOException e) {
            Log.d(TAG, "IOExpeciton", e);
        }
    }

    public static void disableApp(String appPackageName){
        try {
            Process localProcess= Runtime.getRuntime().exec("su");
            String cmd="pm disable "+appPackageName+"\n";
            DataOutputStream dataOutputStream=new DataOutputStream(localProcess.getOutputStream());
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
        } catch (IOException e) {
            Log.d(TAG, "IOExpeciton", e);
        }
    }
}
