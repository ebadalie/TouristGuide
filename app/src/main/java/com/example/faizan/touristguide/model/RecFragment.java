package com.example.faizan.touristguide.model;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faizan.touristguide.DashBoardInfo;
import com.example.faizan.touristguide.MenuContainer;
import com.example.faizan.touristguide.MyAdapter;
import com.example.faizan.touristguide.R;
import com.example.faizan.touristguide.SpinFrag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebad on 2/13/2015.
 */
public class RecFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public RecFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_frag, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<DashBoardInfo> list = GetData();
        //String [] myDataset = new String[]{"1","2","3"};
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(list,this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currIndex;

                if((SpinFrag.queryinDomain == null)
                        || (SpinFrag.queryinDomain[0] == null || SpinFrag.queryinDomain[0].isEmpty()) ){
                    currIndex =  v.getId();
                }
                else {
                    if (SpinFrag.queryinDomain[1].contains(SpinFrag.queryinValue[0])) {
                        currIndex = 0;
                    } else if (SpinFrag.queryinDomain[1].contains(SpinFrag.queryinValue[1])) {
                        currIndex = 1;
                    } else if (SpinFrag.queryinDomain[1].contains(SpinFrag.queryinValue[2])) {
                        currIndex = 2;
                    } else {
                        currIndex = 3;
                    }
                }

                String typeOfCard  = MyAdapter.mDataset.get(currIndex).title;
                Log.i("--------", "Who was Clicked ?  With Tag " + v.getTag());
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
                Intent intent2 = new Intent(getActivity(), MenuContainer.class);
                intent2.putExtra("type",typeOfCard);

                ActivityCompat.startActivity(getActivity(), intent2, options.toBundle());
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("RootView ","On resume");

        if(SpinFrag.queryinDomain == null)
            return;
        if(SpinFrag.queryinDomain[0] == null || SpinFrag.queryinDomain[0].isEmpty())
            return;


        mRecyclerView.callOnClick();
    }

    public List<DashBoardInfo> GetData()
    {

        List<DashBoardInfo> list = new ArrayList<DashBoardInfo>();
        Resources res = getResources();
        list.add(new DashBoardInfo("Hotels",res.getString(R.string.lrpsm_small),res.getString(R.string.lrpsm_small),R.drawable.main_hotel));
        list.add(new DashBoardInfo("Restaurants",res.getString(R.string.lrpsm_small),res.getString(R.string.lrpsm_small),R.drawable.main_rest));
        list.add(new DashBoardInfo("Rentals",res.getString(R.string.lrpsm_small),res.getString(R.string.lrpsm_small),R.drawable.rentals));
        list.add(new DashBoardInfo("Attractions",res.getString(R.string.lrpsm_small),res.getString(R.string.lrpsm_small),R.drawable.main_tourist_attraction));
        return list;
    }
}