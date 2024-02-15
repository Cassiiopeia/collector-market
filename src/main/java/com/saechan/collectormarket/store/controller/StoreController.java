package com.saechan.collectormarket.store.controller;

import com.saechan.collectormarket.store.dto.request.StoreUpdateForm;
import com.saechan.collectormarket.store.dto.response.StoreDto;
import com.saechan.collectormarket.store.dto.response.StoreSearchDto;
import com.saechan.collectormarket.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Slf4j
public class StoreController {
  private final StoreService storeService;

  // 자신의 상점 업데이트
  @PutMapping("/update")
  public ResponseEntity<StoreDto> storeUpdate(
      Authentication authentication,
      @ModelAttribute StoreUpdateForm form
  ){
    String memberEmail = authentication.getName();
    StoreDto storeDto = storeService.update(memberEmail,form);
    return ResponseEntity.ok(storeDto);
  }

  // 상점 상세보기
  @GetMapping("/profile")
  public ResponseEntity<StoreDto> storeProfile(
      Authentication authentication,
      @RequestParam Long storeId
  ){
    StoreDto storeDto = storeService.getProfile(storeId);
    return ResponseEntity.ok(storeDto);
  }

  // 상점 검색
  @GetMapping("/search")
  public ResponseEntity<StoreSearchDto> searchStoreByName(
      Authentication authentication,
      @RequestParam String storeName
  ){
    StoreSearchDto storeSearchDto = storeService.getStoreByName(storeName);
    return ResponseEntity.ok(storeSearchDto);
  }

}
