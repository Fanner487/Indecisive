package com.example.user.indecisive.business;

/**
 * Created by Eamon on 10/11/2016.
 */

public class ItemChoice {

    private int id;
    private String item;
    private String list;
    private int drawer;

    public ItemChoice(int id, String item, String list, int drawer) {
        this.id = id;
        this.item = item;
        this.list = list;
        this.drawer = drawer;
    }

    public ItemChoice(String item, String list, int drawer) {
        this.item = item;
        this.list = list;
        this.drawer = drawer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public int getDrawer() {
        return drawer;
    }

    public void setDrawer(int drawer) {
        this.drawer = drawer;
    }
}
