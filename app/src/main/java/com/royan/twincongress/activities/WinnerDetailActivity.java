package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.amulyakhare.textdrawable.TextDrawable;
import com.royan.twincongress.R;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.Winner;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by szamani on 8/10/2017.
 */

public class WinnerDetailActivity extends PersonDetailBaseActivity {
    private Winner winner;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_detail);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        initDataModel();
        ButterKnife.bind(this);
        bindViewData();
        initCardViews(winner.aabstract);

        mToolbar.inflateMenu(R.menu.menu_speaker_detail);
        FontHelper.applyDefaultFont(findViewById(R.id.activity_winner_detail));
    }

    @Override
    protected void initDataModel() {
        int winnerID = getIntent().getIntExtra(Constants.WINNER_ID, 0);
        int winnerType = getIntent().getIntExtra(Constants.WINNER_TYPE, 0);

        try {
            switch (winnerType) {
                case 0:
                    winner = DataEntries.AKP_International_Winner.get(winnerID);
                    break;
                case 1:
                    winner = DataEntries.AKP_National_Winner.get(winnerID);
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Data is not in ram yet :D");
        }

        // read it from database
        if (winner == null) {
            if (realm == null)
                realm = Realm.getDefaultInstance();
            winner = realm.where(Winner.class)
                    .equalTo("type", winnerType)
                    .equalTo("id", winnerID)
                    .findFirst();
        }

        if (winner == null) {
            System.out.println("THIS SHOULD NEVER BE EXECUTED");
            winner = new Winner();
        }
    }

    @Override
    protected void bindViewData() {
        mTitle.setText(winner.name);
        TextDrawable drawable = FirstLetterDrawableHelper
                .getSmallDrawable(winner.name.substring(0, 1), this);

        if (winner.avatar != null &&
                winner.avatar.length() != 0)
            Picasso.with(this)
                    .load(winner.avatar)
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
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }
}
