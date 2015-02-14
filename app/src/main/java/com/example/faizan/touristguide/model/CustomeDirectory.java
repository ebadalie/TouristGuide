package com.example.faizan.touristguide.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faizan on 1/26/2015.
 */
public class CustomeDirectory {

    public List<CustomeDirectory> directories;
    public List<String> images;

    public CustomeDirectory(){
        this.directories = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public List<CustomeDirectory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<CustomeDirectory> directories) {
        this.directories = directories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
