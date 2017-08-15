package com.royan.twincongress.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.royan.twincongress.R;
import com.royan.twincongress.activities.SpeakerDetailActivity;
import com.royan.twincongress.activities.WinnerDetailActivity;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Winner;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by szamani on 7/31/2017.
 */

public class WinnerAdapter extends RecyclerView.Adapter<WinnerAdapter.WinnerViewHolder> {
    public List<Winner> winners;
    public DataType dataType;
    private final LayoutInflater inflater;
    private Context context;

    public WinnerAdapter(Context context, List<Winner> winners, DataType dataType) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.winners = winners;
        this.dataType = dataType;
    }

    @Override
    public WinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.winner_card, parent, false);
        return new WinnerAdapter.WinnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WinnerViewHolder holder, int position) {
        final Winner winner = winners.get(position);
        holder.winnerName.setText(winner.name);
        holder.winnerEmail.setText(winner.email);
        holder.winnerCountry.setText(winner.country);
        holder.awardTime.setText(winner.award_time);
        holder.awardVenue.setText(winner.award_venue);
        holder.winnerAffiliation.setText(winner.affiliation);
        holder.winnerShortCV.setText(winner.short_cv);

        if (winner.avatar != null &&
                winner.avatar.length() != 0)
            Picasso.with(context).load(winner.avatar)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape)
                    .into(holder.winnerAvatar);
        else {
            String letter = winner.name.substring(0, 1);
            if (winner.name.startsWith("Prof") ||
                    winner.name.startsWith("prof") && winner.name.length() > 6)
                letter = winner.name.substring(6, 7);
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(letter, Color.RED);
            holder.winnerAvatar.setImageDrawable(drawable);
        }

        holder.winnerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WinnerDetailActivity.class);
                intent.putExtra(Constants.WINNER_ID, winner.id - 1);
                intent.putExtra(Constants.WINNER_TYPE, winner.type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return winners.size();
    }

    class WinnerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.winnerAvatar) ImageView winnerAvatar;
        @BindView(R.id.winnerName) TextView winnerName;
        @BindView(R.id.winnerEmail) TextView winnerEmail;
        @BindView(R.id.winnerCountry) TextView winnerCountry;
        @BindView(R.id.awardTime) TextView awardTime;
        @BindView(R.id.awardVenue) TextView awardVenue;
        @BindView(R.id.winnerAffiliation) TextView winnerAffiliation;
        @BindView(R.id.winnerShortCV) TextView winnerShortCV;
        @BindView(R.id.winnerCardView) CardView winnerCardView;
        @BindView(R.id.topBorder) LinearLayout topBorder;

        public WinnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            topBorder.setBackgroundColor(context.getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(context.getResources().getIntArray(R.array.top_bar_colors).length)]);
        }
    }
}
