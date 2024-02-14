package com.saechan.collectormarket.product.model.repository;

import com.saechan.collectormarket.product.model.entity.ProductSearch;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearch, String> {
  @Query("{\"bool\": {\"must\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"productStatus\": \"?1\"}}, {\"match\": {\"productCategory\": \"?2\"}}]}}")
  List<ProductSearch> findByNameAndProductStatusAndProductCategory(String name, String productStatus, String productCategory);
}
