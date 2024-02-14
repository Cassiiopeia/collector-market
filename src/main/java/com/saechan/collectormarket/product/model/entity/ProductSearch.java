package com.saechan.collectormarket.product.model.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearch {
  @Id
  @Indexed
  private String id;

  @Indexed
  private String title;
}
