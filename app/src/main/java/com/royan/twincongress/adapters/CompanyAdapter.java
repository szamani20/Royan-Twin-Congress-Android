package com.royan.twincongress.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.DataType;
import com.royan.twincongress.picassoHelper.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        CompanyViewHolder holder = new CompanyViewHolder(itemView);
        FontHelper.applyDefaultFont(holder.companyCardView);
        return new CompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CompanyViewHolder holder, int position) {
        final Company company = companies.get(position);
        holder.companyName.setText(company.name);
        holder.companyWebsite.setText(company.website);
        holder.companyPhone.setText(company.phone);
        holder.companyLocation.setText(company.location);
        holder.companyAddress.setText(company.address);

        if (company.logo != null &&
                company.logo.length() != 0)
            Picasso.with(context).load(company.logo)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(R.drawable.ic_landscape)
                    .into(holder.companyLogo);
        else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(company.name.substring(0, 1), Color.RED);
            holder.companyLogo.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    class CompanyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.companyLogo)
        ImageView companyLogo;
        @BindView(R.id.companyName)
        TextView companyName;
        @BindView(R.id.companyWebsite)
        TextView companyWebsite;
        @BindView(R.id.companyPhone)
        TextView companyPhone;
        @BindView(R.id.companyLocation)
        TextView companyLocation;
        @BindView(R.id.companyAddress)
        TextView companyAddress;
        @BindView(R.id.companyCardView)
        CardView companyCardView;

        CompanyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("SPEAKER ADAPTER", "" + getLayoutPosition());
        }
    }
}
