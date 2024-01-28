package com.saechan.collectormarket.store.service;

import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.model.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;

  private final MemberRepository memberRepository;

  public Store createStore(Member member) {

    Store store = Store.builder()
        .name("tempName") // 임시이름
        .description(null)
        .image(null)
        .openDt(LocalDateTime.now())
        .products(null)
        .reviews(null)
        .reviewRating(null)
        .member(member)
        .chatRoomStores(null)
        .build();
    storeRepository.save(store);
    store.setName("상점 " + store.getId() + "호"); // 기본 상점 이름 업데이트
    return storeRepository.save(store);
  }

}
