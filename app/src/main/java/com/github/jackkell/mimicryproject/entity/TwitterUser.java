package com.github.jackkell.mimicryproject.entity;

//A Twitter account associated with individual Impersonators
public class TwitterUser extends Entity {

    //TWITTER_USER TABLE
    public static final String TABLE_NAME = "TWITTER_USER";
    public static final String ID = "ID";
    public static final String IMPERSONATOR_ID = "IMPERSONATOR_ID";
    public static final String USERNAME = "USERNAME";
    public static final String LAST_TWEET_ID = "LAST_TWEET_ID";

    //The SQL Statement used to create the TWITTER_USER table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    IMPERSONATOR_ID + " INTEGER NOT NULL, " +
                    USERNAME + " VARCHAR(255) NOT NULL, " +
                    LAST_TWEET_ID + " LONG NOT NULL, " +
                    "FOREIGN KEY(" + IMPERSONATOR_ID + ") REFERENCES " + Impersonator.TABLE_NAME + "(" + Impersonator.ID + "));";

    // The id of the impersonator that this twitter user belongs to
    private Long impersonatorId;

    //The Twitter username
    private String username;

    private Long lastTweetId;

    public TwitterUser(String username){
        this(null, username, null);
    }

    //Creates a Twitter user based on passed attributes
    public TwitterUser(Long impersonatorId, String username, Long lastTweetId) {
        this.username = username;
        this.impersonatorId = impersonatorId;
        this.lastTweetId = lastTweetId;
    }

    public Long getImpersonatorId() {
        return impersonatorId;
    }

    public String getUsername() {
        return username;
    }

    public Long getLastTweetId() {
        return lastTweetId;
    }

    public void setImpersonatorId(Long id) {this.impersonatorId = id;}
}
