package com.saechan.collectormarket.member.model.repository;

import com.saechan.collectormarket.member.model.entity.Member;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

  Optional<Member> findByEmail(String email);

  //
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT m FROM Member m WHERE m.email = :email")
  Optional<Member> findByEmailWithLock(@Param("email") String email);

  Optional<Member> findByEmailAuthCode(String emailAuthCode);
}
