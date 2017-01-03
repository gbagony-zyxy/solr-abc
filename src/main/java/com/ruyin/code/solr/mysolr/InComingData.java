package com.ruyin.code.solr.mysolr;

import com.ruyin.code.solr.bean.Article;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
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
        Article article1 = new Article("1001", "David", "How to live...", "Worth our to think...", "Just to fight!", new Date());
        Article article2 = new Article("1002", "Jerry", "How we dead...", "Worth our to read...", "Just to fly!", new Date());
        Article article3 = new Article("1003", "Tom", "How to think...", "Worth our to do...", "Just to go!", new Date());
        Article article4 = new Article("1004", "John", "How to relax...", "Worth our to reflection...", "Just run!", new Date());
        Article article5 = new Article("1005", "Bob", "How to breath...", "Worth our to try...", "Just do it!", new Date());
        List<Article> articles = Arrays.asList(article1, article2, article3, article4, article5);
        HttpSolrClient client = SolrClient.getSolrClient();
        UpdateResponse response = client.addBeans(articles);
        client.commit();
        response.getStatus();
    }
}