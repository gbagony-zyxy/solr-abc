package com.ruyin.code.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by gbagony on 2016/12/29.
 */
public class User implements Serializable,Cloneable{

    private static final long serialVersionUID = -8006507617468514875L;

    @Field
    private int id;
    @Field
    private String name;
    @Field
    private int age;
    @Field
    private String[] hobbies;
    @Field
    private String address;
    @Field
    private String sex;
    @Field
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

}
