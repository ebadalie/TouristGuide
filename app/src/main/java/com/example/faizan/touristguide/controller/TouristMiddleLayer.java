package com.example.faizan.touristguide.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.faizan.touristguide.AppDataBaseHandler;
import com.example.faizan.touristguide.MainActivity;
import com.example.faizan.touristguide.model.TouristVenue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ebad Ali on 1/25/2015.
 */
public abstract class TouristMiddleLayer {

    AppDataBaseHandler mDbOject ;

    TouristMiddleLayer(Context context)
    {
        mDbOject = new AppDataBaseHandler(context);

        try {
            mDbOject.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void OpenDB()
    {
        try {
            mDbOject.openDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Cursor RunQuery(String query)
    {
        this.OpenDB();

        Cursor cObject =  mDbOject.getReadableDatabase().query("venue",null,
                query,null,"","","");

        return cObject;
    }

    private void CloseDB()
    {
        mDbOject.close();
    }

    public void InsertData()
    {
        OpenDB();

        // Do your task

        CloseDB();
    }

    public abstract List<?> GetVenues(String type);

}
