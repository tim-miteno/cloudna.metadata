package com.miteno.cloudna.model;

/**
 * Created by tim on 18/04/2017.
 */
public class Sorter {

    public enum ORDER {ASC, DESC};

    private String key;
    private ORDER order;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ORDER getOrder() {
        return order;
    }

    public void setOrder(ORDER order) {
        this.order = order;
    }
}
