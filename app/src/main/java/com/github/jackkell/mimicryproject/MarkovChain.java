package com.github.jackkell.mimicryproject;

import android.support.v4.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;



public class MarkovChain {
    // These keys are used to bookend the beginning and end of the hashmap
    private static final String START_KEY = "Start49c9f8067fe4468184af16d7576940c9";
    private static final String END_KEY = "End5adcc130362394d82279fe2aaba135f0";

    // If a key this is equal to the start or end key is used then it will be changed to the alternative value
    private static final String START_KEY_ALT = "_start";
    private static final String END_KEY_ALT = "_end";
    public Map<String, List<Pair<String, Long>>> chain;

    public MarkovChain() {
        this(new ArrayList<String>());
    }

    public MarkovChain(List<String> phrases) {
        chain = new HashMap<>();
        chain.put(START_KEY, new ArrayList<Pair<String, Long>>());

        addPhrases(phrases);
    }

    public MarkovChain(JSONObject json) {
        chain = new HashMap<>();
        try {
            Iterator<String> keyIterator = json.keys();

            while(keyIterator.hasNext()) {
                String mapKey = keyIterator.next();
                JSONObject currentValues = json.getJSONObject(mapKey);
                List<Pair<String, Long>> mapValue = new ArrayList<>();

                Iterator<String> valuesIterator = currentValues.keys();
                while (valuesIterator.hasNext()) {
                    String key = valuesIterator.next();
                    Long value = currentValues.getLong(key);
                    Pair<String, Long> pair = new Pair<>(key, value);
                    mapValue.add(pair);
                }
                chain.put(mapKey, mapValue);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToChain(String key, String value) {
        List<Pair<String, Long>> pairs = chain.get(key);
        // If the give key is the not found in the hash
        if (pairs == null) {
            pairs = new ArrayList<>();
            Pair<String, Long> pair = new Pair<>(value, 1L);
            pairs.add(pair);
            chain.put(key, pairs);
        } else {
            for (int pairIndex = 0; pairIndex < pairs.size(); pairIndex++) {
                Pair<String, Long> currentPair = pairs.get(pairIndex);
                // If the new value is in the list and just needs to be incremented
                if (Objects.equals(currentPair.first, value)) {
                    pairs.set(pairIndex, new Pair<>(currentPair.first, currentPair.second + 1L));
                    chain.put(key, pairs);
                    return;
                }
            }
            // If the new value is not in the list and needs to be added
            Pair<String, Long> pair = new Pair<>(value, 1L);
            pairs.add(pair);
            chain.put(key, pairs);
        }
    }

    public void addPhrase(String phrase) {
        phrase = phrase.trim();
        List<String> words = new ArrayList<>(Arrays.asList(phrase.split(" ")));
        if (words.size() <= 1) {
            return;
        }

        for (int wordIndex = 0; wordIndex < words.size(); wordIndex++) {
            String currentWord = words.get(wordIndex);
            currentWord = Objects.equals(currentWord, START_KEY) ? START_KEY_ALT : currentWord;
            currentWord = Objects.equals(currentWord, END_KEY) ? END_KEY_ALT : currentWord;
            if (wordIndex == 0) {
                addToChain(START_KEY, currentWord);
                addToChain(currentWord, words.get(wordIndex + 1));
            }
            else if (wordIndex == words.size() - 1) {
                addToChain(currentWord, END_KEY);
            }
            else {
                addToChain(currentWord, words.get(wordIndex + 1));
            }
        }
    }

    public void addPhrases(List<String> phrases) {
        for (String phrase : phrases) {
            addPhrase(phrase);
        }
    }

    private String getRandomWeightedWordFromKey (String key) {
        List<Pair<String, Long>> pairs = chain.get(key);

        // Get the total number of occurrences of all the values of a
        // given key to help determine a random
        Long totalWeight = 0L;
        for (Pair<String, Long> pair : pairs) {
            totalWeight += pair.second;
        }
        Random random = new Random();
        // Generate a long number between 0 - totalOccurrences exclusive
        Long randomValue = (random.nextLong() % totalWeight);
        for (Pair<String, Long> pair : pairs) {
            if (randomValue < pair.second) {
                return pair.first;
            } else {
                randomValue -= pair.second;
            }
        }
        return "";
    }

    public String generatePhrase() {
        List<String> words = new ArrayList<>();

        String currentWord = "";
        currentWord = getRandomWeightedWordFromKey(START_KEY);
        while (!Objects.equals(currentWord, END_KEY)) {
            words.add(currentWord);
            currentWord = getRandomWeightedWordFromKey(currentWord);
        }
        //currentWord = getRandomWeightedWordFromKey(END_KEY);
        //words.add(currentWord);

        String phrase = "";
        for (int i = 0; i < words.size(); i++) {
            if (i == words.size() - 1) {
                phrase += words.get(i);
            } else {
                phrase += words.get(i) + " ";
            }
        }

        return phrase;
    }

    public JSONObject toJson() {
        JSONObject emptyJsonObject = new JSONObject();
        try {
            JSONObject chainObject = new JSONObject();
            Set<String> stringSet = chain.keySet();
            for (String key : stringSet) {
                List<Pair<String, Long>> pairs = chain.get(key);
                JSONObject value = new JSONObject();
                for (Pair<String, Long> pair : pairs) {
                    value.put(pair.first, pair.second);
                }
                chainObject.put(key, value);
            }
            return  chainObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return emptyJsonObject;
    }
}
