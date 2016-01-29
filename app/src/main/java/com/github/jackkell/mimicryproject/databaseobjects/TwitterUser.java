package com.github.jackkell.mimicryproject.databaseobjects;

import com.orm.SugarRecord;
import java.util.List;

//A Twitter account associated with individual Impersonators
public class TwitterUser extends SugarRecord<TwitterUser> {
    //The Twitter username
    private String username;

    // The id of the impersonator that this twitter user belongs to
    private Impersonator impersonator;

    public TwitterUser(){}

    //Creates a Twitter user based on passed attributes
    public TwitterUser(String username, List<String> tweets, Impersonator impersonator) {
        TwitterUser twitterUser = this;
        this.username = username;
        this.impersonator = impersonator;

        // TODO: Later in the application try not to have sugar calls within classes only outside classes
        for (String currentTweet : tweets) {

            MimicryTweet tweet = new MimicryTweet(currentTweet, twitterUser);
            tweet.save();
        }
    }

    //GETTER
    public String getUsername() {
        return username;
    }
}
