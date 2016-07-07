package helper;

import java.io.Serializable;

/**
 * Created by Vinit on 08-04-2016.
 */
//POJO class for attraction
public class Attraction implements Serializable {
    int id;
    String name;
    String Description;
    double latitude;
    double longitude;
    String openhours;
    String closehours;
    String address;
    String videoLink;
    float rating;
    String picture;

    //TODO:  picture address needs to be added, I dont know the style it is in sql,just set as string


    public Attraction( int id, String name, String Description,
                       double latitude, double longitude,
                       String openhours, String closehours,
                       String address, String videoLink,
                       float rating ,String picture) {//TODO:picture address
        this.id = id;
        this.name = name;
        this.Description = Description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openhours = openhours;
        this.closehours = closehours;
        this.address = address;
        this.videoLink = videoLink;
        this.rating = rating;

        this.picture = picture;//TODO:picture address
    }

    public Attraction(int bean, String s, String s1) {
    }


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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double lat) {
        this.latitude = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double lon) {
        this.longitude = lon;
    }

    public String getOpenhours(){
        return openhours;
    }

    public void setOpenhours(String oh){
        this.openhours=oh;
    }

    public String getClosehours(){
        return openhours;
    }

    public void setClosehours(String ch){
        this.closehours=ch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addr) {
        this.address = addr;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String vl) {
        this.videoLink = vl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rt) {
        this.rating = rt;
    }

    //TODO:picture address
    public String getPicture() {
        return picture;
    }
    //TODO:picture address
    public void setPicture(String pic) {
        this.picture = pic;
    }

}
