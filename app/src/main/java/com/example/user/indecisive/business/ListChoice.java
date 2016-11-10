package com.example.user.indecisive.business;

/**
 * Created by Eamon on 10/11/2016.
 */

public class ListChoice {

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
}
