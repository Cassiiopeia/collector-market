package com.saechan.collectormarket.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.model.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

  @Mock
  private StoreRepository storeRepository;

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private StoreService storeService;

  private Member member;
  private Store store;

  private final String memberEmail = "chan4760@naver.com";

  @BeforeEach
  void setUp(){
    // 회원 객체 설정
    member = Member.builder()
        .email(memberEmail)
        .activated(true)
        .build();

    // 삼점 객체 설정
    store = Store.builder()
        .name("테스트상점1호")
        .description("테스트 상세 정보")
        .image("테스트 이미지 주소")
        .build();
  }

  @Test
  @DisplayName("상점 생성 성공")
  void createStore() {
    //given
    when(storeRepository.save(any(Store.class))).thenAnswer(invocation -> {
      Store store = invocation.getArgument(0);
      if(store.getId() == null){
        store.setId(1L); // store 의 Id를 1L로 설정;
      }
      return store;
    });
//    when(storeRepository.save(any(Store.class))).thenReturn(store);
//    store.setId(1L);

    //when
    Store createdStore = storeService.createStore(member);

    //then
    assertEquals("상점 "+createdStore.getId()+"호",createdStore.getName());
    assertNull(createdStore.getDescription());
    assertNull(createdStore.getImage());
    assertNotNull(createdStore.getOpenDt());
    assertNull(createdStore.getProducts());
    assertNull(createdStore.getReviews());
    assertNull(createdStore.getReviewRating());
    assertNull(createdStore.getChatRoomStores());
    assertEquals(member.getEmail(), createdStore.getMember().getEmail());

    verify(storeRepository,times(2)).save(any(Store.class));

  }
}