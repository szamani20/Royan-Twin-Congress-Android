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
import com.royan.twincongress.connections.FetchEvent;
import com.royan.twincongress.connections.FetchWinners;
import com.royan.twincongress.helpers.SharedPreferencesHelper;
import com.royan.twincongress.interfaces.OnFirstDownloadTaskListener;
import com.royan.twincongress.models.Abstract;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.Event;
import com.royan.twincongress.models.RealmString;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.Winner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

import static com.royan.twincongress.helpers.SnackBarHelper.showSnackbar;

/**
 * Created by szamani on 8/5/2017.
 */

public class FirstDownloadActivity extends AppCompatActivity implements OnFirstDownloadTaskListener {
    @BindView(R.id.downloadButton)
    BootstrapButton downloadButton;
    @BindView(R.id.downloadProgressBar)
    BootstrapProgressBar downloadProgressBar;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_download);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }

    @OnClick(R.id.downloadButton)
    public void downloadAction(View v) {
        final Boolean isConnected = checkForInternet();
        if (!isConnected)
            showSnackbar(FirstDownloadActivity.this, v,
                    getResources().getString(R.string.no_internet), Snackbar.LENGTH_SHORT);
        else {
            downloadButton.setEnabled(false);
            new FirstDownloadTask(FirstDownloadActivity.this).execute();
        }
    }

    private class FirstDownloadTask extends AsyncTask<Void, Integer, Void> {
        private OnFirstDownloadTaskListener listener;
        private List<List<Speaker>> congressData;
        private List<List<Winner>> winnersData;
        private List<List<Company>> companiesData;
        private List<Event> eventsData;


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

            eventsData = new FetchEvent().getEventsAll();
            publishProgress(13);

            return null;
        }

        @Override
        protected void onPreExecute() {
            congressData = new ArrayList<>();
            winnersData = new ArrayList<>();
            companiesData = new ArrayList<>();
            eventsData = new ArrayList<>(); // not necessary
            downloadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            downloadProgressBar.setVisibility(View.GONE);
            listener.onSpeakerFetchTaskCompleted(congressData, winnersData,
                    companiesData, eventsData);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            downloadProgressBar.setProgress((int) ((((float) values[0] / 13.0)) * 100));
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
                                            final List<List<Company>> companiesList,
                                            final List<Event> eventList) {
//        System.out.println("Finished");

        if (realm == null)
            realm = Realm.getDefaultInstance();
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
                        speaker.sortCriteria = speaker.time;
                        if (speaker.time != null && speaker.time.length() > 15)
                            speaker.sortCriteria = speaker.time.substring(4, 5)
                                    + speaker.time.substring(8, 10)
                                    + speaker.time.substring(11);

                        speaker.aabstract = aabstract;

                        System.out.println(speaker.congress + " " +
                                speaker.type + " " + speaker.id + " " + speaker.sortCriteria);
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

//                        System.out.println(winner.type + " " +
//                                winner.id + " " + winner.name);
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

//                        System.out.println(company.type + " " +
//                                company.id + " " + company.name);
                    }
                }

                for (int i = 0; i < eventList.size(); i++) {
                    Event event = realm.createObject(Event.class);

                    event.id = eventList.get(i).id;
                    event.name = eventList.get(i).name;
                    event.topic = eventList.get(i).topic;
                    event.time = eventList.get(i).time;
                    event.venue = eventList.get(i).venue;

//                    System.out.println(event.id + " " +
//                            event.name + " " + event.topic);
                }

            }
        });

        SharedPreferencesHelper.setDataDownloaded(getApplicationContext(), true);
        startActivity(new Intent(FirstDownloadActivity.this, MainActivity.class));
    }

}
