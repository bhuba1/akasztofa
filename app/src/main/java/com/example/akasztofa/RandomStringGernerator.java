package com.example.akasztofa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStringGernerator {
    private List<String> words;

    public RandomStringGernerator() {
        this.words = new ArrayList<>();
        this.words.add("Macska");
        this.words.add("Kutya");
        this.words.add("Zsiráf");
        this.words.add("Asztal");
        this.words.add("Szék");
        this.words.add("Cica");
        this.words.add("Űrhajó");
        this.words.add("Épület");
        this.words.add("Ház");
        this.words.add("Terasz");
        this.words.add("Alma");
        this.words.add("Banán");
        this.words.add("Krumpli");
    }

    public String getRandomString() {
        Random r = new Random();
        int random = r.nextInt(words.size());

        return  words.get(random);
    }
}
