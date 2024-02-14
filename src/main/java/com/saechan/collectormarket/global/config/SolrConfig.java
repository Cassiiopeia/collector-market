package com.saechan.collectormarket.global.config;

import lombok.Builder;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@EnableSolrRepositories(basePackages = "com.saechan.collectormarket.product.model.repository")
@Configuration
public class SolrConfig {

  @Value("${spring.data.solr.host}")
  private String host;

  @Value("${spring.data.solr.port}")
  private String port;

  @Value("${spring.data.solr.core}")
  private String core;

  @Bean
  public SolrClient solrClient() {
    return new HttpSolrClient.Builder(
        "http://" + host + ":" + port + "/" + core).build();
  }

  @Bean
  public SolrTemplate solrTemplate(SolrClient client) throws Exception {
    return new SolrTemplate(client);
  }
}
