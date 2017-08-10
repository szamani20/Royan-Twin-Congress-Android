package com.royan.twincongress.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.royan.twincongress.R;
//import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.adapters.SpeakerAdapter;
//import com.royan.twincongress.adapters.SpeakerRealmAdapter;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.SnackBarHelper;
import com.royan.twincongress.models.ActivityType;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Speaker;

import java.util.List;

import io.realm.Realm;

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
