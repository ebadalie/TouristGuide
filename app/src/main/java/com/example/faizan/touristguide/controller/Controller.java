package com.example.faizan.touristguide.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.faizan.touristguide.model.TouristVenue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebad Ali on 1/25/2015.
 */
public class Controller extends TouristMiddleLayer {

    private Controller(Context context) {
        super(context);
    }

    public static Controller dbController;

    public static Controller GetObject(Context cont)
    {
        if(dbController == null)
            dbController = new Controller(cont);
        return dbController;
    }

    @Override
    public List<TouristVenue> GetVenues(String type) {
        String VenueQuery = "select * from venue";
        String newType =  AdjustTypeVariable(type);
        List<TouristVenue> list = new ArrayList<>();
        VenueQuery = "  type = '"+newType+"'";
        Cursor cursor = this.RunQuery(VenueQuery);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TouristVenue comment = new TouristVenue(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            list.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        this.mDbOject.close();
        return list;
    }

    private String AdjustTypeVariable(String type) {
        if(type.toLowerCase().contains("rest"))
            return "restaurants";
        else  if(type.toLowerCase().contains("hotel"))
            return "hotels";
        else  if(type.toLowerCase().contains("att"))
            return "attractions";
        else
            return "rentals";
    }


}
