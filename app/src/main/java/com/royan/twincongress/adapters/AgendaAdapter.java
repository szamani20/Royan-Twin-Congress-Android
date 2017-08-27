package com.royan.twincongress.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.RandomHelper;
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
        AgendaViewHolder holder = new AgendaViewHolder(itemView, viewType);
        FontHelper.applyDefaultFont(holder.agendaLLayout);
        return new AgendaViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(AgendaViewHolder holder, int position) {
        final Event event = events.get(position);
        holder.eventTime.setText(event.time);
        holder.eventName.setText(event.name);
        holder.eventTopic.setText(event.topic);
        holder.eventVenue.setText(event.venue);
        boolean exec = false;

        if (events.size() > position + 1 &&
                event.time.equals(events.get(position + 1).time)) {
            exec = true;
            holder.timelineView.setMarker(
                    context.getResources().getDrawable(R.drawable.agenda_marker_same_time));
        }

        if (position > 0 &&
                event.time.equals(events.get(position - 1).time)) {
            exec = true;
            holder.timelineView.setMarker(
                    context.getResources().getDrawable(R.drawable.agenda_marker_same_time));
        }

        if (!exec) {
            holder.timelineView.setMarker(
                    context.getResources().getDrawable(R.drawable.agenda_marker));
        }

        if (event.venue.contains("Hafez") || event.venue.contains("hafez")) {
            holder.eventVenue.setTextColor(context.getResources().getColor(R.color.hafez_color));
            holder.timelineView.setMarkerColor(context.getResources().getColor(R.color.hafez_color));
            holder.topBorder.setBackgroundColor(context.getResources().getColor(R.color.hafez_color));
        }
        if (event.venue.contains("Rudaki") || event.venue.contains("rudaki")) {
            holder.eventVenue.setTextColor(context.getResources().getColor(R.color.rudaki_color));
            holder.timelineView.setMarkerColor(context.getResources().getColor(R.color.rudaki_color));
            holder.topBorder.setBackgroundColor(context.getResources().getColor(R.color.rudaki_color));
        }
        if (event.venue.contains("Saadi") || event.venue.contains("saadi")) {
            holder.eventVenue.setTextColor(context.getResources().getColor(R.color.saadi_color));
            holder.timelineView.setMarkerColor(context.getResources().getColor(R.color.saadi_color));
            holder.topBorder.setBackgroundColor(context.getResources().getColor(R.color.saadi_color));
        }

        holder.eventName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/AvenirLTStd-Heavy.otf"));

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
        @BindView(R.id.timeLine)
        TimelineView timelineView;
        @BindView(R.id.eventTime)
        TextView eventTime;
        @BindView(R.id.eventName)
        TextView eventName;
        @BindView(R.id.eventTopic)
        TextView eventTopic;
        @BindView(R.id.eventVenue)
        TextView eventVenue;
        @BindView(R.id.agendaLLayout)
        LinearLayout agendaLLayout;
        @BindView(R.id.topBorder)
        LinearLayout topBorder;

        public AgendaViewHolder(View itemView, int viewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            timelineView.initLine(viewType);
        }
    }
}
