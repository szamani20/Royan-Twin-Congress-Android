package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.royan.twincongress.R;
import com.royan.twincongress.adapters.SpeakerAdapter;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Speaker;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by szamani on 8/9/2017.
 */

public class SearchResultActivity extends AppCompatActivity {
    private String searchCriteria;
    private int congressType;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        congressType = getIntent().getIntExtra(Constants.CONGRESS_TYPE, 0);
        searchCriteria = getIntent().getStringExtra(Constants.SEARCH_CRITERIA);
        if (searchCriteria == null)
            searchCriteria = "NOTHING";

        initSearchResults();
    }

    private void initSearchResults() {
        initRecyclerView(performSearch());
    }

    private void initRecyclerView(List<Speaker> searchResults) {
        if (searchResults == null) {
            AwesomeTextView noResultText = (AwesomeTextView) findViewById(R.id.noResultText);
            noResultText.setVisibility(View.VISIBLE);
            return;
        }
        AwesomeTextView noResultText = (AwesomeTextView) findViewById(R.id.noResultText);
        noResultText.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.speakerResultRView);
        SpeakerAdapter adapter = new SpeakerAdapter(this, searchResults,
                DataType.Search);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private List<Speaker> performSearch() {  // Called on search button clicked
        if (searchCriteria == null ||
                searchCriteria.length() < 2 ||
                searchCriteria.length() > 30)
            return null;

        if (realm == null)
            realm = Realm.getDefaultInstance();

        OrderedRealmCollection<Speaker> speakers =
                realm.where(Speaker.class)
                        .equalTo("congress", congressType)
                        .like("name", searchCriteria, Case.INSENSITIVE)
                        .or()
                        .like("affiliation", searchCriteria, Case.INSENSITIVE)
                        .or()
                        .like("country", searchCriteria, Case.INSENSITIVE)
                        .or()
                        .like("topic", searchCriteria, Case.INSENSITIVE)
                        .or()
                        .like("aabstract.keyword", searchCriteria, Case.INSENSITIVE)
                        .findAllSorted("type");

        if (speakers == null ||
                speakers.size() == 0)
            return null;

        List<Speaker> searchResults = new ArrayList<>();
        for (Speaker s : speakers)
            searchResults.add(s);

        return searchResults;
    }
}
