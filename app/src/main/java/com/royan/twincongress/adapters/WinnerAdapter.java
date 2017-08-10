package com.royan.twincongress.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royan.twincongress.R;
import com.royan.twincongress.activities.SpeakerDetailActivity;
import com.royan.twincongress.activities.WinnerDetailActivity;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.RandomHelper;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Winner;
import com.squareup.picasso.Picasso;

import java.util.List;

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
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape)
                    .into(holder.winnerAvatar);
        else Picasso.with(context).load(R.drawable.ic_landscape)
                .resize(120, 120)
                .centerCrop()
                .into(holder.winnerAvatar);

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
        ImageView winnerAvatar;
        TextView winnerName;
        TextView winnerEmail;
        TextView winnerCountry;
        TextView awardTime;
        TextView awardVenue;
        TextView winnerAffiliation;
        TextView winnerShortCV;
        CardView winnerCardView;
        LinearLayout topBorder;

        public WinnerViewHolder(View itemView) {
            super(itemView);
            winnerAvatar = (ImageView) itemView.findViewById(R.id.winnerAvatar);
            winnerName = (TextView) itemView.findViewById(R.id.winnerName);
            winnerEmail = (TextView) itemView.findViewById(R.id.winnerEmail);
            winnerCountry = (TextView) itemView.findViewById(R.id.winnerCountry);
            awardTime = (TextView) itemView.findViewById(R.id.awardTime);
            awardVenue = (TextView) itemView.findViewById(R.id.awardVenue);
            winnerAffiliation = (TextView) itemView.findViewById(R.id.winnerAffiliation);
            winnerShortCV = (TextView) itemView.findViewById(R.id.winnerShortCV);
            winnerCardView = (CardView) itemView.findViewById(R.id.winnerCardView);
            topBorder = (LinearLayout) itemView.findViewById(R.id.topBorder);

            topBorder.setBackgroundColor(context.getResources().getIntArray(R.array.top_bar_colors)[
                    RandomHelper.random.nextInt(context.getResources().getIntArray(R.array.top_bar_colors).length)]);
        }
    }
}
