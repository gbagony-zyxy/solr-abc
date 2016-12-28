package com.ruyin.code.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by gbagony on 2016/12/28.
 */
public class BaseTest {

    private static String jetty_solr = "http://localhost:9090/solr/zyfm";
    HttpSolrClient solrClient = new HttpSolrClient.Builder(jetty_solr).build();

    @Test
    public void test() throws IOException, SolrServerException {
        SolrQuery query = new SolrQuery();
        query.set("q","*:*");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("Found num:"+numFound);
        results.stream().forEach(result->result.getFieldNames()
                .forEach(field-> System.out.println(field+" : "+result.get(field))));
    }

    @Test
    public void testUploadFile() throws IOException, SolrServerException {
        String filePath = "C:\\Users\\gbagony\\Desktop\\apache-solr-ref-guide-6.2.pdf";

        ContentStreamUpdateRequest request = new ContentStreamUpdateRequest("/update/extract");

        String contentType = "application/pdf";
        request.addFile(new File(filePath),contentType);
        request.setParam("literal.id","solr");
        request.setParam("uprefix","attr_");
        request.setParam("fmap.content","attr_content");
        request.setAction(AbstractUpdateRequest.ACTION.COMMIT,true,true);

        solrClient.request(request);

        QueryResponse response = solrClient.query(new SolrQuery("*:*"));
        System.out.println(response);
    }

    @Test
    public void deleteIndex(){

    }
}
