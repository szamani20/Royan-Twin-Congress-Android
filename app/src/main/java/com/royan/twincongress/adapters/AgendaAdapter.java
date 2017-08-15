package com.royan.twincongress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.royan.twincongress.R;
import com.royan.twincongress.models.Event;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by szamani on 8/15/2017.
 */

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {
    public List<Event> events;
    public Context context;
    private LayoutInflater inflater;

    public AgendaAdapter(List<Event> events, Context context) {
        inflater = LayoutInflater.from(context);
        this.events = events;
        this.context = context;
    }

    @Override
    public AgendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.agenda_card, parent, false);
        return new AgendaViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(AgendaViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.eventTime.setText(event.time);
        holder.eventName.setText(event.name);
        holder.eventTopic.setText(event.topic);
        holder.eventVenue.setText(event.venue);
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class AgendaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.timeLine) TimelineView timelineView;
        @BindView(R.id.eventTime) TextView eventTime;
        @BindView(R.id.eventName) TextView eventName;
        @BindView(R.id.eventTopic) TextView eventTopic;
        @BindView(R.id.eventVenue) TextView eventVenue;

        public AgendaViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            timelineView.initLine(viewType);
        }
    }
}
