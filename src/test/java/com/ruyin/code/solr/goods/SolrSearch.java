package com.ruyin.code.solr.goods;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.*;

/**
 * Created by gbagony on 2016/12/28.
 */
public class SolrSearch {

    private static String jetty_solr= "http://localhost:9090/solr";
    private static HttpSolrClient client = null;

    private static List<Goods> goodsList = new ArrayList<>();

    //init
    public static void initiate(){
        try {
            client = new HttpSolrClient.Builder(jetty_solr).build();
            client.setConnectionTimeout(100);
            client.setDefaultMaxConnectionsPerHost(100);
            client.setMaxTotalConnections(100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //检测good是否有字段为空

    //将list添加到索引,flag为true在原基础上添加,为false重新添加、原索引不会删除
    public static void addGoods(List<Goods> goodsList,boolean flag) throws IOException, SolrServerException {
        if(flag){
            client.deleteByQuery("*:*");
        }
        Collection<SolrInputDocument> docs = new ArrayList<>();

        goodsList.stream().forEach(goods -> {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("goodsId",goods.getGoodsId());
            doc.addField("title",goods.getTitle());
            doc.addField("siteId",goods.getSiteId());
            doc.addField("purchaseNum",goods.getPurchaseNum());
            doc.addField("statTime",goods.getStartTime().toString());
            doc.addField("disCount",goods.getDisCount());
            doc.addField("curPrice",goods.getCurPrice());
            docs.add(doc);
        });

        client.add(docs);
        client.optimize();
        client.commit();
    }

    //删除所有的索引
    public static void deleteAllIndex() throws IOException, SolrServerException {
        client.deleteByQuery("*:*");
        client.commit();
    }

    //删除指定的索引
    public static void deleteIndex(List<String> ids) throws IOException, SolrServerException {
        client.deleteById(ids);
        client.commit();
    }

    public static QueryResponse search(String[] field,String[] key,int start,
                                       int count,String[] sortfield,boolean[] flag,boolean highlight){
        if(field == null || key == null || field.length != key.length)
            return null;
        if(sortfield == null || flag == null || sortfield.length != flag.length)
            return null;
        SolrQuery query = null;

        //if(field[0].equals("title"))
        query = new SolrQuery(field[0]+":"+key[0]);
        //Arrays.stream(field).forEach(f->{});
        return null;
    }
}
