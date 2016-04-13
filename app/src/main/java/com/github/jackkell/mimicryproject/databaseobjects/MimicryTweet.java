package com.github.jackkell.mimicryproject.databaseobjects;

public class MimicryTweet{
    private String body;
    private TwitterUser twitterUser;

    public MimicryTweet(){}

    public MimicryTweet(String body, TwitterUser twitterUser) {
        this.body = body;
        this.twitterUser = twitterUser;
    }

    public String getBody() {
        return body;
    }

    public TwitterUser getTwitterUser() {
        return twitterUser;
    }
}
