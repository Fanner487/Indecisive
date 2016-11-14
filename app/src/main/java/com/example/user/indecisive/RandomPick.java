package com.example.user.indecisive;

import com.example.user.indecisive.business.ItemChoice;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Eamon on 11/11/2016.
 */

public class RandomPick {

    public RandomPick() {
    }

    public String getRandomFromList(ArrayList<ItemChoice> items){


        Random rand = new Random();

        int index = rand.nextInt(items.size());

        return items.get(index).getItem();
    }


    private int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;


        return randomNum;
    }
}
