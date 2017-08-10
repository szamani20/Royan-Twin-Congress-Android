package com.royan.twincongress;

import android.app.Application;
import co.ronash.pushe.Pushe;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.models.Abstract;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.Winner;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static io.realm.Realm.getDefaultInstance;

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

//        Realm realm =
//                getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                for (int j = 0; j < 9; j++) {
//                    for (int i = 0; i < 500; i++) {
//                        Speaker speaker = realm.createObject(Speaker.class);
//                        Abstract ab = realm.createObject(Abstract.class);
//                        ab.background = "background" + i;
//                        ab.conclusion = "conc" + i;
//                        speaker.id = i;
//                        speaker.type = j % 3;
//                        speaker.congress = j / 3;
//                        if (i % 50 == 0)
//                            speaker.name = "salam";
//                        else speaker.name = "Christopher Catesby Harington " + i;
//                        speaker.email = "kit@GameOfThrones.com";
//                        speaker.country = "GB";
//                        speaker.avatar = R.drawable.kit;
//                        speaker.affiliation = "Senior Scientist & Deputy, Scientific Director\n" +
//                                "of IMBA-Institute of Molecular Biotechnology,\n" +
//                                "Austria";
//                        speaker.topic = "Modelling Human Brain\n" +
//                                "Development in 3D Culture";
//                        speaker.time = "13:20:00 2017 August 23rd";
//                        speaker.venue = "Big hal of fame";
//                        speaker.sAbstract = ab;
//                    }
//
//                    Abstract ab = realm.createObject(Abstract.class);
//                    ab.background = "background" + j;
//                    ab.conclusion = "conc" + j;
//                    Winner winner = realm.createObject(Winner.class);
//                    winner.type = j % 2;
//                    winner.name = "Christopher Catesby Harington " + j;
//                    winner.email = "kit@GameOfThrones.com";
//                    winner.country = "GB";
//                    winner.avatar = R.drawable.kit;
//                    winner.affiliation = "Senior Scientist & Deputy, Scientific Director\n" +
//                            "of IMBA-Institute of Molecular Biotechnology,\n" +
//                            "Austria";
//                    winner.short_cv = "Modelling Human Brain\n" +
//                            "Development in 3D Culture";
//                    winner.award_time = "13:20:00 2017 August 23rd";
//                    winner.award_venue = "Big hal of fame";
//                    winner.anAbstract = ab;
//                }
//            }
//        });
//
//        realm.close();


    }
}
