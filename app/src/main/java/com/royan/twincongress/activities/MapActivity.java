package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.royan.twincongress.R;
import com.royan.twincongress.helpers.Constants;
import com.royan.twincongress.helpers.FontHelper;

/**
 * Created by szamani on 7/30/2017.
 */

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SubsamplingScaleImageView mapImage = (SubsamplingScaleImageView) findViewById(R.id.mapImage);
        int mapType = getIntent().getIntExtra(Constants.MAP_TYPE, Constants.ITEM_MAP_GRAND);
        switch (mapType) {
            case Constants.ITEM_MAP_GRAND:
                mapImage.setImage(ImageSource.resource(R.drawable.map1));
                break;
            case Constants.ITEM_MAP_FIRST:
                mapImage.setImage(ImageSource.resource(R.drawable.map3)); // map 3
                break;
            case Constants.ITEM_MAP_SECOND:
                mapImage.setImage(ImageSource.resource(R.drawable.map2)); // map 2
                break;
            default:
                mapImage.setImage(ImageSource.resource(R.drawable.map1));
                break;
        }
        FontHelper.applyDefaultFont(findViewById(R.id.activity_map));
    }
}
