package com.saechan.collectormarket.product.model.repository;


import com.saechan.collectormarket.product.model.entity.ProductSearch;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends SolrCrudRepository<ProductSearch, String> {

}