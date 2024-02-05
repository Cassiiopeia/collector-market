package com.saechan.collectormarket.store.controller;

import com.saechan.collectormarket.store.dto.request.StoreUpdateForm;
import com.saechan.collectormarket.store.dto.response.StoreDto;
import com.saechan.collectormarket.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Slf4j
public class StoreController {
  private final StoreService storeService;

  @PutMapping("/update")
  public ResponseEntity<StoreDto> update(
      Authentication authentication,
      @ModelAttribute StoreUpdateForm form
  ){
    String memberEmail = authentication.getName();
    StoreDto storeDto = storeService.update(memberEmail,form);
    return ResponseEntity.ok(storeDto);
  }

  @GetMapping("/profile")
  public ResponseEntity<StoreDto> profile(
      Authentication authentication
  ){
    String memberEmail = authentication.getName();
    StoreDto storeDto = storeService.getProfile(memberEmail);
    return ResponseEntity.ok(storeDto);
  }

}
