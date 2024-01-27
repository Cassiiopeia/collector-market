package com.saechan.collectormarket.member.model.repository;

import com.saechan.collectormarket.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

}
