package ch.epfl.sweng.swissaffinity.events;



import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.*;
import ch.epfl.sweng.swissaffinity.utilities.*;
/**
 * Created by Max on 16/10/2015.
 */
public abstract class Event {

    private int id;
    private Location location;
    private String name;
    private int maxPeople;
    private Calendar dateBeginning;
    private Calendar dateEnd;
    private int basePrice;
    private ArrayList<Contributor> animators;
    private boolean state;
    private String description;
    private URL imageEventURl;
    private Calendar createdAt;
    private Confirmation confirmation;

    public Contributor getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Contributor createdby) {
        this.createdby = createdby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Calendar getDateBeginning() {
        return dateBeginning;
    }

    public void setDateBeginning(Calendar dateBeginning) {
        this.dateBeginning = dateBeginning;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public ArrayList<Contributor> getAnimators() {
        return animators;
    }

    public void setAnimators(ArrayList<Contributor> animators) {
        this.animators = animators;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getImageEventURl() {
        return imageEventURl;
    }

    public void setImageEventURl(URL imageEventURl) {
        this.imageEventURl = imageEventURl;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Confirmation confirmation) {
        this.confirmation = confirmation;
    }

    private Contributor createdby;



    public Event(int id , Location location , String name , int maxPeople , Calendar dateBeginning,Calendar dateEnd,int basePrice,ArrayList<Contributor> animators,boolean state , String description,URL imageEventURl , Calendar createdAt,Confirmation confirmation , Contributor createdby) {
        this.id=id;
        this.location=location;
        this.name=name;
        this.maxPeople=maxPeople;
        this.dateBeginning=dateBeginning;
        this.dateEnd=dateEnd;
        this.basePrice=basePrice;
        this.animators=animators;
        this.state=state;
        this.description=description;
        this.imageEventURl=imageEventURl;
        this.createdAt=createdAt;
        this.confirmation=confirmation;
        this.createdby=createdby;
    }

}
