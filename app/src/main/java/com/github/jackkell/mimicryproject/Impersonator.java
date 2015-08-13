package com.github.jackkell.mimicryproject;

import java.util.List;

public class Impersonator {

    private String namne;
    private List<String> twitterUsers;
    private List<ImpersonatorPost> posts;

    public Impersonator(String name, List<String> twitterUsers, List<ImpersonatorPost> posts){
        this.namne = name;
        this.twitterUsers = twitterUsers;
        this.posts = posts;
    }

    public void setNamne(String namne) {
        this.namne = namne;
    }

    public List<String> getTwitterUsers() {
        return twitterUsers;
    }

    public List<ImpersonatorPost> getPosts() {
        return posts;
    }

    public int GetIsFavoritedPostCount() {
        int favoritedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            favoritedCount += posts.get(index).isFavorited() ? 1 : 0;
        }
        return favoritedCount;
    }

    public int GetIsTweetedPostCount() {
        int tweetedCount = 0;
        for(int index = 0; index < posts.size(); index++) {
            tweetedCount += posts.get(index).isTweeted() ? 1 : 0;
        }
        return tweetedCount;
    }
}
