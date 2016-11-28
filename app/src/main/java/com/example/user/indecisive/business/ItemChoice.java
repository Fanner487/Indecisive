package com.example.user.indecisive.business;
/**
 * Created by Eamon on 10/11/2016.
 *
 * Encapsulation for an item in a list
 */

public class ItemChoice {

    final String TAG = ItemChoice.class.getSimpleName();

    private int id;
    private String item;
    private String list;
    private int isDrawer;

    public ItemChoice(int id, String item, String list, int drawer) {
        this.id = id;
        this.item = item;
        this.list = list;
        this.isDrawer = drawer;
    }

    public ItemChoice(String item, String list, int drawer) {
        this.item = item;
        this.list = list;
        this.isDrawer = drawer;
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

    public int getIsDrawer() {
        return isDrawer;
    }

    public void setIsDrawer(int isDrawer) {
        this.isDrawer = isDrawer;
    }

    @Override
    public String toString() {
        return "ItemChoice{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", list='" + list + '\'' +
                ", isDrawer=" + isDrawer +
                '}';
    }
}
