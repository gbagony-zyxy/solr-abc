package com.ruyin.code.solr.mysolr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by gbagony on 2017/1/3.
 */
public class SolrClient {

    private static String jetty_solr="http://localhost:9090/solr/mysolr";
    //private static final ThreadLocal<HttpSolrClient> localHolder = new ThreadLocal<>();
    private HttpSolrClient solrClient = null;

    public static HttpSolrClient getSolrClient(){
        return new HttpSolrClient.Builder(jetty_solr).build();
    }
}
