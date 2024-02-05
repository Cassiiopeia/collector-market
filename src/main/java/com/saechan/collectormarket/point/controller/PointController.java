package com.saechan.collectormarket.point.controller;

import com.saechan.collectormarket.point.dto.request.ChargePointForm;
import com.saechan.collectormarket.point.dto.request.WithDrawPointForm;
import com.saechan.collectormarket.point.dto.response.PointTransactionDto;
import com.saechan.collectormarket.point.service.PointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
@Slf4j
public class PointController {

  private final PointService pointService;

  @PostMapping("/charge")
  public ResponseEntity<PointTransactionDto> chargePoint(
      Authentication authentication,
      @Valid @RequestBody ChargePointForm form
  ) {
    String memberEmail = authentication.getName();
    PointTransactionDto pointTransactionDto = pointService.charge(memberEmail, form);
    return ResponseEntity.ok(pointTransactionDto);
  }

  @PostMapping("/withDraw")
  public ResponseEntity<PointTransactionDto> withDrawPoint(
      Authentication authentication,
      @Valid @RequestBody WithDrawPointForm form
  ) {
    log.info("Point withdraw Controller started");
    String memberEmail = authentication.getName();
    PointTransactionDto pointTransactionDto = pointService.withDraw(memberEmail, form);
    return ResponseEntity.ok(pointTransactionDto);
  }

  @GetMapping("/history")
  public ResponseEntity<Page<PointTransactionDto>> history(
      Authentication authentication,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize
  ) {
    String memberEmail = authentication.getName();
    Page<PointTransactionDto> pointTransactionDtos = pointService.history(memberEmail, page, pageSize);
    return ResponseEntity.ok(pointTransactionDtos);
  }
}