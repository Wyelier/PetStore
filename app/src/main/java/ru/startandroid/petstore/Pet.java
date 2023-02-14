package ru.startandroid.petstore;

import java.util.List;

public class Pet {
    private int id;
    private String name;
    private List<String> photoUrls;

//    public Pet(int id=, String name, List<String> photoUrl) {
//        this.id = id;
//        this.name = name;
//        this.photoUrls = photoUrl;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
