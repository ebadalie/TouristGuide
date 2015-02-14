package com.example.faizan.touristguide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Ebad on 2/13/2015.
 */
public class SingleItem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_elem);

        ImageView view = (ImageView) findViewById(R.id.single_img);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageResource((int)GridContainer.curr);


    }
}
