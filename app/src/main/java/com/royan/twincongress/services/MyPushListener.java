package com.royan.twincongress.services;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.royan.twincongress.connections.FetchCompany;
import com.royan.twincongress.connections.FetchCongress;
import com.royan.twincongress.connections.FetchWinners;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.PushedData;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.Winner;

import org.json.JSONObject;

import co.ronash.pushe.PusheListenerService;
import io.realm.Realm;

/**
 * Created by szamani on 8/6/2017.
 */

public class MyPushListener extends PusheListenerService {
    @Override
    public void onMessageReceived(JSONObject json, JSONObject messageContent) {
        System.out.println("#####################################################################");
        System.out.println(json.toString());  // custom content!
        System.out.println(messageContent.toString());

        processJsonData(json.toString());
    }

    private void processJsonData(String json) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        PushedData data = gson.fromJson(json, PushedData.class);
        System.out.println(data.id + " " + data.type + " " + data.sub_type + " " + data.model);
        // Currently i don't know why i serialize nulls and then check if there is one
        // Why just don't do these stuff?
        if (data == null ||
                data.model == null ||
                data.model.length() == 0 ||
                data.type == null ||
                data.type.length() == 0 ||
                data.id == null)
            return;
        if (data.type.equals(Constants.SCC) ||
                data.type.equals(Constants.RBC) ||
                data.type.equals(Constants.NURSE))
            if (data.sub_type == null ||
                    data.sub_type.length() == 0)
                return;

        switch (data.model) {
            case Constants.SPEAKER:
                changeSpeaker(data);
                break;
            case Constants.WINNER:
                changeWinner(data);
                break;
            case Constants.COMPANY:
                changeCompany(data);
                break;
        }

    }

    private void changeCompany(PushedData data) {
        final int type;
        switch (data.type) {
            case Constants.SPONSOR:
                type = 0;
                break;
            case Constants.ORDINARY:
                type = 1;
                break;
            default:
                return;
        }
        new FetchCompanyTask(type, data.id).execute();
    }

    private class FetchCompanyTask extends AsyncTask<Void, Integer, Void> {
        private Company fetched_company;
        private int type, id;

        FetchCompanyTask(int type, int id) {
            this.type = type;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO check for null
            fetched_company = new FetchCompany().getCompanyRange(type, id, id).get(0);
            System.out.println("########################");
            System.out.println(fetched_company.name);
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Company company = realm
                            .where(Company.class)
                            .equalTo("type", type)
                            .equalTo("id", id)
                            .findFirst();
                    if (company != null) {
                        company.name = fetched_company.name;
                        company.website = fetched_company.website;
                        company.logo = fetched_company.logo;
                        company.location = fetched_company.location;
                        company.phone = fetched_company.phone;
                        company.address = fetched_company.address;
                    }
                }
            });
            return null;
        }
    }

    private void changeWinner(PushedData data) {
        final int type;
        switch (data.type) {
            case Constants.INTERNATIONAL:
                type = 0;
                break;
            case Constants.NATIONAL:
                type = 1;
                break;
            default:
                return;
        }
        new FetchWinnerTask(type, data.id).execute();
    }

    private class FetchWinnerTask extends AsyncTask<Void, Integer, Void> {
        private Winner fetched_winner;
        private int type, id;

        FetchWinnerTask(int type, int id) {
            this.type = type;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO check for null
            fetched_winner = new FetchWinners().getWinnerRange(type, id, id).get(0);
            System.out.println("########################");
            System.out.println(fetched_winner.aabstract.background);
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Winner winner = realm
                            .where(Winner.class)
                            .equalTo("type", type)
                            .equalTo("id", id)
                            .findFirst();
                    if (winner != null) {
                        winner.name = fetched_winner.name;
                        winner.email = fetched_winner.email;
                        winner.country = fetched_winner.country;
                        winner.avatar = fetched_winner.avatar;
                        winner.affiliation = fetched_winner.affiliation;
                        winner.short_cv = fetched_winner.short_cv;
                        winner.award_time = fetched_winner.award_time;
                        winner.award_venue = fetched_winner.award_venue;

                        // want to change aabstract? then change every damn single field
                    }
                }
            });
            return null;
        }
    }


    private void changeSpeaker(final PushedData data) {
        final int congress;
        final int type;
        switch (data.type) {
            case Constants.SCC:
                congress = 0;
                break;
            case Constants.RBC:
                congress = 1;
                break;
            case Constants.NURSE:
                congress = 2;
                break;
            default:
                return;
        }
        switch (data.sub_type) {
            case Constants.IS:
                type = 0;
                break;
            case Constants.OP:
                type = 1;
                break;
            case Constants.POSTER:
                type = 2;
                break;
            default:
                return;
        }

        new FetchSpeakerTask(congress, type, data.id).execute();
    }

    private class FetchSpeakerTask extends AsyncTask<Void, Integer, Void> {
        private Speaker fetched_speaker;
        private int congress, type, id;

        FetchSpeakerTask(int congress, int type, int id) {
            this.congress = congress;
            this.type = type;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO check for null
            fetched_speaker = new FetchCongress().getSpeakerRange(congress, type, id, id).get(0);
            System.out.println("########################");
            System.out.println(fetched_speaker.aabstract.background);
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Speaker speaker = realm
                            .where(Speaker.class)
                            .equalTo("congress", congress)
                            .equalTo("type", type)
                            .equalTo("id", id)
                            .findFirst();
                    if (speaker != null) {
                        speaker.name = fetched_speaker.name;
                        speaker.email = fetched_speaker.email;
                        speaker.country = fetched_speaker.country;
                        speaker.avatar = fetched_speaker.avatar;
                        speaker.affiliation = fetched_speaker.affiliation;
                        speaker.topic = fetched_speaker.topic;
                        speaker.time = fetched_speaker.time;
                        speaker.venue = fetched_speaker.venue;

                        // want to change aabstract? then change every damn single field
                    }
                }
            });
            return null;
        }
    }

}
