package com.saechan.collectormarket.product.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
  MENS_CLOTHING("남성의류"),
  WOMENS_CLOTHING("여성의류"),
  SHOES("신발"),
  BAGS("가방"),
  WALLETS("지갑"),
  WATCHES("시계"),
  JEWELRY("쥬얼리"),
  FASHION_ACCESSORIES("패션악세사리"),
  DIGITAL("디지털"),
  HOME_APPLIANCES("가전제품"),
  SPORTS("스포츠"),
  VEHICLES("차량"),
  STAR_GOODS("스타굿즈"),
  TICKETS("티켓"),
  ART("예술"),
  MUSIC("음악"),
  BOOKS("도서"),
  BEAUTY("뷰티"),
  FURNITURE("가구"),
  HOUSEHOLD_ITEMS("생활용품"),
  INDUSTRIAL_ITEMS("산업용품"),
  FOOD("식품"),
  ANIMALS("동물"),
  ETC("기타");

  private final String category;

}