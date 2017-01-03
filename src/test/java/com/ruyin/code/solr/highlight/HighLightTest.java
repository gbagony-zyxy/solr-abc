package com.ruyin.code.solr.highlight;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by gbagony on 2017/1/3.
 */
public class HighLightTest {
    private static String jetty_solr = "http://localhost:9090/solr/mysolr";
    private static HttpSolrClient solrClient = null;

    @Before
    public void init(){
        solrClient = new HttpSolrClient.Builder(jetty_solr).build();
    }

    @Test
    public void test1() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("id:SP2514N");
        //开启高亮
        query.setHighlight(true);
        //返回的字符个数
        query.setHighlightFragsize(100);
        query.setHighlightRequireFieldMatch(true);
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");
        query.setParam("hl.fl", "id");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList list = response.getResults();
        Map<String, Map<String, List<String>>> map = response.getHighlighting();
        list.stream().forEach(doc-> System.out.println(map.get(doc.getFieldValue("id").toString())));
    }
}
