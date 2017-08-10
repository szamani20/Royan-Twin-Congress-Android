package com.royan.twincongress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.royan.twincongress.R;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.models.Winner;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by szamani on 7/30/2017.
 */

public class WinnerRealmAdapter extends RealmRecyclerViewAdapter<Winner, WinnerRealmAdapter.WinnerViewHolder> {
    public OrderedRealmCollection<Winner> winners;
    public DataType dataType;
    private final LayoutInflater inflater;
    private Context context;

    public WinnerRealmAdapter(Context context, OrderedRealmCollection<Winner> winners, DataType dataType) {
        super(winners, true);
        setHasStableIds(true);
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.winners = winners;
        this.dataType = dataType;
    }

    @Override
    public WinnerRealmAdapter.WinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.winner_card, parent, false);
        return new WinnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WinnerRealmAdapter.WinnerViewHolder holder, int position) {
        holder.winnerName.setText(winners.get(position).name);
        holder.winnerEmail.setText(winners.get(position).email);
        holder.winnerCountry.setText(winners.get(position).country);
        holder.awardTime.setText(winners.get(position).award_time);
        holder.awardVenue.setText(winners.get(position).award_venue);
        holder.winnerAffiliation.setText(winners.get(position).affiliation);
        holder.winnerShortCV.setText(winners.get(position).short_cv);

        // TODO: do abstract stuff

        Picasso.with(context).load(winners.get(position).avatar)
                .resize(100, 100)
                .centerCrop()
                .into(holder.winnerAvatar);
    }

    @Override
    public int getItemCount() {
        return winners.size();
    }

    class WinnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView winnerAvatar;
        TextView winnerName;
        TextView winnerEmail;
        TextView winnerCountry;
        TextView awardTime;
        TextView awardVenue;
        TextView winnerAffiliation;
        TextView winnerShortCV;
//        LinearLayout abstract_;

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
//            abstract_ = (LinearLayout) inflater.inflate(R.layout.abstract_, null, true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("WINNER ADAPTER", "" + getLayoutPosition());
        }
    }
}
