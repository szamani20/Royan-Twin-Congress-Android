package com.royan.twincongress.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;
import com.royan.twincongress.helpers.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.royan.twincongress.helpers.SnackBarHelper.showSnackbar;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rbcImageView)
    ImageView rbcImageView;
    @BindView(R.id.sccImageView)
    ImageView sccImageView;
    @BindView(R.id.nurseImageView)
    ImageView nurseImageView;
    @BindView(R.id.awardImageView)
    ImageView awardImageView;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        setupDrawer(toolbar);
        setupTapTarget();
        FontHelper.applyDefaultFont(findViewById(R.id.activity_main));
    }

    private void setupTapTarget() {
        if (SharedPreferencesHelper.getActivityTapTarget(this, Constants.MAIN_ACTIVITY))
            return;

        TextView tapTargetView = (TextView) findViewById(R.id.tapTargetView);
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(tapTargetView,
                        getResources().getString(R.string.navigation_drawer),
                        getResources().getString(R.string.navigation_drawer_text))
                        // All options below are optional
                        .outerCircleColor(R.color.nc_color)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.rbc_color)   // Specify a color for the target circle
                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.scc_color)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                        .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        drawer.openDrawer();
                    }
                });
    }

    @OnClick(R.id.rbcImageView)
    public void startScc() {
        startActivity(new Intent(MainActivity.this, RBCActivity.class));
    }

    @OnClick(R.id.sccImageView)
    public void startRbc() {
        startActivity(new Intent(MainActivity.this, SCCActivity.class));
    }

    @OnClick(R.id.nurseImageView)
    public void startNc() {
        startActivity(new Intent(MainActivity.this, NurseActivity.class));
    }

    @OnClick(R.id.awardImageView)
    public void startAkp() {
        startActivity(new Intent(MainActivity.this, AwardActivity.class));
    }

    private void setupDrawer(Toolbar toolbar) {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.targaryen)
                .build();

        PrimaryDrawerItem item = new PrimaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Heavy.otf"))
                .withIdentifier(Constants.ITEM_BANNER).withName(R.string.app_name);
        SecondaryDrawerItem bookmarks = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_bookmark_black_24dp)
                .withIdentifier(Constants.ITEM_BOOKMARKS).withName(R.string.bookmarks);
        SecondaryDrawerItem agenda = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.agenda_icon)
                .withIdentifier(Constants.ITEM_AGENDA).withName(R.string.agenda);
        SecondaryDrawerItem agendaDownload = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_file_download_black_24dp)
                .withIdentifier(Constants.ITEM_AGENDA_DOWNLOAD).withName(R.string.agenda_download);
        SecondaryDrawerItem companies = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_format_list_bulleted_black_24dp)
                .withIdentifier(Constants.ITEM_COMPANIES).withName(R.string.companies);
        SecondaryDrawerItem sponsors = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_format_list_bulleted_black_24dp)
                .withIdentifier(Constants.ITEM_SPONSORS).withName(R.string.sponsors);
        SecondaryDrawerItem grandFloorMap = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_map_black_24dp)
                .withIdentifier(Constants.ITEM_MAP_GRAND).withName(R.string.grand_floor_map);
        SecondaryDrawerItem minus2Map = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_map_black_24dp)
                .withIdentifier(Constants.ITEM_MAP_SECOND).withName(R.string.second_floor_map);
        SecondaryDrawerItem minus1Map = new SecondaryDrawerItem()
                .withTypeface(Typeface.createFromAsset(getAssets(), "fonts/AvenirLTStd-Light.otf"))
                .withIcon(R.drawable.ic_map_black_24dp)
                .withIdentifier(Constants.ITEM_MAP_FIRST).withName(R.string.first_floor_map);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(header)
                .addDrawerItems(
                        item,
                        new DividerDrawerItem(),
                        bookmarks,
                        agenda,
                        agendaDownload,
                        companies,
                        sponsors,
                        grandFloorMap,
                        minus1Map,
                        minus2Map
                )
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case Constants.ITEM_BANNER:
                                showSnackbar(MainActivity.this, view, "Twin Congress", Snackbar.LENGTH_SHORT);
                                break;
                            case Constants.ITEM_BOOKMARKS:
                                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                                break;
                            case Constants.ITEM_AGENDA:
                                startActivity(new Intent(MainActivity.this, AgendaActivity.class));
                                break;
                            case Constants.ITEM_AGENDA_DOWNLOAD:
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                                startActivity(browserIntent);
                                break;
                            case Constants.ITEM_COMPANIES:
                                startActivity(new Intent(MainActivity.this, CompanyActivity.class));
                                break;
                            case Constants.ITEM_SPONSORS:
                                startActivity(new Intent(MainActivity.this, SponsorActivity.class));
                                break;
                            case Constants.ITEM_MAP_GRAND:
                                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                                intent.putExtra(Constants.MAP_TYPE, Constants.ITEM_MAP_GRAND);
                                startActivity(intent);
                                break;
                            case Constants.ITEM_MAP_FIRST:
                                Intent intent1 = new Intent(MainActivity.this, MapActivity.class);
                                intent1.putExtra(Constants.MAP_TYPE, Constants.ITEM_MAP_FIRST);
                                startActivity(intent1);
                                break;
                            case Constants.ITEM_MAP_SECOND:
                                Intent intent2 = new Intent(MainActivity.this, MapActivity.class);
                                intent2.putExtra(Constants.MAP_TYPE, Constants.ITEM_MAP_SECOND);
                                startActivity(intent2);
                                break;
                        }
                        return true;
                    }
                })
                .build();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawer != null) {
            drawer.setSelection(-1);
            drawer.closeDrawer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        finish();
//        System.exit(0);
    }
}
