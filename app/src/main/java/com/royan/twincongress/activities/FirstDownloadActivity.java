package com.royan.twincongress.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.royan.twincongress.R;
import com.royan.twincongress.connections.FetchCompany;
import com.royan.twincongress.connections.FetchCongress;
import com.royan.twincongress.connections.FetchWinners;
import com.royan.twincongress.helpers.SharedPreferencesHelper;
import com.royan.twincongress.interfaces.OnFirstDownloadTaskListener;
import com.royan.twincongress.models.Abstract;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.RealmString;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.Winner;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

import static com.royan.twincongress.helpers.SnackBarHelper.showSnackbar;

/**
 * Created by szamani on 8/5/2017.
 */

public class FirstDownloadActivity extends AppCompatActivity implements OnFirstDownloadTaskListener {
    private BootstrapButton downloadButton;
    private BootstrapProgressBar downloadProgressBar;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_download);
        realm = Realm.getDefaultInstance();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }

    private void initViews() {
        downloadButton = (BootstrapButton) findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean isConnected = checkForInternet();
                if (!isConnected)
                    showSnackbar(FirstDownloadActivity.this, v, "Update", Snackbar.LENGTH_SHORT);
                else {
                    downloadButton.setEnabled(false);
                    new FirstDownloadTask(FirstDownloadActivity.this).execute();
                }
            }
        });

        downloadProgressBar = (BootstrapProgressBar) findViewById(R.id.downloadProgressBar);
    }

    private class FirstDownloadTask extends AsyncTask<Void, Integer, Void> {
        private OnFirstDownloadTaskListener listener;
        private List<List<Speaker>> congressData;
        private List<List<Winner>> winnersData;
        private List<List<Company>> companiesData;

        FirstDownloadTask(OnFirstDownloadTaskListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 9; i++) {
                congressData.add(new FetchCongress().getSpeakerAll(i / 3, i % 3));
                publishProgress(i);
            }

            for (int i = 0; i < 2; i++) {
                winnersData.add(new FetchWinners().getWinnerAll(i % 2)); // 0 --> international, 1 --> national
                publishProgress(i + 9);
            }

            for (int i = 0; i < 2; i++) {
                companiesData.add(new FetchCompany().getCompanyAll(i % 2)); // 0 --> Sponsor, 1 --> Ordinary
                publishProgress(i + 11);
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            congressData = new ArrayList<>();
            winnersData = new ArrayList<>();
            companiesData = new ArrayList<>();
            downloadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            downloadProgressBar.setVisibility(View.GONE);
            listener.onSpeakerFetchTaskCompleted(congressData, winnersData, companiesData);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            downloadProgressBar.setProgress((int) ((((float) values[0] / 12.0)) * 100));
        }
    }

    private boolean checkForInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    @Override
    public void onSpeakerFetchTaskCompleted(final List<List<Speaker>> speakerList,
                                            final List<List<Winner>> winnerList,
                                            final List<List<Company>> companiesList) {
        System.out.println("Finished");

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 0; i < speakerList.size(); i++) {
                    for (int j = 0; j < speakerList.get(i).size(); j++) {
                        Speaker speaker = realm.createObject(Speaker.class);
                        Abstract aabstract = realm.createObject(Abstract.class);

                        aabstract.background = speakerList.get(i).get(j).aabstract.background;
                        aabstract.objective = speakerList.get(i).get(j).aabstract.objective;
                        aabstract.method = speakerList.get(i).get(j).aabstract.method;
                        aabstract.result = speakerList.get(i).get(j).aabstract.result;
                        aabstract.conclusion = speakerList.get(i).get(j).aabstract.conclusion;
                        aabstract.keyword = speakerList.get(i).get(j).aabstract.keyword;

                        speaker.id = speakerList.get(i).get(j).id;
                        speaker.congress = speakerList.get(i).get(j).congress;
                        speaker.type = speakerList.get(i).get(j).type;
                        speaker.name = speakerList.get(i).get(j).name;
                        speaker.email = speakerList.get(i).get(j).email;
                        speaker.country = speakerList.get(i).get(j).country;
                        speaker.avatar = speakerList.get(i).get(j).avatar;
                        speaker.affiliation = speakerList.get(i).get(j).affiliation;
                        speaker.topic = speakerList.get(i).get(j).topic;
                        speaker.time = speakerList.get(i).get(j).time;
                        speaker.venue = speakerList.get(i).get(j).venue;

                        speaker.aabstract = aabstract;

                        System.out.println(speaker.congress + " " +
                                speaker.type + " " + speaker.id + " " + speaker.name);
                    }
                }

                for (int i = 0; i < winnerList.size(); i++) {
                    for (int j = 0; j < winnerList.get(i).size(); j++) {
                        Winner winner = realm.createObject(Winner.class);
                        Abstract aabstract = realm.createObject(Abstract.class);

                        aabstract.background = winnerList.get(i).get(j).aabstract.background;
                        aabstract.objective = winnerList.get(i).get(j).aabstract.objective;
                        aabstract.method = winnerList.get(i).get(j).aabstract.method;
                        aabstract.result = winnerList.get(i).get(j).aabstract.result;
                        aabstract.conclusion = winnerList.get(i).get(j).aabstract.conclusion;
                        aabstract.keyword = winnerList.get(i).get(j).aabstract.keyword;

                        winner.id = winnerList.get(i).get(j).id;
                        winner.type = winnerList.get(i).get(j).type;
                        winner.name = winnerList.get(i).get(j).name;
                        winner.email = winnerList.get(i).get(j).email;
                        winner.country = winnerList.get(i).get(j).country;
                        winner.avatar = winnerList.get(i).get(j).avatar;
                        winner.affiliation = winnerList.get(i).get(j).affiliation;
                        winner.short_cv = winnerList.get(i).get(j).short_cv;
                        winner.award_time = winnerList.get(i).get(j).award_time;
                        winner.award_venue = winnerList.get(i).get(j).award_venue;

                        winner.aabstract = aabstract;

                        System.out.println(winner.type + " " +
                                winner.id + " " + winner.name);
                    }
                }

                for (int i = 0; i < companiesList.size(); i++) {
                    for (int j = 0; j < companiesList.get(i).size(); j++) {
                        Company company = realm.createObject(Company.class);

                        company.id = companiesList.get(i).get(j).id;
                        company.type = companiesList.get(i).get(j).type;
                        company.name = companiesList.get(i).get(j).name;
                        company.website = companiesList.get(i).get(j).website;
                        company.logo = companiesList.get(i).get(j).logo;
                        company.location = companiesList.get(i).get(j).location;
                        company.phone = companiesList.get(i).get(j).phone;
                        company.address = companiesList.get(i).get(j).address;
                        company.pics = new RealmList<RealmString>();

                        for (RealmString r : companiesList.get(i).get(j).pics)
                            company.pics.add(r);

                        System.out.println(company.type + " " +
                                company.id + " " + company.name);
                    }
                }


            }
        });

        SharedPreferencesHelper.setDataDownloaded(getApplicationContext(), true);
        startActivity(new Intent(FirstDownloadActivity.this, MainActivity.class));
    }
}