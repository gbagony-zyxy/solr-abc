package com.ruyin.code.solr;

import com.ruyin.code.solr.bean.Index;
import com.ruyin.code.solr.bean.User;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gbagony on 2016/12/28.
 */
public class ServerTest {

    private static String jetty_solr = "http://localhost:9090/solr/solr-basic";
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

    public static void addBeans() throws IOException, SolrServerException {
        Index index = new Index();
        index.setId("6");
        index.setName("Add beans index 1");
        index.setMenu("Index beans menu 1");
        index.setCat(new String[]{"made智障","无word可说"});
        List<Index> indexs = new ArrayList<>();
        indexs.add(index);

        index = new Index();
        index.setId("7");
        index.setName("Add beans index 2");
        index.setMenu("Index beans menu 2");
        index.setCat(new String[]{"aaa","bbb"});
        indexs.add(index);

        UpdateResponse response = solrClient.addBeans(indexs);
        solrClient.commit();

        query("id:6 id:7");
    }

    //删除索引
    public static void removeIndex() throws IOException, SolrServerException {
        //根据id删除索引
        solrClient.deleteById("1");
        solrClient.commit();
        query("id:1");

        //根据id集合删除多个索引
        List<String> ids = new ArrayList<>();
        ids.add("2");
        ids.add("3");
        solrClient.deleteById(ids);
        solrClient.commit(true,true);
        query("id:3 id:2");

        //query("id:4 id:6");
        //删除查询到的索引信息
        solrClient.deleteByQuery("id:4 id:6");
        solrClient.commit(true,true);
        query("*:*");
    }

    public static void removeIndex(String query){

    }

    public static void removeIndex(List<String> list){

    }

    public static void removeIndexByQuery(){

    }

    //查询索引
    public static void queryAll() throws IOException, SolrServerException {
        ModifiableSolrParams params = new ModifiableSolrParams();
        //查询所有索引
        params.set("q","*:*");
        //返回记录从哪一条开始
        params.set("start",0);
        //solr查询默认只返回10条数据
        params.set("rows",Integer.MAX_VALUE);

        //排序规则
        params.set("sort","score desc");
        //返回信息并全部加上score
        params.set("f1","*,score");

        QueryResponse response = solrClient.query(params);
        response.getResults().stream().forEach(s-> System.out.println(s));
    }

    //其他与server相关方法
    public static void otherMethod() throws IOException, SolrServerException {
        solrClient.getBinder();
        solrClient.optimize();

        Index index = new Index();
        index.setId("299");
        index.setName("Add bean index666");
        index.setMenu("Index bean menu666");
        index.setCat(new String[]{"a666","b666"});

        UpdateResponse response = solrClient.addBean(index);
        queryAll();
        solrClient.rollback();
        solrClient.commit();
        queryAll();
    }

    //文档查询
    public static void queryCase() throws IOException, SolrServerException {
        //AND 并且
        SolrQuery params = new SolrQuery("name:iPod AND manu:Belkin");
        //OR 或者
        params.setQuery("name:iPod OR manu:belkin");
        //空格 等同于 OR
        params.setQuery("name:iPod manu:belkin");

        //一个索引中包含多个字段
        params.setQuery("name:canon,apple");
        //一个索引中不包含字段
        params.setQuery("name:canon,apple NOT manu:inc");
        //    50<=price<=200
        params.setQuery("price:[50 TO 200]");
        params.setQuery("popularity:[5 TO 6]");
        //  50 < price < 200
        params.setQuery("price:{50 TO 200}");

        params.setQuery("price:[50 TO 200] AND popularity:[5 TO 6]");
        params.setQuery("price:[50 TO 200] OR popularity:[5 TO 6]");

        //过滤器查询,听说可以提高性能,filter类似多个条件组合,如and
        params.addFilterQuery("id:VA902B");
        params.addFilterQuery("price:[50 TO 200]");

        //排序
        params.addSort("id", SolrQuery.ORDER.asc);

        //分页,两种方式均可
        params.add("start","0");
        params.add("rows","200");
        params.setStart(0);
        params.setRows(Integer.MAX_VALUE);

        //开启高亮
        params.setHighlight(true);
        //设置高亮字段
        params.addHighlightField("name");
        //高亮关键字前缀
        params.setHighlightSimplePre("<font color='red'>");
        //高亮关键字后缀
        params.setHighlightSimplePost("</font>");
        //结果分片数
        params.setHighlightSnippets(1);
        //每个分片的最大长度,默认为100
        params.setHighlightFragsize(1000);

        //分片信息
        params.setFacet(true)
                .setFacetMinCount(1)
                .setFacetLimit(5)
                .addFacetField("name")
                .addFacetField("address_s");

        QueryResponse response = solrClient.query(params);

        response.getResults().stream().forEach(s-> System.out.println(s));

        response.getFacetFields().stream()
                .forEach(facet->facet.getValues()
                        .forEach(value-> System.out.println(value.getName()+":"+value.getCount())));
    }

    //分片查询、统计
    public static void facetQueryCase() throws IOException, SolrServerException {
        SolrQuery params = new SolrQuery("*:*");

        //sort
        params.addSort("id", SolrQuery.ORDER.asc);
        //params.addSort(SolrQuery.SortClause.create("name", SolrQuery.ORDER.desc));

        params.setStart(0);
        params.setRows(Integer.MAX_VALUE);

        //Facet为solr中的层次分类查询
        params.setFacet(true)
                .setQuery("*:*")
                .setFacetMinCount(1)
                .setFacetPrefix("cor")
                .addFacetField("manu")
                .addFacetField("name");

        QueryResponse response= solrClient.query(params);

        //输出查询结果集
        //response.getResults().stream().forEach(s-> System.out.println(s));

        //输出分片信息
        response.getFacetFields().stream()
                .forEach(field->field.getValues()
                    .forEach(count -> System.out.println(count.getName()+":"+count.getCount())));
    }

    //doc 与 javaBean的相互转换
    private static void docInterconversionWithBean() throws IOException, SolrServerException {
        SolrDocument doc = new SolrDocument();
        doc.addField("id",456);
        doc.addField("name","SolrDocument");
        doc.addField("hobbies",new String[]{"music","book","sport"});
        doc.put("address","HangZhou");
        doc.setField("sex","man");
        doc.setField("remark","China people");

        DocumentObjectBinder binder = new DocumentObjectBinder();

        User user = new User();
        user.setId(222);
        user.setName("JavaBean");
        user.setHobbies(new String[]{"music","book","sport"});
        user.setAddress("HangZhou");
        user.setAge(24);
        user.setSex("Man");
        user.setRemark("good man");
        //bean -> solrInputDocument
        SolrInputDocument inputDoc = binder.toSolrInputDocument(user);
        System.out.println(solrClient.add(inputDoc).getQTime());
        //SolrInputDocument -> bean
        User user1 = binder.getBean(User.class,doc);
        System.out.println(user1.getId()+":"+user1.getName());

    }

    private static void createDoc() throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id",System.currentTimeMillis());
        doc.addField("name","SolrInputDocument");
        doc.addField("age",22,2f);

        doc.addField("hobbies",new String[]{"music","book","sport"});
        doc.addField("address",new SolrInputField("hangzhou"));

        doc.setField("sex","man");
        doc.setField("remark","China people",2.0f);

        UpdateResponse response = solrClient.add(doc);


    }

    @Test
    public void test() throws IOException, SolrServerException {
        //addDoc();
        //query("id:1");
        //addDocs();
        //addBean();
        //addBeans();
        //removeIndex();
        //queryAll();
        //otherMethod();
        //facetQueryCase();
        docInterconversionWithBean();
    }

    //关于java对象深复制与浅复制
    @Test
    public void test1() throws CloneNotSupportedException {
        User user1 = new User();
        user1.setId(3);
        User user2 = user1;
        System.out.println(user1.getId()+":"+user2.getId());
        user2.setId(10);
        System.out.println(user1.getId()+":"+user2.getId());
        User user3 = (User) user1.clone();
        System.out.println(user1.getId()+":"+user3.getId());
        user3.setId(15);
        System.out.println(user1.getId()+":"+user3.getId());
    }
}
