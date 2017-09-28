package auto.com.applocked.listener;

import java.lang.ref.WeakReference;

public class AppRequestSu {
    private static WeakReference<RequestSuListener> sRequestSuListener;

    public static void requestSu(boolean getSu) {
        if (sRequestSuListener != null && sRequestSuListener.get() != null) {
            sRequestSuListener.get().requestSu(getSu);
        }
    }

    public static void setRequestSuListener(RequestSuListener suListener) {
        if (sRequestSuListener == null) {
            sRequestSuListener = new WeakReference<RequestSuListener>(suListener);
        }
    }

    public interface RequestSuListener{
        void requestSu(boolean getSu);
    }
}
