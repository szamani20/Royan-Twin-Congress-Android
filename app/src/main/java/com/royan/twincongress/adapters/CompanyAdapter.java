package com.royan.twincongress.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royan.twincongress.R;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.DataType;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by szamani on 8/5/2017.
 */

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    public List<Company> companies;
    public DataType dataType;
    private final LayoutInflater inflater;
    private Context context;

    public CompanyAdapter(Context context, List<Company> companies, DataType dataType) {
        inflater = LayoutInflater.from(context);
        this.companies = companies;
        this.dataType = dataType;
        this.context = context;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.company_card, parent, false);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        holder.companyName.setText(companies.get(position).name);
        holder.companyWebsite.setText(companies.get(position).website);
        holder.companyPhone.setText(companies.get(position).phone);
        holder.companyLocation.setText(companies.get(position).location);
        holder.companyAddress.setText(companies.get(position).address);

        if (companies.get(position).logo != null &&
                companies.get(position).logo.length() != 0)
            Picasso.with(context).load(companies.get(position).logo)
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape)
                    .into(holder.companyLogo);
        else Picasso.with(context).load(R.drawable.ic_landscape)
                .resize(120, 120)
                .centerCrop()
                .into(holder.companyLogo);

//        holder.companyGalleryRView.setLayoutManager(
//                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        );
//        GalleryHorizontalAdapter adapter =
//                new GalleryHorizontalAdapter(context, companies.get(position).pics);
//        holder.companyGalleryRView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView companyLogo;
        TextView companyName;
        TextView companyWebsite;
        TextView companyPhone;
        TextView companyLocation;
        TextView companyAddress;
//        RecyclerView companyGalleryRView;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            companyLogo = (ImageView) itemView.findViewById(R.id.companyLogo);
            companyName = (TextView) itemView.findViewById(R.id.companyName);
            companyWebsite = (TextView) itemView.findViewById(R.id.companyWebsite);
            companyPhone = (TextView) itemView.findViewById(R.id.companyPhone);
            companyLocation = (TextView) itemView.findViewById(R.id.companyLocation);
            companyAddress = (TextView) itemView.findViewById(R.id.companyAddress);
//            companyGalleryRView = (RecyclerView)
//                    itemView.findViewById(R.id.companyGalleryRView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("SPEAKER ADAPTER", "" + getLayoutPosition());
        }
    }
}
