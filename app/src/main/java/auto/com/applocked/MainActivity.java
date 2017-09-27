package auto.com.applocked;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{

    private static final String TAG = "MainActivity";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mInstallAppsView;
    private AppsAdapter mAppsAdapter;
    private ArrayList<AppInfo> mAppInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(auto.com.applocked.R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAppInfos = new ArrayList<AppInfo>();
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for (int i = 0, len = packageInfos.size(); i < len; i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                AppInfo appInfo = new AppInfo();
                appInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                appInfo.packageName = packageInfo.packageName;
                appInfo.versionName = packageInfo.versionName;
                appInfo.versionCode = packageInfo.versionCode;
                appInfo.enabled = packageInfo.applicationInfo.enabled;
                mAppInfos.add(appInfo);
            }
        }
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mInstallAppsView = (RecyclerView) findViewById(auto.com.applocked.R.id.install_apps);
        mAppsAdapter = new AppsAdapter(this, mAppInfos);
        mInstallAppsView.setLayoutManager(mLayoutManager); // 设置布局管理器
        mInstallAppsView.setAdapter(mAppsAdapter);
        mAppsAdapter.setOnItemListener(new AppsAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Toast.makeText(MainActivity.this, "" + positon, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
