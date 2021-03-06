package com.ruyin.code.solr.mysolr;

import com.ruyin.code.solr.bean.Article;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.*;

/**
 * Created by gbagony on 2017/1/3.
 */
public class InComingData {

    public static void main(String[] args) throws IOException, SolrServerException {
        //atomicSetUpdate();
        //atomicAddUpdate();
        atomicIncUpdate();
        //importData();
        //removeData();
        //queryHl();
    }

    private static void importOneData(){
        Article article = new Article("2017","cctv","昔年","","",new Date(),111);
    }

    private static void importData() throws IOException, SolrServerException {
        Article article1 = new Article("1001", "鲁迅", "纪念刘和珍君", "有的人死了，她还活着...", "真的猛士，敢于直面惨淡的人生，敢于正视淋漓的鲜血。这是怎样的哀痛者和幸福者？然而造化又常常为庸人设计，以时间的流驶，来洗涤旧迹，仅使留下淡红的血色和微漠的悲哀。在这淡红的血色和微漠的悲哀中，又给人暂得偷生，维持着这似人非人的世界。我不知道这样的世界何时是一个尽头！", new Date(),20);
        Article article2 = new Article("1002", "海子", "面朝大海", "做一个幸福的人", "面朝大海，春暖花开", new Date(),50);
        Article article3 = new Article("1003", "司马迁abaqus软件", "史记ABAQUS", "史家之绝唱，无韵之离骚。Nice to meet you,abaqus软件现在很populate", "第一部编年体通史。", new Date(),100);
        Article article4 = new Article("1004", "John", "How to relax...", "Worth you to reflection...", "One of the major benefits of the Spring IO Platform is that it provides a set of versions that are known to work together," +
                " while also allowing you to override those versions to suit the needs of your project.Both the Spring IO Platform bom, " +
                "and the Spring Boot bom from which it inherits, use properties to define the versions of the managed dependencies. " +
                "To change the version of a dependency the value of its version property can be overridden. To identify the property that you wish to override, " +
                "consult the <properties> sections of the Spring IO Platform bom and the Spring Boot bom from which it inherits. " +
                "Exactly how the property is overridden depends on whether your project is built with Maven or Gradle.", new Date(),120);
        Article article5 = new Article("1005", "Bob", "How to breath...", "Worth our to try...", "Just do it!", new Date(),111);

        Article article6 = new Article("1006", "wxz", "To be strongger...", "No give up ,no cry...", "A little porblem", new Date(),115);
        Article article7 = new Article("1007", "bbb", "活着", "活着就是一种修行", "不让回忆暗淡，努力修行", new Date(),50);
        Article article8 = new Article("1008", "<font color=\"red\">大人物</font>", "<a href=\"www.baidu.com\">百度竞价,害人害己</a>", "<p>一切都他妈是为了钱</p>", "<h3>只要能赚钱，什么黑医院都能上头条。。。</h3>", new Date(),666666666);
        List<Article> articles = Arrays.asList(article1, article2, article3, article4, article5,article6,article7,article8);
        HttpSolrClient client = SolrClient.getSolrClient();
        //client.setParser(new DelegationTokenResponse.JsonMapResponseParser());
        //client.setParser(new XMLResponseParser());
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

    private static void queryHl() throws IOException, SolrServerException {

        SolrQuery query = new SolrQuery("guide:*our*");
        //query.setRequestHandler("/search");
        query.setHighlight(true);
        query.setParam("hl.fl","author,title,desc,context");
        query.setHighlightSnippets(1);
        query.setHighlightFragsize(200);
        query.setHighlightSimplePre("<font color=\"red\">");
        query.setHighlightSimplePost("</font>");
        query.setParam("start","0");
        query.setParam("rows","1");

        QueryResponse response = SolrClient.getSolrClient().query(query);
        SolrDocumentList ls = response.getResults();
        //response 获取bean也是从results当中获取的，所以修改别名只适用于results域，而对于highlight域则不适用(不支持别名)
        //response.getBeans();
        SolrDocument doc = null;

        Map<String, Map<String, List<String>>> map = response.getHighlighting();
        ls.stream().forEach(s-> System.out.println(s));

    }

    //Solr 支持的原子更新，减少内存以及cpu的损耗
    public static void atomicSetUpdate() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("post_id","1006");
        Map<String,String> map = new HashMap<>();
        map.put("set","Solr使用的基本规则");
        document.addField("title",map);
        HttpSolrClient client = SolrClient.getSolrClient();
        client.add(document);
        client.commit();
    }

    public static void atomicAddUpdate() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("post_id","1006");
        Map<String,String> map = new HashMap<>();
        map.put("add","This is a big problem...");
        document.addField("context",map);
        HttpSolrClient client = SolrClient.getSolrClient();
        client.add(document);
        client.commit();
    }

    public static void atomicIncUpdate() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("post_id","1006");
        Map<String,String> map = new HashMap<>();
        map.put("inc","100");
        document.addField("price",map);
        HttpSolrClient client = SolrClient.getSolrClient();
        client.add(document);
        client.commit();
    }
}