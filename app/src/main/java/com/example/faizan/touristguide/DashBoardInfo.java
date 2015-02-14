package com.example.faizan.touristguide;

/**
 * Created by Faizan on 1/19/2015.
 */
public class DashBoardInfo {
    public String title;
    public String name;
    public String email;
    public int imageRes;
    public static final String NAME_PREFIX = "Name_";
    public static final String SURNAME_PREFIX = "Surname_";
    public static final String EMAIL_PREFIX = "email_";


    public DashBoardInfo(String str1,String str2,String str3, int imageRes)
    {
        this.title = str1;
        this.name = str2;
        this.email = str3;
        this.imageRes = imageRes;
    }
}
