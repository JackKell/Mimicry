package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator extends SugarRecord<Impersonator> {

    //Every Impersonator needs a name for identification reasons
    private String name;
    //This stores the date that the Impersonator was created
    private Date dateCreated;

    public Impersonator(){}

    //Creates an Impersonator base on the attributes passed in.
    public Impersonator(String name, List<TwitterUser> twitterUsers, List<ImpersonatorPost> posts, Date dateCreated){
        this.name = name;
        this.dateCreated = dateCreated;

        for (TwitterUser twitterUser: twitterUsers){
            twitterUser.setImpersonator(this);
            twitterUser.save();
        }

        for (ImpersonatorPost impersonatorPost: posts){
            impersonatorPost.setImpersonator(this);
            impersonatorPost.save();
        }
    }

    //Sets the name of the Impersonator.  Will occur when editing Impersonators
    // TODO: implement sugar stuff here
    public void setName(String name) {
        this.name = name;
    }

    //Gets the name of the Impersonator.  Used when displaying Impersonators
    public String getName(){
        return this.name;
    }

    //Helps display the date the Impersonator was created
    public String getDateCreated(){
        return DatabaseOpenHelper.DAY_FORMAT.format(dateCreated);
    }

    //Grabs the Twitter users associated with the Impersonator
    public List<TwitterUser> getTwitterUsers() {
        return TwitterUser.find(TwitterUser.class, "impersonator = ?", "" + this.getId());
    }

    public List<ImpersonatorPost> getPosts(){
        return ImpersonatorPost.find(ImpersonatorPost.class, "impersonator = ?", "" + this.getId());
    }
}
