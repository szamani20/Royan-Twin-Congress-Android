package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.royan.twincongress.R;
import com.royan.twincongress.adapters.AgendaAdapter;
import com.royan.twincongress.dataEntries.DataEntries;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.Event;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;

import static com.royan.twincongress.helpers.Constants.EVENT_FETCH_OFFSET;
import static com.royan.twincongress.helpers.Constants.EVENT_FETCH_SIZE;

/**
 * Created by szamani on 8/14/2017.
 */

public class AgendaActivity extends AppCompatActivity {
    private AgendaAdapter adapter;
    private Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.agenda);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initModel();
        initRecyclerView();
        FontHelper.applyDefaultFont(findViewById(R.id.activityAgendaLayout));
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.agendaRView);
        adapter = new AgendaAdapter(DataEntries.events, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void initModel() {
        if (DataEntries.events == null ||
                DataEntries.events.size() == 0) {
            DataEntries.events = new ArrayList<>();
            fetchData();
        }
    }

    private void fetchData() {
        if (realm == null)
            realm = Realm.getDefaultInstance();

        OrderedRealmCollection<Event> events =
                realm.where(Event.class)
                        .between("id", EVENT_FETCH_OFFSET, EVENT_FETCH_OFFSET + EVENT_FETCH_SIZE)
                        .findAllSorted("id");

        if (events == null ||
                events.size() == 0)
            return;

        EVENT_FETCH_OFFSET += EVENT_FETCH_SIZE;

//        System.out.println("FETCH " + events.size());

        // Load more in this screen
        if (adapter != null && adapter.events != null && adapter.events.size() != 0) {
            for (Event e : events)
                adapter.events.add(e);
            DataEntries.events = adapter.events;
            adapter.notifyDataSetChanged();
        }

        // First time data is being created
        else {
            if (DataEntries.events == null)
                DataEntries.events = new ArrayList<>();
            for (Event e : events)
                DataEntries.events.add(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (realm != null)
//            realm.close();
    }
}
