package com.github.jackkell.mimicryproject.databaseobjects;


import com.github.jackkell.mimicryproject.MarkovChain;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.IOException;
import java.util.Date;
import java.util.List;

//Impersonator is an object used to generate posts based on the vernacular of its Twitter users
public class Impersonator extends SugarRecord<Impersonator> {

    //Every Impersonator needs a name for identification reasons
    private String name;
    //This stores the date that the Impersonator was created
    private Date dateCreated;

    @Ignore
    private MarkovChain markovChain;

    private String markovChainJson;

    public Impersonator(){}

    public Impersonator(String name) {
        this(name, new MarkovChain());
    }

    public Impersonator(String name, MarkovChain chain){
        this.name = name;
        this.dateCreated = new Date();
        this.markovChain = chain;
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

    public void addTwitterUser(String username, List<String> tweets){
        TwitterUser twitterUser = new TwitterUser(username, tweets, this);
        twitterUser.save();
    }

    public void addPost() {
        String phrase = markovChain.generatePhrase();
        ImpersonatorPost impersonatorPost = new ImpersonatorPost(phrase, this);
        impersonatorPost.save();
    }

    public int getPostCount(){
        return getPosts().size();
    }

    public int getTweetCount(){
        List<ImpersonatorPost> impersonatorPosts = getPosts();
        int tweetCount = 0;
        for (ImpersonatorPost impersonatorPost : impersonatorPosts){
            if (impersonatorPost.isTweeted()){
                tweetCount++;
            }
        }
        return tweetCount;
    }

    public int getFavoriteCount(){
        List<ImpersonatorPost> impersonatorPosts = getPosts();
        int FavoritedCount = 0;
        for (ImpersonatorPost impersonatorPost : impersonatorPosts){
            if (impersonatorPost.isFavorited()){
                FavoritedCount++;
            }
        }
        return FavoritedCount;
    }

    public MarkovChain getMarkovChain() throws IOException {
        return markovChain;
    }
}
