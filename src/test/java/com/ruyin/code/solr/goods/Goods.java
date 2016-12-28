package com.ruyin.code.solr.goods;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by gbagony on 2016/12/28.
 */
public class Goods {
    @Field
    private String goodsId;
    @Field
    private String title;
    @Field
    private String siteId;
    @Field
    private int purchaseNum;
    @Field
    private Date startTime;
    @Field
    private double disCount;
    @Field
    private double curPrice;

    private Goods(Builder builder){
        this.goodsId = builder.goodsId;
        this.title = builder.title;
        this.siteId = builder.siteId;
        this.purchaseNum = builder.purchaseNum;
        this.startTime = builder.startTime;
        this.disCount = builder.disCount;
        this.curPrice = builder.curPrice;
    }

    public static class Builder{
        private String goodsId;
        private String siteId;
        private int purchaseNum;
        private double curPrice;

        private String title;
        private Date startTime;
        private double disCount;

        public Builder(String goodsId,String siteId,int purchaseNum,double curPrice){
            this.goodsId = goodsId;
            this.siteId = siteId;
            this.purchaseNum = purchaseNum;
            this.curPrice = curPrice;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder startTime(Date startTime){
            this.startTime = startTime;
            return this;
        }

        public Builder disCount(double disCount){
            this.disCount = disCount;
            return this;
        }

        public Goods build(){
            return new Goods(this);
        }
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(int purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public double getDisCount() {
        return disCount;
    }

    public void setDisCount(double disCount) {
        this.disCount = disCount;
    }

    public double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(double curPrice) {
        this.curPrice = curPrice;
    }
}
