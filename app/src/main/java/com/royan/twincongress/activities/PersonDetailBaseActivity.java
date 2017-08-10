package com.royan.twincongress.activities;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royan.twincongress.R;
import com.royan.twincongress.models.Abstract;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.royan.twincongress.helpers.Constants.ABSTRACT_SIZE;
import static com.royan.twincongress.helpers.Constants.BACKGROUND;
import static com.royan.twincongress.helpers.Constants.CONCLUSION;
import static com.royan.twincongress.helpers.Constants.KEYWORD;
import static com.royan.twincongress.helpers.Constants.METHOD;
import static com.royan.twincongress.helpers.Constants.OBJECTIVE;
import static com.royan.twincongress.helpers.Constants.RESULT;

/**
 * Created by szamani on 8/10/2017.
 */

public abstract class PersonDetailBaseActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    protected Toolbar mToolbar;
    protected LinearLayout mTitleContainer;
    protected TextView mTitle;
    protected CardView[] cardViews;
    protected TextView nameText;
    protected TextView subText;
    protected CircleImageView avatar;
    protected AppBarLayout mAppBarLayout;

    protected void initCardViews(Abstract aabstract) {
        if (cardViews == null)
            cardViews = new CardView[ABSTRACT_SIZE];
        cardViews[BACKGROUND] = (CardView) findViewById(R.id.backgroundSection);
        cardViews[OBJECTIVE] = (CardView) findViewById(R.id.objectiveSection);
        cardViews[METHOD] = (CardView) findViewById(R.id.methodSection);
        cardViews[RESULT] = (CardView) findViewById(R.id.resultSection);
        cardViews[CONCLUSION] = (CardView) findViewById(R.id.conclusionSection);
        cardViews[KEYWORD] = (CardView) findViewById(R.id.keywordSection);

        if (aabstract.background != null &&
                aabstract.background.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[BACKGROUND].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[BACKGROUND].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[BACKGROUND].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.background);
            sectionMainText.setText(aabstract.background);
            cardViews[BACKGROUND].setVisibility(View.VISIBLE);
        }
        if (aabstract.objective != null &&
                aabstract.objective.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[OBJECTIVE].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[OBJECTIVE].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[OBJECTIVE].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.objective);
            sectionMainText.setText(aabstract.objective);
            cardViews[OBJECTIVE].setVisibility(View.VISIBLE);
        }
        if (aabstract.method != null &&
                aabstract.method.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[METHOD].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[METHOD].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[METHOD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.method);
            sectionMainText.setText(aabstract.method);
            cardViews[METHOD].setVisibility(View.VISIBLE);
        }
        if (aabstract.result != null &&
                aabstract.result.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[RESULT].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[RESULT].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[RESULT].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.result);
            sectionMainText.setText(aabstract.result);
            cardViews[RESULT].setVisibility(View.VISIBLE);
        }
        if (aabstract.conclusion != null &&
                aabstract.conclusion.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[CONCLUSION].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[CONCLUSION].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[CONCLUSION].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.conclusion);
            sectionMainText.setText(aabstract.conclusion);
            cardViews[CONCLUSION].setVisibility(View.VISIBLE);
        }
        if (aabstract.keyword != null &&
                aabstract.keyword.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[KEYWORD].findViewById(R.id.sectionTitle);
            ImageView sectionIcon = (ImageView) cardViews[KEYWORD].findViewById(R.id.sectionIcon);
            TextView sectionMainText = (TextView) cardViews[KEYWORD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.keyword);
            sectionMainText.setText(aabstract.keyword);
            cardViews[KEYWORD].setVisibility(View.VISIBLE);
        }
    }

    protected void initViews() {
        mTitle = (TextView) findViewById(R.id.nameTextCollapsed);
        nameText = (TextView) findViewById(R.id.nameText);
        subText = (TextView) findViewById(R.id.subText);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        mTitleContainer = (LinearLayout) findViewById(R.id.linearLayoutTitle);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBar);
    }

    protected abstract void initDataModel();
    protected abstract void bindViewData();

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
