package com.ruyin.code.solr;

import com.ruyin.code.solr.bean.Index;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gbagony on 2016/12/28.
 */
public class ServerTest {

    private static String jetty_solr = "http://localhost:9090/solr/zyfm";
    private static HttpSolrClient solrClient = null;

    @Before
    public void init(){
        solrClient = new HttpSolrClient.Builder(jetty_solr).build();
        //socket read timeout
        solrClient.setSoTimeout(1000);
        //连接超时时间
        solrClient.setConnectionTimeout(100);
        //默认每台主机最大客户端连接请求数
        solrClient.setDefaultMaxConnectionsPerHost(100);
        //
        solrClient.setMaxTotalConnections(100);
        //是否跟踪重定向,默认false
        solrClient.setFollowRedirects(false);
        //服务器是否允许压缩
        solrClient.setAllowCompression(true);
    }

    @After
    public void destroy(){
        solrClient = null;
    }

    //根据查询字段查询内容
    public static void query(String query) throws IOException, SolrServerException {
        SolrParams params = new SolrQuery(query);

        QueryResponse response = solrClient.query(params);
        SolrDocumentList list = response.getResults();
        list.stream().forEach(s-> System.out.println(s));
    }

    //添加文档,在managed-schema中设置uniqueKey为id,就表示id是唯一的,如果在添加document的时候id重复添加，那么后面添加的相同id的doc会覆盖前面的doc,类似于update操作,而不会出现重复的数据
    public static void  addDoc() throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",1);
        doc.addField("name","Solr Input Document");
        doc.addField("menu","This is SolrInputDocument content.");

        UpdateResponse response = solrClient.add(doc);
        solrClient.commit();
        System.out.println("QTime :"+response.getQTime());
        System.out.println("Elapsed Time :"+response.getElapsedTime());
        System.out.println("Url :"+response.getRequestUrl());
        System.out.println("Status :"+response.getStatus());

        //query("name:Solr");
    }

    //添加多个document
    public static void addDocs() throws IOException, SolrServerException {
        Collection<SolrInputDocument> docs = new ArrayList<>();

        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",2);
        doc.addField("name","Solr Input Document 2");
        doc.addField("menu","This is SolrInputDocument 2 content.");
        docs.add(doc);

        doc = new SolrInputDocument();
        doc.addField("id",3);
        doc.addField("name","Solr Input Document 3");
        doc.addField("menu","This is SolrInputDocument 3 content.");
        docs.add(doc);

        UpdateResponse response = solrClient.add(docs);
        solrClient.commit();

        query("solr");
    }

    public static void addBean() throws IOException, SolrServerException {
        Index index = new Index();
        index.setId("4");
        index.setName("Add bean index");
        index.setMenu("Index bean menu");
        index.setCat(new String[]{"abc","efg"});

        UpdateResponse response = solrClient.addBean(index);
        solrClient.commit();

        query("*:*");
    }

    @Test
    public void test() throws IOException, SolrServerException {
        //addDoc();
        //query("id:1");
        //addDocs();
        addBean();
    }
}
