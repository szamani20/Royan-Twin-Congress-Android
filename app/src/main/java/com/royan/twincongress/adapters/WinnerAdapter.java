package com.royan.twincongress.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
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
import com.royan.twincongress.activities.WinnerDetailActivity;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.SnackBarHelper;
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
        WinnerViewHolder holder = new WinnerViewHolder(itemView);
        FontHelper.applyDefaultFont(holder.winnerCardView);
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

        if (winner.name == null || winner.name.length() == 0)
            holder.winnerNameLayout.setVisibility(View.GONE);
        if (winner.email == null || winner.email.length() == 0)
            holder.winnerEmailLayout.setVisibility(View.GONE);
        if (winner.country == null || winner.country.length() == 0)
            holder.winnerEmailLayout.setVisibility(View.GONE);
        if (winner.country == null || winner.country.length() == 0)
            holder.winnerCountryLayout.setVisibility(View.GONE);
        if (winner.award_time == null || winner.award_time.length() == 0)
            holder.winnerTimeLayout.setVisibility(View.GONE);
        if (winner.award_venue == null || winner.award_venue.length() == 0)
            holder.winnerVenueLayout.setVisibility(View.GONE);

        TextDrawable drawable = FirstLetterDrawableHelper
                .getBigDrawable(winner.name.substring(0, 1), context, holder.topBorder);

        if (winner.avatar != null &&
                winner.avatar.length() != 0)
            Picasso.with(context).load(winner.avatar)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(drawable)
                    .into(holder.winnerAvatar);
        else {
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

        holder.winnerName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Winner Name", winner.name);
                clipboard.setPrimaryClip(clip);
                SnackBarHelper.showSnackbar(context, v,
                        context.getResources().getString(R.string.copied_to_clipboard),
                        Snackbar.LENGTH_SHORT);

                return true;
            }
        });

        holder.winnerEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Winner Email", winner.email);
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
        @BindView(R.id.winnerNameLayout)
        LinearLayout winnerNameLayout;
        @BindView(R.id.winnerEmailLayout)
        LinearLayout winnerEmailLayout;
        @BindView(R.id.winnerCountryLayout)
        LinearLayout winnerCountryLayout;
        @BindView(R.id.winnerTimeLayout)
        LinearLayout winnerTimeLayout;
        @BindView(R.id.winnerVenueLayout)
        LinearLayout winnerVenueLayout;

        WinnerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
