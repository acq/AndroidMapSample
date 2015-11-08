package com.applidium.map_sample;

import com.applidium.library.stetho.SQLDumpPlugin;
import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class DebugApplication extends App {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableDumpapp(new DumperPluginsProvider() {
                @Override
                public Iterable<DumperPlugin> get() {
                    List<DumperPlugin> plugins = new ArrayList<>();
                    plugins.add(new SQLDumpPlugin());
                    for (DumperPlugin plugin : Stetho.defaultDumperPluginsProvider(getContext()).get()) {
                        plugins.add(plugin);
                    }
                    return plugins;
                }
            })
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build());
    }
}
