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
    private String description;
    @Field
    private String context;
    @Field
    private Date writeDate;

    public Article(){}

    public Article(String post_id, String author, String title, String description, String context, Date writeDate) {
        this.post_id = post_id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.context = context;
        this.writeDate = writeDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Article{" +
                "post_id='" + post_id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", context='" + context + '\'' +
                ", writeDate=" + writeDate +
                '}';
    }
}
