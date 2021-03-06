package com.royan.twincongress.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
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
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.SnackBarHelper;
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
        SpeakerViewHolder holder = new SpeakerViewHolder(itemView);
        FontHelper.applyDefaultFont(holder.speakerCardView);
        return holder;
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

        if (speaker.name == null || speaker.name.length() == 0)
            holder.speakerNameLayout.setVisibility(View.GONE);
        if (speaker.email == null || speaker.email.length() == 0)
            holder.speakerEmailLayout.setVisibility(View.GONE);
        if (speaker.country == null || speaker.country.length() == 0)
            holder.speakerEmailLayout.setVisibility(View.GONE);
        if (speaker.country == null || speaker.country.length() == 0)
            holder.speakerCountryLayout.setVisibility(View.GONE);
        if (speaker.time == null || speaker.time.length() == 0)
            holder.speakerTimeLayout.setVisibility(View.GONE);
        if (speaker.venue == null || speaker.venue.length() == 0)
            holder.speakerVenueLayout.setVisibility(View.GONE);
        if (dataType == DataType.Poster)
            holder.speakerVenueLayout.setVisibility(View.GONE);

        System.out.println(speaker.id + " " + speaker.name);

        holder.speakerName.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/AvenirLTStd-Heavy.otf"));

        TextDrawable drawable = FirstLetterDrawableHelper
                .getBigDrawable(speaker.name.substring(0, 1),
                        context, holder.topBorder);

        if (speaker.avatar != null &&
                speaker.avatar.length() != 0)
            Picasso.with(context).load(speaker.avatar)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(drawable)
                    .into(holder.speakerAvatar);
        else {
            holder.speakerAvatar.setImageDrawable(drawable);
        }

        holder.speakerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpeakerDetailActivity.class);
                intent.putExtra(Constants.SPEAKER_ID, speaker.id);
                intent.putExtra(Constants.SPEAKER_TYPE, speaker.type);
                intent.putExtra(Constants.CONGRESS_TYPE, speaker.congress);
                context.startActivity(intent);
            }
        });

        holder.speakerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Speaker Name", speaker.name);
                clipboard.setPrimaryClip(clip);
                SnackBarHelper.showSnackbar(context, v,
                        context.getResources().getString(R.string.copied_to_clipboard),
                        Snackbar.LENGTH_SHORT);

                return true;
            }
        });

        holder.speakerEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Speaker Email", speaker.email);
                clipboard.setPrimaryClip(clip);
                SnackBarHelper.showSnackbar(context, v,
                        context.getResources().getString(R.string.copied_to_clipboard),
                        Snackbar.LENGTH_SHORT);

                return true;
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
        @BindView(R.id.speakerNameLayout)
        LinearLayout speakerNameLayout;
        @BindView(R.id.speakerEmailLayout)
        LinearLayout speakerEmailLayout;
        @BindView(R.id.speakerCountryLayout)
        LinearLayout speakerCountryLayout;
        @BindView(R.id.speakerTimeLayout)
        LinearLayout speakerTimeLayout;
        @BindView(R.id.speakerVenueLayout)
        LinearLayout speakerVenueLayout;

        SpeakerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
