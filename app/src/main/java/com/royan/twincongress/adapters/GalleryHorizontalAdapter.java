package com.royan.twincongress.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.royan.twincongress.R;
import com.royan.twincongress.models.RealmString;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szamani on 8/6/2017.
 */

public class GalleryHorizontalAdapter extends RecyclerView.Adapter<GalleryHorizontalAdapter.GalleryViewHolder> {
    public List<RealmString> pics;
    private final LayoutInflater inflater;
    private Context context;

    public GalleryHorizontalAdapter(Context context, List<RealmString> pics) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.pics = pics;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.company_gallery_item, parent, false);
        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        if (pics.get(position).pic != null &&
                pics.get(position).pic.length() != 0)
            Picasso.with(context).load(pics.get(position).pic)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.pic);
        else
            Picasso.with(context).load(R.drawable.ic_landscape)
                    .resize(200, 200)
                    .centerCrop()
                    .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView pic;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.companyPic);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("SPEAKER ADAPTER", "" + getLayoutPosition());
        }
    }
}