package com.github.jackkell.mimicryproject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Jackkell on 12/2/2015.
 */
public class MarkovChain {

    private static final String START_KEY = "_start";
    private static final String END_KEY = "_end";
    private Hashtable<String, Vector<Object>> chain;

    public MarkovChain() {
        chain = new Hashtable<>();
        chain.put(START_KEY, new Vector<>());
        chain.put(END_KEY, new Vector<>());
    }

    public void addPhrase(String phrase) {
        phrase = phrase.trim();
        String[] words = phrase.split(" ");
        if (words.length <= 1) {
            return;
        }
        //Loop through all words
        for (int i = 0; i < words.length ; i++) {
            // If starting word
            if (i == 0) {
                Vector<Object> startingWords = chain.get(START_KEY);
                startingWords.add(words[i]);

                Vector<Object> suffix = chain.get(words[i]);
                if (suffix == null) {
                    suffix = new Vector<>();
                    suffix.add(words[i + 1]);
                    chain.put(words[i], suffix);
                }
            }
            // If ending word
            else if (i == words.length - 1) {
                Vector<Object> endWords = chain.get(END_KEY);
                endWords.add(words[i]);
            }
            // If middle word
            else {
                Vector<Object> suffix = chain.get(words[i]);
                if (suffix == null) {
                    suffix = new Vector<>();
                    suffix.add(words[i+1]);
                    chain.put(words[i], suffix);
                } else {
                    suffix.add(words[i+1]);
                    chain.put(words[i], suffix);
                }
            }
        }
    }

    public String generatePhrase() {
        List<Object> words = new ArrayList<>();

        Object newWord = "";

        Vector<Object> startWords = chain.get(START_KEY);
        Vector<Object> endWords = chain.get(END_KEY);

        int startWordsLen = startWords.size();
        Random random = new Random();
        newWord = startWords.get(random.nextInt(startWordsLen));
        words.add(newWord);

        while (!endWords.contains(newWord)) {
            Vector<Object> wordSelection = chain.get(newWord);
            int wordSelectionLen = wordSelection.size();
            newWord = wordSelection.get(random.nextInt(wordSelectionLen));
            words.add(newWord);
        }
        String newPhrase = "";
        for (Object word: words) {
            newPhrase += word + " ";
        }
        return newPhrase.trim();
    }
}
