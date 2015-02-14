package com.example.faizan.touristguide;

/**
 * Created by Faizan on 1/22/2015.
 */
public class MenuCard {

    protected String name;
    protected String reaLocation;
    protected String desc;
    protected String type;
    protected int imgRes;

    public MenuCard(String name, String reaLocation,  String desc, String type, int imgRes) {
        this.name = name;
        this.reaLocation = fixTheString(reaLocation);
        this.desc = desc;
        this.type = type;
        this.imgRes = imgRes;
    }

    static String fixTheString(String str)
    {
//        str = str.replace("\"","");
        str = str.replaceAll("[^a-zA-Z0-9]", " ");
        return str;
    }
}
