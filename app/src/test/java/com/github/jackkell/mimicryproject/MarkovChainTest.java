package com.github.jackkell.mimicryproject;

import android.support.v4.util.Pair;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarkovChainTest {

    @Test
    public void MarkovChainTest() {
        String phrase1 = "The dog ran down the hill.";
        String phrase2 = "I ran the dog over with my car.";
        String phrase3 = "I wanted to go up the hill.";
        List<String> phrases = new ArrayList<>();
        phrases.add(phrase1);
        phrases.add(phrase2);
        phrases.add(phrase3);

        MarkovChain markovChain = new MarkovChain();
        markovChain = new MarkovChain(phrases);

        markovChain.addPhrase("When I grow up I want to be Super Man!");

        String phrase4 = "The end is coming.";
        String phrase5 = "I want to be the very best like no one ever was.";
        String phrase6 = "I will travel across the land";

        phrases = new ArrayList<>();

        phrases.add(phrase4);
        phrases.add(phrase5);
        phrases.add(phrase6);

        markovChain.addPhrases(phrases);

        assertEquals("dog", markovChain.chain.get("The").get(0).first);
        assertEquals(Long.valueOf(1L), markovChain.chain.get("The").get(0).second);

        assertEquals("hill.", markovChain.chain.get("the").get(0).first);
        assertEquals(Long.valueOf(2L), markovChain.chain.get("the").get(0).second);
        assertEquals("dog", markovChain.chain.get("the").get(1).first);
        assertEquals(Long.valueOf(1L), markovChain.chain.get("the").get(1).second);
    }

    @Test
    public void JSONTest() {
        String phrase1 = "The dog ran down the hill.";
        String phrase2 = "I ran with my dog over with my car.";
        String phrase3 = "I wanted to go up the hill.";
        List<String> phrases = new ArrayList<>();
        phrases.add(phrase1);
        phrases.add(phrase2);
        phrases.add(phrase3);

        MarkovChain markovChain1 = new MarkovChain(phrases);
        System.out.println("Markov Chain 1: Created scratch then Changed to JSON");
        String chainString1 = markovChain1.toJson().toString();
        System.out.println(chainString1);

        MarkovChain markovChain2 = new MarkovChain(markovChain1.toJson());

        System.out.println("Markov Chain 2: Created from JSON the Change to Chain then to JSON");
        String chainString2 = markovChain2.toJson().toString();
        System.out.println(markovChain2.toJson().toString());

        assertEquals(chainString1, chainString2);
    }

    @Test
    public void GeneratePhraseTest() {
        String phrase1 = "The dog ran down the hill.";
        String phrase2 = "I ran to the park with my dog and had a nice day.";
        String phrase3 = "I wanted to go up the hill.";
        List<String> phrases = new ArrayList<>();
        phrases.add(phrase1);
        phrases.add(phrase2);
        phrases.add(phrase3);

        MarkovChain markovChain = new MarkovChain(phrases);

        System.out.println("Original \"Posts\":");
        System.out.println(phrase1);
        System.out.println(phrase2);
        System.out.println(phrase3);

        System.out.println();

        List<String> uniquePosts = new ArrayList<>();
        System.out.println("New Test \"Posts\":");
        for (int i = 0; i < 100; i++) {
            String newPost = markovChain.generatePhrase();
            if (!uniquePosts.contains(newPost)) {
                uniquePosts.add(newPost);
                System.out.println(newPost);
            }
        }
        System.out.println();
    }
}
