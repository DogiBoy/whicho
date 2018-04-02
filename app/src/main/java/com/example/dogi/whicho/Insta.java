package com.example.dogi.whicho;

/**
 * Created by Dogi on 5.03.2018.
 */

public class Insta {
    private String title,image,image2;
    public Insta(){

    }
    public Insta (String title , String image , String image2){
        this.title = title;
        this.image = image;
        this.image2 = image2;

    }
    public String getTitle(){
        return title;
    }
    public String getImage(){
        return image;
    }
    public String getImage2(){return image2;}

    public void setTitle(String title){
        this.title = title;
    }
    public void setImage(String image){
        this.image = image;
    }
    public void setImage2(String image2){this.image2 = image2;}


}
