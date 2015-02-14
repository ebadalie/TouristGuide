package com.example.faizan.touristguide;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by Faizan on 1/19/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{


    static int tagIdentifier = 0;

    @Override
    public void onClick(View v) {
        int currIndex = v.getId();


        String typeOfCard  = MyAdapter.mDataset.get(currIndex).title;
        Log.i("--------","Who was Clicked ?  With Tag "+v.getTag());
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent2 = new Intent(activity, MenuContainer.class);
        intent2.putExtra("type",typeOfCard);

        ActivityCompat.startActivity(activity, intent2, options.toBundle());
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder  {
        // each data item is just a string in this case
        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;
        protected ImageView vImage;

        public ViewHolder(View v, MyAdapter adb) {
            super(v);

            vTitle = (TextView) v.findViewById(R.id.title);
            vImage = (ImageView)v.findViewById(R.id.main_imview);
            v.setId(new Integer(tagIdentifier++%4));
           v.setOnClickListener(adb);
        }
    }

    public static List<DashBoardInfo> mDataset;
    Activity activity;
    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter( List<DashBoardInfo> myDataset,Activity activity) {
        mDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
         // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customcard, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v,this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);
        DashBoardInfo ci = mDataset.get(position);

//        holder.vName.setText(ci.title);
//        holder.vSurname.setText(ci.name);
//        holder.vEmail.setText(ci.email);
        holder.vTitle.setText(ci.title + " ");
        holder.vImage.setBackgroundResource(ci.imageRes);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}