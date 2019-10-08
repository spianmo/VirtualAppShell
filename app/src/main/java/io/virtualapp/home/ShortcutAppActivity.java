package io.virtualapp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trap.vxp.R;
import io.virtualapp.home.repo.XAppDataInstalled;

public class ShortcutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut_app);
        Bundle lpBundle = getIntent().getExtras();
        while (lpBundle != null) {
            try {
                // Fix android O shortcut by Saurik QQ 384550791
                String pIntentInvoke = lpBundle.getString("pArgsToLaunch");
                getIntent().removeExtra("pArgsToLaunch");
                if (pIntentInvoke == null) {
                    break;
                }
                int dwUserID = lpBundle.getInt("dwUserID");
                XAppDataInstalled lpAppInfo = new XAppDataInstalled();
                lpAppInfo.pkgName = pIntentInvoke;
                LoadingActivity.launch(ShortcutAppActivity.this, pIntentInvoke, dwUserID);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            break;
        }
        finish();
    }
}
