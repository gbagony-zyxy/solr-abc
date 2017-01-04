package com.ruyin.code.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by gbagony on 2017/1/3.
 */
public class Article {

    @Field
    private String post_id;
    @Field
    private String author;
    @Field
    private String title;
    @Field
    private String desc;
    @Field
    private String context;
    @Field
    private Date writeDate;
    @Field
    private double price;

    public Article(){}

    public Article(String post_id, String author, String title, String desc, String context, Date writeDate,double price) {
        this.post_id = post_id;
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.context = context;
        this.writeDate = writeDate;
        this.price = price;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Article{" +
                "post_id='" + post_id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", context='" + context + '\'' +
                ", writeDate=" + writeDate +
                ", price=" + price +
                '}';
    }
}
