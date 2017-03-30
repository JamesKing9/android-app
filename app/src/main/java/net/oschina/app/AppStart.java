package net.oschina.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import net.oschina.app.improve.main.MainActivity;
import net.oschina.app.util.TDevice;

import org.kymjs.kjframe.utils.PreferenceHelper;

/**
 * 应用启动界面
 */
public class AppStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(MainActivity.class);
        if (aty != null && !aty.isFinishing()) { /*“且”*/
            finish();
        }

        setContentView(R.layout.app_start);
        findViewById(R.id.app_start_view).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectTo(); /*延时 0.8s，跳转到。。。*/
            }
        }, 800);
    }

    // 此方法执行后Activity 可见
    @Override
    protected void onResume() {
        super.onResume();
        /*org.kymjs.kjframe.utils.PreferenceHelper*/
        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
                "first_install", -1); /* 读取 /data/data/net.oschina.app/shared_prefs/first_install.xml 文件*/
        int currentVersion = TDevice.getVersionCode();
        if (cacheVersion < currentVersion) {
            PreferenceHelper.write(this, "first_install", "first_install",
                    currentVersion);
            // TODO 新版本时进行一定操作
        }
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}