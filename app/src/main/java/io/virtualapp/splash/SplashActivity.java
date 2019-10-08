package io.virtualapp.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.lody.virtual.client.core.VirtualCore;

import com.trap.vxp.R;
import io.virtualapp.VCommends;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.HomeActivity;
import jonathanfinerty.once.Once;

public class SplashActivity extends VActivity {

    private void toDesktop()
    {
        HomeActivity.goHome(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        @SuppressWarnings("unused")
        boolean enterGuide = !Once.beenDone(Once.THIS_APP_INSTALL, VCommends.TAG_NEW_VERSION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        VUiKit.defer().when(() -> {
            long time = System.currentTimeMillis();
            doActionInThread();
            time = System.currentTimeMillis() - time;
            long delta = 3000L - time;
            if (delta > 0) {
                VUiKit.sleep(delta);
            }
        }).done((res) -> {
            appLikeOnCreate();
        });
    }

    private void appLikeOnCreate() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.KILL_BACKGROUND_PROCESSES)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE) ||
                    !ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.KILL_BACKGROUND_PROCESSES) ||
                    !ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.KILL_BACKGROUND_PROCESSES,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        ResultGen);
            }
        } else {
            bindAndInit();
        }
    }

    static final public int ResultGen = 0x80;

    @Override
    public void onRequestPermissionsResult(int ret,String permissions[], int[] grantResults) {
        if (ret == ResultGen) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindAndInit();
            } else {
                finish();
            }
        }
    }

    private void doActionInThread() {
        if (!VirtualCore.get().isEngineLaunched()) {
            VirtualCore.get().waitForEngine();
        }
    }

    private void bindAndInit() {
        toDesktop();
        finish();
    }
}
