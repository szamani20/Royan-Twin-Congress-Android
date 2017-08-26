package com.royan.twincongress.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
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
import com.royan.twincongress.helpers.FirstLetterDrawableHelper;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.SnackBarHelper;
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

        if (company.name == null || company.name.length() == 0)
            holder.companyNameLayout.setVisibility(View.GONE);
        if (company.website == null || company.website.length() == 0)
            holder.companyWebsiteLayout.setVisibility(View.GONE);
        if (company.phone == null || company.phone.length() == 0)
            holder.companyPhoneLayout.setVisibility(View.GONE);
        if (company.location == null || company.location.length() == 0)
            holder.companyLocationLayout.setVisibility(View.GONE);
        if (company.address == null || company.address.length() == 0)
            holder.companyAddressLayout.setVisibility(View.GONE);

        holder.companyName.setTypeface(Typeface.createFromAsset(context.getAssets(),
                "fonts/AvenirLTStd-Heavy.otf"));

        TextDrawable drawable = FirstLetterDrawableHelper
                .getBigDrawable(company.name.substring(0, 1), context, holder.topBorder);

        if (company.logo != null &&
                company.logo.length() != 0)
            Picasso.with(context).load(company.logo)
                    .transform(new CircleTransform())
                    .resize(120, 120)
                    .centerCrop()
                    .placeholder(drawable)
                    .into(holder.companyLogo);
        else {
            holder.companyLogo.setImageDrawable(drawable);
        }

        holder.companyName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Compant Name", company.name);
                clipboard.setPrimaryClip(clip);
                SnackBarHelper.showSnackbar(context, v,
                        context.getResources().getString(R.string.copied_to_clipboard),
                        Snackbar.LENGTH_SHORT);

                return true;
            }
        });

        holder.companyAddress.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Compant Address", company.address);
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
        @BindView(R.id.topBorder)
        LinearLayout topBorder;
        @BindView(R.id.companyNameLayout)
        LinearLayout companyNameLayout;
        @BindView(R.id.companyWebsiteLayout)
        LinearLayout companyWebsiteLayout;
        @BindView(R.id.companyPhoneLayout)
        LinearLayout companyPhoneLayout;
        @BindView(R.id.companyLocationLayout)
        LinearLayout companyLocationLayout;
        @BindView(R.id.companyAddressLayout)
        LinearLayout companyAddressLayout;

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
