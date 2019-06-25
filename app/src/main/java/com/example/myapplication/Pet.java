package com.example.myapplication;

public class Pet {
    private String Name;
    private String Description;
    private String Available;
    private String Photo;

    public Pet() {

    }

    public Pet(String name, String description, String available, String photo) {

        Name = name;
        Description = description;
        Available = available;
        Photo = photo;
    }

    //Getter
    public String getName() {
        return Name;
    }

    String getDescription() {
        return Description;
    }

    String getAvailable() {
        return Available;
    }

    String getPhoto() {
        return Photo;
    }

    //Setter
    public void setName(String name) {
        Name = name;
    }

}
