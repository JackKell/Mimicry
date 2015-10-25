package com.github.jackkell.mimicryproject.databaseobjects;

import com.orm.SugarRecord;

public class MimicryTweet extends SugarRecord<MimicryTweet> {
    private String body;
    private TwitterUser twitterUser;

    public MimicryTweet(String body, TwitterUser twitterUser) {
        this.body = body;
        this.twitterUser = twitterUser;
    }

    public String getBody() {
        return body;
    }
}
