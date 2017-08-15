package com.royan.twincongress.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.royan.twincongress.R;
import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.SharedPreferencesHelper;
import com.royan.twincongress.models.DataType;

/**
 * Created by szamani on 7/27/2017.
 */

public abstract class CongressBaseActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    protected SpeakerAdapter adapter;
    protected BottomBar bottomBar;
    protected LayoutInflater inflater;
    protected String searchCriteria;
    protected int congressType;

    protected abstract void initRecyclerView();

    protected void setupTapTarget() {
        if (SharedPreferencesHelper.getActivityTapTarget(this, Constants.CONGRESS_ACTIVITY))
            return;

        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.menu_search), "Search")
                                .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                                .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                                .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                                .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                                .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                                .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)                   // Whether to tint the target view's color
                                .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                .targetRadius(40),                  // Specify the target radius (in dp)
                        TapTarget.forView(bottomBar.getTabAtPosition(0), "Invited Speaker")
                                .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                                .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                                .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                                .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                                .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                                .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)                   // Whether to tint the target view's color
                                .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                .targetRadius(40),
                        TapTarget.forView(bottomBar.getTabAtPosition(1), "Oral Presentation")
                                .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                                .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                                .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                                .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                                .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                                .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)                   // Whether to tint the target view's color
                                .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                .targetRadius(40),
                        TapTarget.forView(bottomBar.getTabAtPosition(2), "Poster")
                                .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                                .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                                .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                                .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                                .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                                .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                                .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                .drawShadow(true)                   // Whether to draw a drop shadow or not
                                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                                .tintTarget(true)                   // Whether to tint the target view's color
                                .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                                .targetRadius(40)
                ).listener(new TapTargetSequence.Listener() {
            // This listener will tell us when interesting(tm) events happen in regards
            // to the sequence
            @Override
            public void onSequenceFinish() {
                // Yay
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                // Boo
            }
        }).start();
    }

    protected void initModels() {
        populateOP();
        populateP();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                showSearchDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initDefaultTabModels() {
        populateIS();
    }

    protected void initViews() {
        inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                switch (tabId) {   // into OOD? --> initialize tabs in app startup and use the list here
                    case R.id.tab_is:
                        setGeneralContent(DataType.InvitedSpeaker);
                        break;
                    case R.id.tab_os:
                        setGeneralContent(DataType.OralPresentation);
                        break;
                    case R.id.tab_p:
                        setGeneralContent(DataType.Poster);
                        break;
                }
            }
        });
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {   // into OOD? --> initialize tabs in app startup and use the list here
                    case R.id.tab_is:
                        setGeneralContent(DataType.InvitedSpeaker);
                        break;
                    case R.id.tab_os:
                        setGeneralContent(DataType.OralPresentation);
                        break;
                    case R.id.tab_p:
                        setGeneralContent(DataType.Poster);
                        break;
                }
            }
        });
        bottomBar.setSelected(false);
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                // TODO: cache contents maybe
            }
        });
    }

    protected abstract void setGeneralContent(DataType dataType);

    protected abstract void populateIS();

    protected abstract void populateOP();

    protected abstract void populateP();

    protected abstract void setSearchContent();

    protected abstract void fetchData(int congress, int type);

    protected void showSearchDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.search)
                .icon(ContextCompat.getDrawable(this, R.drawable.ic_search_black_24dp))
                .positiveText(R.string.search)
                .negativeText(R.string.cancel)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }
                })
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRange(2, 30)
                .input(R.string.search_criteria, R.string.empty,
                        true, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                System.out.println("ABSTRACT? " + input.toString());
                                searchCriteria = input.toString();
                                Intent intent = new Intent(CongressBaseActivity.this, SearchResultActivity.class);
                                intent.putExtra(Constants.SEARCH_CRITERIA, searchCriteria);
                                intent.putExtra(Constants.CONGRESS_TYPE, congressType);
                                startActivity(intent);
                            }
                        })
                .build();

        dialog.show();
    }

    protected abstract void performSearch();
}
