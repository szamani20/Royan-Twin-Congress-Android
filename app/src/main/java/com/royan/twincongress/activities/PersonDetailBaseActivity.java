package com.royan.twincongress.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royan.twincongress.R;
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.helpers.SnackBarHelper;
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
            final TextView sectionMainText = (TextView) cardViews[BACKGROUND].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.background);
            sectionMainText.setText(aabstract.background);
            cardViews[BACKGROUND].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[BACKGROUND].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
        if (aabstract.objective != null &&
                aabstract.objective.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[OBJECTIVE].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[OBJECTIVE].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.objective_icon));
            TextView sectionMainText = (TextView) cardViews[OBJECTIVE].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.objective);
            sectionMainText.setText(aabstract.objective);
            cardViews[OBJECTIVE].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[OBJECTIVE].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
        if (aabstract.method != null &&
                aabstract.method.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[METHOD].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[METHOD].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.method_icon));
            TextView sectionMainText = (TextView) cardViews[METHOD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.method);
            sectionMainText.setText(aabstract.method);
            cardViews[METHOD].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[METHOD].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
        if (aabstract.result != null &&
                aabstract.result.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[RESULT].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[RESULT].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.result_icon));
            TextView sectionMainText = (TextView) cardViews[RESULT].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.result);
            sectionMainText.setText(aabstract.result);
            cardViews[RESULT].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[RESULT].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
        if (aabstract.conclusion != null &&
                aabstract.conclusion.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[CONCLUSION].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[CONCLUSION].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.conclusion_icon));
            TextView sectionMainText = (TextView) cardViews[CONCLUSION].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.conclusion);
            sectionMainText.setText(aabstract.conclusion);
            cardViews[CONCLUSION].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[CONCLUSION].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
        if (aabstract.keyword != null &&
                aabstract.keyword.length() != 0) {
            TextView sectionTitle = (TextView) cardViews[KEYWORD].findViewById(R.id.sectionTitle);
            sectionTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"));
            ImageView sectionIcon = (ImageView) cardViews[KEYWORD].findViewById(R.id.sectionIcon);
            sectionIcon.setImageDrawable(getResources().getDrawable(R.drawable.keyword_icon));
            TextView sectionMainText = (TextView) cardViews[KEYWORD].findViewById(R.id.sectionMainText);
            sectionTitle.setText(R.string.keyword);
            sectionMainText.setText(aabstract.keyword);
            cardViews[KEYWORD].setVisibility(View.VISIBLE);

            setOnLongClickListener(sectionMainText);

            sectionTopBorder = (LinearLayout) cardViews[KEYWORD].findViewById(R.id.sectionTopBorder);
            FirstLetterDrawableHelper.setViewRandomColor(this, sectionTopBorder);
        }
    }

    protected void setOnLongClickListener(final TextView view) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Section Text", view.getText().toString());
                clipboard.setPrimaryClip(clip);
                SnackBarHelper.showSnackbar(getApplicationContext(), v,
                        getResources().getString(R.string.copied_to_clipboard),
                        Snackbar.LENGTH_SHORT);

                return true;
            }
        });
    }

    protected abstract void initDataModel();

    protected abstract void bindViewData();
}
