package com.royan.twincongress.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.models.Abstract;

import butterknife.BindView;
import butterknife.BindViews;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.royan.twincongress.helpers.Constants.BACKGROUND;
import static com.royan.twincongress.helpers.Constants.CONCLUSION;
import static com.royan.twincongress.helpers.Constants.KEYWORD;
import static com.royan.twincongress.helpers.Constants.METHOD;
import static com.royan.twincongress.helpers.Constants.OBJECTIVE;
import static com.royan.twincongress.helpers.Constants.RESULT;

/**
 * Created by szamani on 8/10/2017.
 */

public abstract class PersonDetailBaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    @BindView(R.id.nameTextCollapsed)
    TextView mTitle;
    @BindView(R.id.sectionTopBorder)
    LinearLayout sectionTopBorder;
    @BindViews({R.id.backgroundSection, R.id.objectiveSection, R.id.methodSection,
            R.id.resultSection, R.id.conclusionSection, R.id.keywordSection})
    CardView[] cardViews;
    @BindView(R.id.avatar)
    CircleImageView avatar;

    protected void initCardViews(Abstract aabstract) {
        if (aabstract.background != null &&
                aabstract.background.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[BACKGROUND].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[BACKGROUND].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.background_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[BACKGROUND].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.background);
            sectionMainText.setText(aabstract.background);
            cardViews[BACKGROUND].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[BACKGROUND].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
        if (aabstract.objective != null &&
                aabstract.objective.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[OBJECTIVE].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[OBJECTIVE].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.objective_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[OBJECTIVE].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.objective);
            sectionMainText.setText(aabstract.objective);
            cardViews[OBJECTIVE].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[OBJECTIVE].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
        if (aabstract.method != null &&
                aabstract.method.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[METHOD].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[METHOD].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.method_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[METHOD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.method);
            sectionMainText.setText(aabstract.method);
            cardViews[METHOD].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[METHOD].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
        if (aabstract.result != null &&
                aabstract.result.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[RESULT].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[RESULT].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.result_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[RESULT].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.result);
            sectionMainText.setText(aabstract.result);
            cardViews[RESULT].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[RESULT].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
        if (aabstract.conclusion != null &&
                aabstract.conclusion.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[CONCLUSION].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[CONCLUSION].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.conclusion_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[CONCLUSION].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.conclusion);
            sectionMainText.setText(aabstract.conclusion);
            cardViews[CONCLUSION].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[CONCLUSION].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
        if (aabstract.keyword != null &&
                aabstract.keyword.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[KEYWORD].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[KEYWORD].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.keyword_icon));
            DocumentView sectionMainText = (DocumentView) cardViews[KEYWORD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.keyword);
            sectionMainText.setText(aabstract.keyword);
            cardViews[KEYWORD].setVisibility(View.VISIBLE);

            sectionTopBorder = (LinearLayout) cardViews[KEYWORD].findViewById(R.id.sectionTopBorder);
            final int randomColor = getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(getResources().getIntArray(R.array.top_bar_colors).length)];
            sectionTopBorder.setBackgroundColor(randomColor);
        }
    }

    protected abstract void initDataModel();

    protected abstract void bindViewData();
}
