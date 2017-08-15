package com.royan.twincongress;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.royan.twincongress.helpers.RandomHelper;

import co.ronash.pushe.Pushe;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by szamani on 7/28/2017.
 */

public class RoyanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Pushe.initialize(this,true);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);

        TypefaceProvider.registerDefaultIconSets();
        RandomHelper.initRandom();
    }
}
