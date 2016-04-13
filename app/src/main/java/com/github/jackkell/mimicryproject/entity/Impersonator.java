package com.github.jackkell.mimicryproject.entity;


import com.github.jackkell.mimicryproject.MarkovChain;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator extends Entity {

    //TABLE_NAME TABLE
    public static final String TABLE_NAME = "TABLE_NAME";
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

    public Impersonator(){
        name = "Name";
        dateCreated = new Date();
        markovChain = new MarkovChain();
    }

    public Impersonator(String name) {
        this(name, new MarkovChain());
    }

    public Impersonator(String name, MarkovChain chain){
        this(name, new Date(), chain);
    }

    public Impersonator(String name, Date dateCreated, MarkovChain chain) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.markovChain = chain;
    }

    public Impersonator(String name, Date dateCreated, JSONObject twitterUsers, MarkovChain chain) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.markovChain = chain;
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

    public void addTwitterUser(String username, List<String> tweets){
        //TwitterUser twitterUser = new TwitterUser(username, tweets, this);
        //twitterUser.save();
    }

    public void addPost() {
        String phrase = markovChain.generatePhrase();
        ImpersonatorPost impersonatorPost = new ImpersonatorPost(id, phrase);
        //impersonatorPost.save();
    }

    public MarkovChain getMarkovChain() {
        return markovChain;
    }
}
