package com.royan.twincongress.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.royan.twincongress.R;

/**
 * Created by szamani on 7/30/2017.
 */

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SubsamplingScaleImageView mapImage = (SubsamplingScaleImageView) findViewById(R.id.mapImage);
        mapImage.setImage(ImageSource.resource(R.drawable.westeros));
    }
}
