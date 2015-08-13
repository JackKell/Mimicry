package com.github.jackkell.mimicryproject;

import java.util.Date;

public class ImpersonatorPost {
    private String body;
    private boolean isFavorited;
    private boolean isTweeted;
    private Date dateCreated;

    public ImpersonatorPost(String body, boolean isFavorited, boolean isTweeted, Date dateCreated) {
        this.body = body;
        this.isFavorited = isFavorited;
        this.isTweeted = isTweeted;
        this.dateCreated = dateCreated;
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
