package com.saechan.collectormarket.point.model.repository;

import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.point.model.entity.PointTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long> {
  Page<PointTransaction> findAllByMember(Member member, Pageable pageable);
}

