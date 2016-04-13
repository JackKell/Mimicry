package com.github.jackkell.mimicryproject.entity;

import java.util.Date;

//A post created by the Impersonator consisting of a body and several other attributes
public class ImpersonatorPost extends Entity {

    //POST TABLE
    public static final String TABLE_NAME = "POST";
    public static final String ID = "ID";
    public static final String IMPERSONATOR_ID = "IMPERSONATOR_ID";
    public static final String BODY = "BODY";
    public static final String IS_FAVORITED = "IS_FAVORITED";
    public static final String IS_TWEETED = "IS_TWEETED";
    public static final String DATE_CREATED = "DATE_CREATED";

    //The SQL Statement used to create the POST table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_ID + " LONG NOT NULL, " +
                    BODY + " TEXT NOT NULL, " +
                    IS_FAVORITED + " INTEGER NOT NULL, " +
                    IS_TWEETED + " INTEGER NOT NULL, " +
                    DATE_CREATED + "LONG NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_ID + ") REFERENCES " + Impersonator.TABLE_NAME + "(" + Impersonator.ID + "));";

    private Long impersonatorId;
    //The body of the post.  THe main text
    private String body;
    //Signifies whether the post is favorited or not
    private Boolean isFavorited;
    //Signifies whether the post is Tweeted or not
    private Boolean isTweeted;
    //Tells when the post was created
    private Date dateCreated;

    public ImpersonatorPost(){}

    public ImpersonatorPost(Long impersonatorId, String body) {
        this(impersonatorId, body, false, false, new Date());
    }

    public ImpersonatorPost(Long impersonatorId, String body, boolean isFavorited, boolean isTweeted, Date dateCreated) {
        this.impersonatorId = impersonatorId;
        this.body = body;
        this.isFavorited = isFavorited;
        this.isTweeted = isTweeted;
        this.dateCreated = dateCreated;
    }

    //GETTERS AND SETTERS
    public Long getImpersonatorId() {
        return impersonatorId;
    }

    public String getBody() {
        return body;
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
