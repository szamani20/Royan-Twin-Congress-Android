package com.royan.twincongress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

import static com.royan.twincongress.helpers.SnackBarHelper.showSnackbar;

public class MainActivity extends AppCompatActivity {
    private CardView SCCCardView;
    private CardView RBCCardView;
    private CardView AKPCardView;
    private CardView nurseCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        setupDrawer(toolbar);
        initViews();
    }

    private void initViews() {
        SCCCardView = (CardView) findViewById(R.id.SCCCardView);
        RBCCardView = (CardView) findViewById(R.id.RBCCardView);
        nurseCardView = (CardView) findViewById(R.id.nurseCardView);
        AKPCardView = (CardView) findViewById(R.id.AKPCardView);

        SCCCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SCCActivity.class));
            }
        });

        RBCCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RBCActivity.class));
            }
        });

        nurseCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NurseActivity.class));
            }
        });

        AKPCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AwardActivity.class));
            }
        });
    }

    private void setupDrawer(Toolbar toolbar) {
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();

        PrimaryDrawerItem item = new PrimaryDrawerItem()
                .withIdentifier(Constants.ITEM_BANNER).withName(R.string.app_name);
        SecondaryDrawerItem bookmarks = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_bookmark_black_24dp)
                .withIdentifier(Constants.ITEM_BOOKMARKS).withName(R.string.bookmarks);
        SecondaryDrawerItem agenda = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_view_agenda_black_24dp)
                .withIdentifier(Constants.ITEM_AGENDA).withName(R.string.agenda);
        SecondaryDrawerItem companies = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_format_list_bulleted_black_24dp)
                .withIdentifier(Constants.ITEM_COMPANIES).withName(R.string.companies);
        SecondaryDrawerItem sponsors = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_format_list_numbered_black_24dp)
                .withIdentifier(Constants.ITEM_SPONSORS).withName(R.string.sponsors);
        SecondaryDrawerItem map = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_map_black_24dp)
                .withIdentifier(Constants.ITEM_MAP).withName(R.string.map);
        SecondaryDrawerItem update = new SecondaryDrawerItem()
                .withIcon(R.drawable.ic_update_black_24dp)
                .withIdentifier(Constants.ITEM_UPDATE).withName(R.string.update);

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(header)
                .addDrawerItems(
                        item,
                        new DividerDrawerItem(),
                        bookmarks,
                        agenda,
                        companies,
                        sponsors,
                        map,
                        update
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
                                showSnackbar(MainActivity.this, view, "agenda", Snackbar.LENGTH_SHORT);
                                break;
                            case Constants.ITEM_COMPANIES:
                                startActivity(new Intent(MainActivity.this, CompanyActivity.class));
                                break;
                            case Constants.ITEM_SPONSORS:
                                startActivity(new Intent(MainActivity.this, SponsorActivity.class));
                                break;
                            case Constants.ITEM_MAP:
                                startActivity(new Intent(MainActivity.this, MapActivity.class));
                                break;
                            case Constants.ITEM_UPDATE:
                                showSnackbar(MainActivity.this, view, "Update", Snackbar.LENGTH_SHORT);
                                break;
                        }
                        return true;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

}
