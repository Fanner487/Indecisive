package com.example.user.indecisive.business;

/**
 * Created by Eamon on 10/11/2016.
 *
 * Encapsulates a list
 */

public class ListChoice {

    final String TAG = ListChoice.class.getSimpleName();

    private String listName;
    private int isDrawer;

    public ListChoice(String name, int isDrawer) {
        this.listName = name;
        this.isDrawer = isDrawer;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String name) {
        this.listName = name;
    }

    public int getIsDrawer() {
        return isDrawer;
    }

    public void setIsDrawer(int isDrawer) {
        this.isDrawer = isDrawer;
    }


    @Override
    public String toString() {
        return "ListChoice{" +
                "listName='" + listName + '\'' +
                ", isDrawer=" + isDrawer +
                '}';
    }
}
