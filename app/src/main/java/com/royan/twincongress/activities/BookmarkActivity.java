package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.royan.twincongress.R;
import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.Speaker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by szamani on 7/30/2017.
 */

public class BookmarkActivity extends AppCompatActivity {
    @BindView(R.id.speakerBookmarkRView) RecyclerView recyclerView;
    @BindView(R.id.noResultText) AwesomeTextView noResultText;
    private SpeakerAdapter adapter;
    private List<Speaker> bookmarks;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.bookmarks);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initBookmarks();
        ButterKnife.bind(this);
        initRecyclerView();
        FontHelper.applyDefaultFont(findViewById(R.id.activityBookmarkLayout));
    }

    private void initBookmarks() {
        if (realm == null)
            realm = Realm.getDefaultInstance();

        bookmarks = new ArrayList<>();

        // TODO: boundary or limit result count
        OrderedRealmCollection<Speaker> speakers =
                realm.where(Speaker.class)
                        .equalTo("bookmarked", true)
                        .findAllSorted("name");

        if (speakers == null ||
                speakers.size() == 0) {
            dataSetChanged();
            return;
        }

        for (Speaker s : speakers)
            bookmarks.add(s);

        dataSetChanged();
    }

    private void dataSetChanged() {
        if (adapter != null) {
            adapter.speakers = bookmarks;
            adapter.notifyDataSetChanged();
            if (bookmarks.size() == 0 && noResultText != null)
                noResultText.setVisibility(View.VISIBLE);
        }
    }

    private void initRecyclerView() {
        if (bookmarks == null ||
                bookmarks.size() == 0) {
            noResultText.setVisibility(View.VISIBLE);
            return;
        }
        noResultText.setVisibility(View.GONE);

        adapter = new SpeakerAdapter(this, bookmarks, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBookmarks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }
}
