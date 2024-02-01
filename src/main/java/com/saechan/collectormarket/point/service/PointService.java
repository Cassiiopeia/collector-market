package com.saechan.collectormarket.point.service;

import com.saechan.collectormarket.global.excpetion.ErrorCode;
import com.saechan.collectormarket.member.exception.MemberException;
import com.saechan.collectormarket.member.model.entity.Member;
import com.saechan.collectormarket.member.model.repository.MemberRepository;
import com.saechan.collectormarket.point.dto.request.ChargePointForm;
import com.saechan.collectormarket.point.dto.request.WithDrawPointForm;
import com.saechan.collectormarket.point.dto.response.PointTransactionDto;
import com.saechan.collectormarket.point.exception.PointTransactionException;
import com.saechan.collectormarket.point.model.entity.PointTransaction;
import com.saechan.collectormarket.point.model.repository.PointTransactionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {

  private final PointTransactionRepository pointTransactionRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public PointTransactionDto charge(String memberEmail, ChargePointForm form) {
    // 회원 검증
    Member member = verifyMemberFromEmail(memberEmail);

    // 충전거래 내역 저장 및 회원 업데이트
    Double existingPoint = member.getPoint();
    Double currentPoint = existingPoint + form.getChargeAmount();

    // 회원 포인트 업데이트
    member.setPoint(currentPoint);
    memberRepository.save(member);

    // 포인트 거래 저장
    return PointTransactionDto.from(
        pointTransactionRepository.save(
            PointTransaction.builder()
                .transactionAmount(form.getChargeAmount())
                .currentPoint(currentPoint)
                .transactionDt(LocalDateTime.now())
                .member(member)
                .build()));
  }

  @Transactional
  public PointTransactionDto withDraw(String memberEmail, WithDrawPointForm form) {

    log.info("Point Withdraw started");
    // 회원 검증
    Member member = verifyMemberFromEmail(memberEmail);

    // 계좌 유효 검증 로직

    // 출금거래 내역 저장 및 회원 업데이트
    Double existingPoint = member.getPoint();
    Double currentPoint = existingPoint - form.getWithDrawAmount();

    if (currentPoint < 0) {
      // 현재포인트보다 큰 포인트를 출금할수없음
      throw new PointTransactionException(ErrorCode.NOT_ENOUGH_POINT);
    }

    member.setPoint(currentPoint);
    memberRepository.save(member);

    // 포인트 거래 저장
    return PointTransactionDto.from(
        pointTransactionRepository.save(
            PointTransaction.builder()
                .transactionAmount(-1 * form.getWithDrawAmount())
                .currentPoint(currentPoint)
                .transactionDt(LocalDateTime.now())
                .member(member)
                .build()));
  }

  @Transactional(readOnly = true)
  public Page<PointTransactionDto> history(String memberEmail, int page) {
    // 회원 검증
    Member member = verifyMemberFromEmail(memberEmail);

    // 페이징 요청 설정 (최근 10개의 거래 내역, 최근거래순)
    PageRequest pageRequest
        = PageRequest.of(page, 10, Sort.by("transactionDt").descending());

    // 페이징 결과 불러오기
    return pointTransactionRepository.findAllByMember(member, pageRequest)
        .map(pointTransaction -> PointTransactionDto.from(pointTransaction));
  }

  private Member verifyMemberFromEmail(String email) {
    // 회원 존재 확인
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

    // 비활성화 회원 확인
    if (!member.getActivated()) {
      throw new MemberException(ErrorCode.MEMBER_NOT_ACTIVATED);
    }
    return member;
  }

}
