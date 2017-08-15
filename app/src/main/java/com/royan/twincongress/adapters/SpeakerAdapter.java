package com.royan.twincongress.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.royan.twincongress.R;
import com.royan.twincongress.activities.SpeakerDetailActivity;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by szamani on 7/27/2017.
 */

public class SpeakerAdapter extends
        RecyclerView.Adapter<SpeakerAdapter.SpeakerViewHolder> {
    private Context context;
    public List<Speaker> speakers;
    public DataType dataType;
    private final LayoutInflater inflater;


    public SpeakerAdapter(Context context, List<Speaker> speakers,
                          DataType dataType) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.speakers = speakers;
        this.dataType = dataType;
    }

    @Override
    public SpeakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.speaker_card, parent, false);
        return new SpeakerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SpeakerViewHolder holder, int position) {
        final Speaker speaker = speakers.get(position);
        holder.speakerName.setText(speaker.name);
        holder.speakerEmail.setText(speaker.email);
        holder.speakerCountry.setText(speaker.country);
        holder.speechTime.setText(speaker.time);
        holder.speechVenue.setText(speaker.venue);
        holder.speechAffiliation.setText(speaker.affiliation);
        holder.speechTopic.setText(speaker.topic);

        if (speaker.avatar != null &&
                speaker.avatar.length() != 0)
            Picasso.with(context).load(speaker.avatar)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape)
                    .into(holder.speakerAvatar);
        else {
            String letter = speaker.name.substring(0, 1);
            if (speaker.name.startsWith("Prof") ||
                    speaker.name.startsWith("prof") && speaker.name.length() > 6)
                letter = speaker.name.substring(6, 7);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(letter, Color.RED);
            System.out.println(letter + "  " + drawable.getIntrinsicHeight() + "  " + drawable.getIntrinsicWidth());
            holder.speakerAvatar.setImageDrawable(drawable);
        }

        holder.speakerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeakerDetailActivity.class);
                intent.putExtra(Constants.SPEAKER_ID, speaker.id - 1);
                intent.putExtra(Constants.SPEAKER_TYPE, speaker.type);
                intent.putExtra(Constants.CONGRESS_TYPE, speaker.congress);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }


    class SpeakerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.speakerAvatar)
        ImageView speakerAvatar;
        @BindView(R.id.speakerName)
        TextView speakerName;
        @BindView(R.id.speakerEmail)
        TextView speakerEmail;
        @BindView(R.id.speakerCountry)
        TextView speakerCountry;
        @BindView(R.id.speechTime)
        TextView speechTime;
        @BindView(R.id.speechVenue)
        TextView speechVenue;
        @BindView(R.id.speechAffiliation)
        TextView speechAffiliation;
        @BindView(R.id.speechTopic)
        TextView speechTopic;
        @BindView(R.id.speakerCardView)
        CardView speakerCardView;
        @BindView(R.id.topBorder)
        LinearLayout topBorder;

        SpeakerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            topBorder.setBackgroundColor(context.getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(context.getResources().getIntArray(R.array.top_bar_colors).length)]);
        }

    }
}
