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

    public ItemChoice getRandomFromList(ArrayList<ItemChoice> items){


        Random rand = new Random();

        int index = rand.nextInt(items.size());

        return items.get(index);
    }


}
