package com.royan.twincongress.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amulyakhare.textdrawable.TextDrawable;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.royan.twincongress.R;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.SharedPreferencesHelper;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
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
        ButterKnife.bind(this);
        bindViewData();
        initCardViews(speaker.aabstract);

        mToolbar.inflateMenu(R.menu.menu_speaker_detail);
        setupTapTarget();
        FontHelper.applyDefaultFont(findViewById(R.id.activity_speaker_detail));
    }

    private void setupTapTarget() {
        if (SharedPreferencesHelper.getActivityTapTarget(this, Constants.SPEAKER_DETAIL_ACTIVITY))
            return;

        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.menu_bookmark), "Bookmark", "Access to your bookmarks from side drawer")
                        // All options below are optional
                        .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                        .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                    }
                });
    }

    private Speaker getSpeakerFromID(List<Speaker> speakerList, int id) {
        for (Speaker s : speakerList)
            if (s.id == id)
                return s;
        return speakerList.get(0);
    }

    protected void initDataModel() {
        int speakerType = getIntent().getIntExtra(Constants.SPEAKER_TYPE, 0);
        int congressType = getIntent().getIntExtra(Constants.CONGRESS_TYPE, 0);
        int speakerID = getIntent().getIntExtra(Constants.SPEAKER_ID, 0);

//        System.out.println("HELLLLO " + speakerType + " " + congressType + " " + speakerID);

        try {
            switch (congressType) {
                case 0:
                    switch (speakerType) {
                        case 0:
                            speaker = getSpeakerFromID(DataEntries.SCC_IS_Speaker, speakerID);
                            break;
                        case 1:
                            speaker = getSpeakerFromID(DataEntries.SCC_OP_Speaker, speakerID);
                            break;
                        case 2:
                            speaker = getSpeakerFromID(DataEntries.SCC_Poster_Speaker, speakerID);
                            break;
                    }
                    break;
                case 1:
                    switch (speakerType) {
                        case 0:
                            speaker = getSpeakerFromID(DataEntries.RBC_IS_Speaker, speakerID);
                            break;
                        case 1:
                            speaker = getSpeakerFromID(DataEntries.RBC_OP_Speaker, speakerID);
                            break;
                        case 2:
                            speaker = getSpeakerFromID(DataEntries.RBC_Poster_Speaker, speakerID);
                            break;
                    }
                    break;
                case 2:
                    switch (speakerType) {
                        case 0:
                            speaker = getSpeakerFromID(DataEntries.Nurse_IS_Speaker, speakerID);
                            break;
                        case 1:
                            speaker = getSpeakerFromID(DataEntries.Nurse_OP_Speaker, speakerID);
                            break;
                        case 2:
                            speaker = getSpeakerFromID(DataEntries.Nurse_Poster_Speaker, speakerID);
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
        mTitle.setText(speaker.name);

        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        TextDrawable drawable = FirstLetterDrawableHelper
                .getSmallDrawable(speaker.name.substring(0, 1), this);

//        System.out.println("#####################   " + speaker.name + "  " + drawable.getIntrinsicWidth() + " " + drawable.getIntrinsicHeight());

        if (speaker.avatar != null &&
                speaker.avatar.length() != 0)
            Picasso.with(this)
                    .load(speaker.avatar)
                    .transform(new CircleTransform())
                    .resize(50, 50)
                    .centerCrop()
                    .placeholder(drawable)
                    .into(avatar);
        else {
            avatar.setImageDrawable(drawable);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_speaker_detail, menu);
        bookmarkMenu = menu.getItem(0);
        if (speaker == null) {
//            System.out.println("SPEAKER NULL");
            return true;
        }
        if (speaker.bookmarked != null && speaker.bookmarked)
            bookmarkMenu.setIcon(R.drawable.ic_bookmark_black_24dp);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        System.out.println(speaker.bookmarked);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }
}
