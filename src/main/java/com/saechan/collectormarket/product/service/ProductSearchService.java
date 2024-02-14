package com.saechan.collectormarket.product.service;

import com.saechan.collectormarket.product.dto.response.ProductDto;
import com.saechan.collectormarket.product.model.entity.Product;
import com.saechan.collectormarket.product.model.entity.ProductSearch;
import com.saechan.collectormarket.product.model.repository.ProductRepository;
import com.saechan.collectormarket.product.model.repository.ProductSearchRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final ProductRepository productRepository;
  private final ProductSearchRepository productSearchRepository;

  @Async
  @Transactional(readOnly = true)
  public void indexAllProducts(){
    List<Product> products = productRepository.findAll();
    List<ProductSearch> productSearches = products.stream()
        .map(product -> ProductSearch.from(product)).collect(Collectors.toList());
    productSearchRepository.saveAll(productSearches);
  }

  @Async
  public void indexProduct(Product product){
    productSearchRepository.save(ProductSearch.from(product));
  }

  @Async
  public void deleteProductIndex(Product product){
    productSearchRepository.deleteById(ProductSearch.from(product).getId());
  }

  public List<ProductDto> searchByNameAndProductStatusAndProductCategory(
      String name, String ProductStatus, String ProductCategory){
     return productSearchRepository.findByNameAndProductStatusAndProductCategory(
        name.toLowerCase(), ProductStatus.toLowerCase(), ProductCategory.toLowerCase())
         .stream()
         .map(productSearch -> ProductDto.from(productRepository.findById(Long.parseLong(productSearch.getId())).get()))
         .collect(Collectors.toList());
  }
}
