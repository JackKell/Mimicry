package com.github.jackkell.mimicryproject.databaseobjects;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.orm.SugarRecord;

import java.util.Date;

//A post created by the Impersonator consisting of a body and several other attributes
public class ImpersonatorPost extends SugarRecord<ImpersonatorPost>{
    //The body of the post.  THe main text
    private String body;
    //Signifies whether the post is favorited or not
    private boolean isFavorited;
    //Signifies whether the post is Tweeted or not
    private boolean isTweeted;
    //Tells when the post was created
    private Date dateCreated;
    // The impersonator that created this impersonator post.
    private Impersonator impersonator;

    public ImpersonatorPost(){}

    //Creates the ImpersonatorPost based on the passed attributes
    public ImpersonatorPost(String body, boolean isFavorited, boolean isTweeted, Date dateCreated, Impersonator impersonator) {
        this.body = body;
        this.isFavorited = isFavorited;
        this.isTweeted = isTweeted;
        this.dateCreated = dateCreated;
        this.impersonator = impersonator;
    }

    //GETTERS AND SETTERS
    public String getBody() {
        return body;
    }

    public void setImpersonator(Impersonator impersonator){
        this.impersonator = impersonator;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public boolean isTweeted() {
        return isTweeted;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
