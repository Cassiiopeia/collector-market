package com.saechan.collectormarket.store.service;

import com.saechan.collectormarket.global.s3.FileStorageService;
import com.saechan.collectormarket.global.exception.ErrorCode;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.service.MemberUtils;
import com.saechan.collectormarket.store.dto.request.StoreUpdateForm;
import com.saechan.collectormarket.store.dto.response.StoreDto;
import com.saechan.collectormarket.store.exception.StoreException;
import com.saechan.collectormarket.store.model.entity.Store;
import com.saechan.collectormarket.store.model.repository.StoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;

  private final FileStorageService fileStorageService;

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

  public StoreDto update(String memberEmail, StoreUpdateForm form) {
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상점 검증
    Store store = storeRepository.findById(member.getStore().getId())
        .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));

    // 기존 이미지 있으면 삭제
    if( store.getImage() != null && !store.getImage().isEmpty()){
      fileStorageService.deleteFile(store.getImage());
    }

    // 새 이미지 업로드
    MultipartFile imageFile = form.getImage();
    String imageUrl = null;
    if( !imageFile.isEmpty()){
      imageUrl = fileStorageService.uploadFile(imageFile,"store", store.getId());
    }

    // 상점 업데이트
    store.setName(form.getName());
    store.setDescription(form.getDescription());
    store.setImage(imageUrl);

    // 상점 저장
    return StoreDto.from(storeRepository.save(store));
  }

  public StoreDto getProfile(String memberEmail) {
    // 회원 검증
    Member member = MemberUtils.verifyMemberFromEmail(memberEmail);

    // 상점 검증
    return StoreDto.from(
        storeRepository.findById(member.getStore().getId())
            .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND)));
  }
}
