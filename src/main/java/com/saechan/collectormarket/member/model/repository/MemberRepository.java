package com.saechan.collectormarket.member.model.repository;

import com.saechan.collectormarket.member.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByEmailAuthCode(String emailAuthCode);
}
