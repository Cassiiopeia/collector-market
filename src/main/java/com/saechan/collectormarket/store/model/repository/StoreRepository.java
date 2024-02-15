package com.saechan.collectormarket.store.model.repository;

import com.saechan.collectormarket.store.model.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

  @Override
  Optional<Store> findById(Long storeId);

  Optional<Store> findByName(String storeName);
}
