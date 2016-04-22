package com.github.jackkell.mimicryproject.entity;


import android.util.Log;

import com.github.jackkell.mimicryproject.MarkovChain;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator extends Entity {
    private String TAG = "Impersonator";

    //TABLE_NAME TABLE
    public static final String TABLE_NAME = "IMPERSONATOR";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String DATE_CREATED = "DATE_CREATED";
    public static final String MARKOV_CHAIN = "MARKOV_CHAIN";

    //The SQL Statement used to create the TABLE_NAME table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR(140) NOT NULL, " +
                    DATE_CREATED + " LONG NOT NULL, " +
                    MARKOV_CHAIN + " TEXT NOT NULL);";

    //Every Impersonator needs a name for identification reasons
    private String name;

    //This stores the date that the Impersonator was created
    private Date dateCreated;

    private MarkovChain markovChain;

    private List<ImpersonatorPost> posts;

    private List<TwitterUser> twitterUsers;

    public Impersonator(){
        this("", new Date(), new MarkovChain());
    }

    public Impersonator(String name) {
        this(name, new MarkovChain());
    }

    public Impersonator(String name, MarkovChain chain){
        this(name, new Date(), chain);
    }

    public Impersonator(String name, Date dateCreated, MarkovChain chain) {
        this(null, name, dateCreated, chain, new ArrayList<TwitterUser>(), new ArrayList<ImpersonatorPost>());
    }

    public Impersonator(Long id, String name, Date dateCreated, MarkovChain chain, List<TwitterUser> twitterUsers, List<ImpersonatorPost> posts) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.markovChain = chain;
        this.twitterUsers = twitterUsers;
        this.posts = posts;

    }

    //Sets the name of the Impersonator.  Will occur when editing Impersonators
    public void setName(String name) {
        this.name = name;
    }

    //Gets the name of the Impersonator.  Used when displaying Impersonators
    public String getName(){
        return this.name;
    }

    //Helps display the date the Impersonator was created
    public Date getDateCreated(){
        return dateCreated;
    }

    public MarkovChain getMarkovChain() {
        return markovChain;
    }

    public List<ImpersonatorPost> getImpersonatorPosts() {
        return posts;
    }

    public List<TwitterUser> getTwitterUsers() {
        return twitterUsers;
    }

    public void addTwitterUser(TwitterUser twitterUser){
        twitterUsers.add(twitterUser);
    }

    public void addPost() {
        String body = markovChain.generatePhrase();
        ImpersonatorPost impersonatorPost = new ImpersonatorPost(id, body);
        posts.add(impersonatorPost);
    }

    public int getPostCount() {
        return posts.size();
    }

    public int getIsFavoritedCount() {
        int isFavoritedCount = 0;
        for (ImpersonatorPost post: posts) {
            isFavoritedCount += post.isFavorited() ? 1 : 0;
        }
        return  isFavoritedCount;
    }

    public int getIsTweetedCount() {
        int isTweetedCount = 0;
        for (ImpersonatorPost post: posts) {
            isTweetedCount += post.isFavorited() ? 1 : 0;
        }
        return  isTweetedCount;
    }
}
