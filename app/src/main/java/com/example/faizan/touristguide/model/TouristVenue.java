package com.example.faizan.touristguide.model;

import android.content.Context;
import android.database.Cursor;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by Ebad Ali on 1/25/2015.
 */
public class TouristVenue  {

    int id;
    String location,name,description,type;

    public TouristVenue(int id, String location, String name, String description, String type) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
