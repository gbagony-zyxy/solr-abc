package com.ruyin.code.solr.baseop;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.SolrPing;
import org.apache.solr.client.solrj.response.SolrPingResponse;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

/**
 *  使用ping命令查看core状态
 */
public class PingOp {

    public static void main(String[] args) throws IOException, SolrServerException {
        String solr_url = "http://localhost:9090/solr";
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solr_url).build();
        SolrPing ping = new SolrPing();
        ping.getParams().add("wt","json").add("distrib","false").add("indent","true");
        SolrPingResponse response = ping.process(solrClient,"ruyin");
        System.out.println(response.getStatus());


        DateTimeFormatter.ISO_INSTANT.format(new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }

            @Override
            public long getLong(TemporalField field) {
                return 0;
            }
        });
    }
}
