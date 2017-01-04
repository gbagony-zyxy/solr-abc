package com.ruyin.code.solr.mysolr;

import com.ruyin.code.solr.bean.Article;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by gbagony on 2017/1/3.
 */
public class InComingData {

    public static void main(String[] args) throws IOException, SolrServerException {
        importData();
        //removeData();
    }

    private static void importData() throws IOException, SolrServerException {
        Article article1 = new Article("1001", "鲁迅", "纪念刘和珍君", "有的人死了，她还活着...", "不在沉默中死亡，就在沉默中爆发！", new Date(),20);
        Article article2 = new Article("1002", "海子", "How we dead...", "Worth our to read...", "Just to fly!", new Date(),50);
        Article article3 = new Article("1003", "Tom", "How to think...", "Worth our to do...", "Just to go!", new Date(),100);
        Article article4 = new Article("1004", "John", "How to relax...", "Worth our to reflection...", "Just run!", new Date(),120);
        Article article5 = new Article("1005", "Bob", "How to breath...", "Worth our to try...", "Just do it!", new Date(),111);
        List<Article> articles = Arrays.asList(article1, article2, article3, article4, article5);
        HttpSolrClient client = SolrClient.getSolrClient();
        UpdateResponse response = client.addBeans(articles);
        client.commit();
        response.getStatus();
    }

    private static void removeData() throws IOException, SolrServerException {
        HttpSolrClient client = SolrClient.getSolrClient();
        client.deleteByQuery("*:*");
        client.commit();
    }

    private static void updateData(){
        UpdateRequest request = new UpdateRequest();
        request.add("");
    }
}