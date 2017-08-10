//package com.royan.twincongress.adapters;
//
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.royan.twincongress.R;
//import com.royan.twincongress.interfaces.OnLoadMoreListener;
//import com.royan.twincongress.models.DataType;
//import com.royan.twincongress.models.Speaker;
//import com.squareup.picasso.Picasso;
//
//import io.realm.OrderedRealmCollection;
//import io.realm.RealmRecyclerViewAdapter;
//
//import java.util.List;
//
///**
// * Created by szamani on 7/29/2017.
// */
//
//public class SpeakerRealmAdapter extends RealmRecyclerViewAdapter<Speaker, SpeakerRealmAdapter.SpeakerViewHolder> {
//    public OrderedRealmCollection<Speaker> speakers;
//    public DataType dataType;
//    private final LayoutInflater inflater;
//    private Context context;
//
//    public SpeakerRealmAdapter(Context context, OrderedRealmCollection<Speaker> speakers, DataType dataType) {
//        super(speakers, true);
//        setHasStableIds(true);
//        inflater = LayoutInflater.from(context);
//        this.context = context;
//        this.speakers = speakers;
//        this.dataType = dataType;
//    }
//
//    @Override
//    public SpeakerRealmAdapter.SpeakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = inflater.inflate(R.layout.speaker_card, parent, false);
//        return new SpeakerRealmAdapter.SpeakerViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(SpeakerRealmAdapter.SpeakerViewHolder holder, int position) {
////        System.out.println("POSITION " + position);
////        System.out.println("NAME " + speakers.get(position).name);
////        System.out.println(" " + speakers.size());
//
////        final Speaker speaker = getItem(position);
////        System.out.println(speaker == null);
////        if (speaker == null)
////            return;
//
//        holder.speakerName.setText(speakers.get(position).name);
//        holder.speakerEmail.setText(speakers.get(position).email);
//        holder.speakerCountry.setText(speakers.get(position).country);
//        holder.speechTime.setText(speakers.get(position).time);
//        holder.speechVenue.setText(speakers.get(position).venue);
//        holder.speechAffiliation.setText(speakers.get(position).affiliation);
//        holder.speechTopic.setText(speakers.get(position).topic);
//        Picasso.with(context).load(speakers.get(position).avatar)
//                .resize(100, 100)
//                .centerCrop()
//                .into(holder.speakerAvatar);
//
////        holder.speakerName.setText(speaker.name);
////        holder.speakerEmail.setText(speaker.email);
////        holder.speakerCountry.setText(speaker.country);
////        holder.speechTime.setText(speaker.time);
////        holder.speechVenue.setText(speaker.venue);
////        holder.speechAffiliation.setText(speaker.affiliation);
////        holder.speechTopic.setText(speaker.topic);
////        Picasso.with(context).load(speaker.avatar)
////                .resize(100, 100)
////                .centerCrop()
////                .into(holder.speakerAvatar);
//    }
//
//    @Override
//    public int getItemCount() {
//        return speakers.size();
//    }
//
//    class SpeakerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        ImageView speakerAvatar;
//        TextView speakerName;
//        TextView speakerEmail;
//        TextView speakerCountry;
//        TextView speechTime;
//        TextView speechVenue;
//        TextView speechAffiliation;
//        TextView speechTopic;
//
//        public SpeakerViewHolder(View itemView) {
//            super(itemView);
//            speakerAvatar = (ImageView) itemView.findViewById(R.id.speakerAvatar);
//            speakerName = (TextView) itemView.findViewById(R.id.speakerName);
//            speakerEmail = (TextView) itemView.findViewById(R.id.speakerEmail);
//            speakerCountry = (TextView) itemView.findViewById(R.id.speakerCountry);
//            speechTime = (TextView) itemView.findViewById(R.id.speechTime);
//            speechVenue = (TextView) itemView.findViewById(R.id.speechVenue);
//            speechAffiliation = (TextView) itemView.findViewById(R.id.speechAffiliation);
//            speechTopic = (TextView) itemView.findViewById(R.id.speechTopic);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            Log.d("SPEAKER ADAPTER", "" + getLayoutPosition());
//        }
//    }
//}
