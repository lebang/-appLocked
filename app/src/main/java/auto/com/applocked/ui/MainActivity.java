package auto.com.applocked.ui;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import auto.com.applocked.model.AppInfo;
import auto.com.applocked.adapter.AppsAdapter;
import auto.com.applocked.R;

public class MainActivity extends Activity implements AppsAdapter.OnItemListener, TabLayout.OnTabSelectedListener {

    private static final String TAG = "MainActivity";
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mInstallAppsView;
    private TabLayout mTabLayout;
    private AppsAdapter mAppsAdapter, mSystemAdapter;
    private ArrayList<AppInfo> mAppInfos = new ArrayList<AppInfo>();
    private ArrayList<AppInfo> mSystemAppInfos = new ArrayList<AppInfo>();
    private final static String CURRENT_APP_PKG_NAME = "auto.com.applocked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long t = System.currentTimeMillis();
        Log.d(TAG, "init" + t);
        initAppList();
        Log.d(TAG, "init" + (System.currentTimeMillis() - t));
        initView();
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.list_tab);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("应用列表"));
        mTabLayout.addTab(mTabLayout.newTab().setText("系统应用"));
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mInstallAppsView = (RecyclerView) findViewById(auto.com.applocked.R.id.install_apps);
        mAppsAdapter = new AppsAdapter(this, mAppInfos);
        mSystemAdapter = new AppsAdapter(this, mSystemAppInfos);
        mInstallAppsView.setLayoutManager(mLayoutManager); // 设置布局管理器
        mInstallAppsView.setAdapter(mAppsAdapter);
        mTabLayout.addOnTabSelectedListener(this);
        mAppsAdapter.setOnItemListener(this);
    }

    private void initAppList() {
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for (int i = 0, len = packageInfos.size(); i < len; i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            AppInfo appInfo = new AppInfo();
            appInfo.appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            appInfo.appIcon = packageInfo.applicationInfo.loadIcon(getPackageManager());
            appInfo.packageName = packageInfo.packageName;
            appInfo.versionName = packageInfo.versionName;
            appInfo.versionCode = packageInfo.versionCode;
            appInfo.enabled = packageInfo.applicationInfo.enabled;
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (TextUtils.equals(appInfo.packageName, CURRENT_APP_PKG_NAME)){
                    continue;
                }
                mAppInfos.add(appInfo);
            } else {
                mSystemAppInfos.add(appInfo);
            }
        }
    }

    @Override
    public void onItemClick(View view, int positon) {

    }

    @Override
    public void onItemLongClick(View view, int positon) {
        AppInfo appInfo = (AppInfo) view.getTag();
        Toast.makeText(this, "name:" + appInfo.appName + ";enable:" + appInfo.enabled, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                mInstallAppsView.setLayoutManager(mLayoutManager);
                mInstallAppsView.setAdapter(mAppsAdapter);
                break;
            case 1:
                mInstallAppsView.setLayoutManager(mLayoutManager);
                mInstallAppsView.setAdapter(mSystemAdapter);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
