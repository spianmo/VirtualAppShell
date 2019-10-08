package io.virtualapp.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;

import com.lody.virtual.sandxposed.XposedModuleProfile;

import java.util.ArrayList;
import java.util.List;

import com.trap.vxp.R;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.adapters.XposedModuleAdapter;
import io.virtualapp.home.adapters.decorations.ItemOffsetDecoration;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.repo.AppRepository;

public class XposedManagerActivity extends VActivity {

    SwitchCompat xposedSwitch;
    RecyclerView recyclerView;

    AppRepository appRepository;
    List<AppData> modules = new ArrayList<>();

    XposedModuleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xposed_manager);
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initXposedGlobalSettings();
        initModuleList();
    }

    private void initXposedGlobalSettings() {
        xposedSwitch = findViewById(R.id.xposed_enable_switch);
        xposedSwitch.setChecked( XposedModuleProfile.isXposedEnable());
        xposedSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            XposedModuleProfile.enbaleXposed(b);
            recyclerView.setEnabled(b);
            recyclerView.setAlpha(b ? 1 : 0.5f);
        });
    }

    private void initModuleList() {

        boolean xposedEnabled =  XposedModuleProfile.isXposedEnable();

        recyclerView = findViewById(R.id.module_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(VUiKit.dpToPx(getContext(), 4)));
        recyclerView.setEnabled(xposedEnabled);
        recyclerView.setAlpha(xposedEnabled ? 1 : 0.5f);

        appRepository = new AppRepository(this);
        adapter = new XposedModuleAdapter(this, appRepository, modules);
        appRepository.getVirtualXposedModules()
                .done(result -> {
                    modules.clear();
                    modules.addAll(result);
                    adapter.notifyDataSetChanged();
                });

        recyclerView.setAdapter(adapter);

    }

}
