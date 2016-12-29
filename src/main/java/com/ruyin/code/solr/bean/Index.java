package com.ruyin.code.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by gbagony on 2016/12/28.
 */
public class Index {
    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String menu;
    @Field
    private String[] cat;
    /*@Field
    private String[] feature;
    @Field
    private float price;
    @Field
    private int popularity;
    @Field
    private boolean isStock;*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String[] getCat() {
        return cat;
    }

    public void setCat(String[] cat) {
        this.cat = cat;
    }

    /*public String[] getFeature() {
        return feature;
    }

    public void setFeature(String[] feature) {
        this.feature = feature;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isStock() {
        return isStock;
    }

    public void setStock(boolean stock) {
        isStock = stock;
    }

    @Override
    public String toString() {
        return "Index{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", menu='" + menu + '\'' +
                ", cat=" + Arrays.toString(cat) +
                ", feature=" + Arrays.toString(feature) +
                ", price=" + price +
                ", popularity=" + popularity +
                ", isStock=" + isStock +
                '}';
    }*/
}
