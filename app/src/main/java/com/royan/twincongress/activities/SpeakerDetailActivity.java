package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.royan.twincongress.R;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.models.Speaker;
import com.squareup.picasso.Picasso;

import io.realm.Realm;


/**
 * Created by szamani on 8/8/2017.
 */

public class SpeakerDetailActivity extends PersonDetailBaseActivity {
    private Speaker speaker;
    private MenuItem bookmarkMenu;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        initDataModel();
        initViews();
        bindViewData();
        initCardViews(speaker.aabstract);

        mAppBarLayout.addOnOffsetChangedListener(this);

        mToolbar.inflateMenu(R.menu.menu_speaker_detail);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    protected void initDataModel() {
        int speakerType = getIntent().getIntExtra(Constants.SPEAKER_TYPE, 0);
        int congressType = getIntent().getIntExtra(Constants.CONGRESS_TYPE, 0);
        int speakerID = getIntent().getIntExtra(Constants.SPEAKER_ID, 0);

        System.out.println("HELLLLO " + speakerType + " " + congressType + " " + speakerID);

        try {
            switch (congressType) {
                case 0:
                    switch (speakerType) {
                        case 0:
                            speaker = DataEntries.SCC_IS_Speaker.get(speakerID);
                            break;
                        case 1:
                            speaker = DataEntries.SCC_OP_Speaker.get(speakerID);
                            break;
                        case 2:
                            speaker = DataEntries.SCC_Poster_Speaker.get(speakerID);
                            break;
                    }
                    break;
                case 1:
                    switch (speakerType) {
                        case 0:
                            speaker = DataEntries.RBC_IS_Speaker.get(speakerID);
                            break;
                        case 1:
                            speaker = DataEntries.RBC_OP_Speaker.get(speakerID);
                            break;
                        case 2:
                            speaker = DataEntries.RBC_Poster_Speaker.get(speakerID);
                            break;
                    }
                    break;
                case 2:
                    switch (speakerType) {
                        case 0:
                            speaker = DataEntries.Nurse_IS_Speaker.get(speakerID);
                            break;
                        case 1:
                            speaker = DataEntries.Nurse_OP_Speaker.get(speakerID);
                            break;
                        case 2:
                            speaker = DataEntries.Nurse_Poster_Speaker.get(speakerID);
                            break;
                    }
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Data is not in ram yet!");
        }

        // read it from database
        if (speaker == null) {
            if (realm == null)
                realm = Realm.getDefaultInstance();
            speaker = realm.where(Speaker.class)
                    .equalTo("congress", congressType)
                    .equalTo("type", speakerType)
                    .equalTo("id", speakerID + 1)
                    .findFirst();
        }

        if (speaker == null) {
            System.out.println("THIS SHOULD NEVER BE EXECUTED");
            speaker = new Speaker();
        }
    }

    protected void bindViewData() {
        nameText.setText(speaker.name);
        mTitle.setText(speaker.name);
        subText.setText(speaker.email);
        if (speaker.avatar != null &&
                speaker.avatar.length() != 0)
            Picasso.with(this)
                    .load(speaker.avatar)
                    .resize(110, 110)
                    .centerCrop()
                    .into(avatar);
        else
            Picasso.with(this)
                    .load(R.drawable.ic_landscape)
                    .resize(110, 110)
                    .centerCrop()
                    .into(avatar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_speaker_detail, menu);
        bookmarkMenu = menu.getItem(0);
        if (speaker == null) {
            System.out.println("SPEAKER NULL");
            return true;
        }
        if (speaker.bookmarked != null && speaker.bookmarked)
            bookmarkMenu.setIcon(R.drawable.ic_bookmark_black_24dp);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(speaker.bookmarked);
        switch (item.getItemId()) {
            case R.id.menu_bookmark:
                if (speaker.bookmarked != null && speaker.bookmarked) {
                    bookmarkMenu.setIcon(R.drawable.ic_bookmark_border_black_24dp);
                    if (realm == null)
                        realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            speaker.bookmarked = false;
                        }
                    });
                } else { // not sure how null boolean field works at current time
                    bookmarkMenu.setIcon(R.drawable.ic_bookmark_black_24dp);
                    if (realm == null)
                        realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            speaker.bookmarked = true;
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
