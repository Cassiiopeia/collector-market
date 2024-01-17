# 콜렉터마켓
: 자신의 물건을 팔아 포인트를 벌고, 포인트로 자신이 원하는 물건을 수집할 수 있는 간편한 중고 거래 플랫폼입니다.
![Main](/doc/img/main.png)

## 개발환경
- JDK 17
- Spring Boot
- Gradle
- MariaDB
- Elastic Search

## Model 설명

- Member : 회원
  - id, 이메일, 비밀번호, 자신의상점, 회원탈퇴여부, 동네지역, 포인트, 이메일인증여부
    
- Store : 상점 (모든 회원은 하나의 상점을 가짐 )
  - id, 상점이름, 상점소개, 판매상품, 구매한상품, 상점리뷰
    
- product : 상품
  - id, 상품설명, 가격, 상품상태, 거래방식, 배송비, 카테고리, 판매상점(Store), 상점
    
- transaction : 거래
  - id, 구매자상점(Store), 판매상점(Store), 거래완료시간, 거래상태
    
- review : 리뷰 (평점)
  - id, 평점, 리뷰내용, 리뷰상점

- pointTransaction: 포인트 거래
    - id, 거래 금액, 거래시간, 거래회원(member) 
  
- ChatRoom : 채팅창 (상점끼리 메시지)
  - id, 판매상점(Store), 구매상점(Store) 
  
- ChatMessage : 채팅내용 (채팅 메시지내용)
  - id, 보내는상점(Store), 채팅내용, 채팅창(ChatRoom), 전송시간

  
## 프로젝트 기능

1. 회원 로그인 기능
   - 회원가입이 완료되면 이메일 인증후 계정이 활성화됩니다.
   - 활성화 된 계정만 로그인이 가능합니다.
   - 로그인시 Jwt를 이용한 토큰인증
   - SpringSecuirty를 이용하여 접근제어 권한 설정
     
2. 회원 정보 CRUD 기능
   - 회원정보 생성, 수정, 탈퇴
   - 회원정보 조회 ( 자신의 상점, 자신의 포인트, 이메일, 전화번호 등등 )
     
3. 상점 기능
   - 모든 회원은 하나의 상점을 가집니다. (OneToOne 관계 - 회원탈퇴시 상점도 없어집니다.)
     - 회원 생성시 자동으로 자신의 상점이 생성됩니다. (상점이름은 자동부여, Unique값을 가집니다.)
   - 상점 정보 수정 (이미지, 상점소개)
   - 자신의 상점 조회 ( 판매상품, 구매한상품, 상점후기)
     
4. 상품 기능
   - 자신의 상점에 상품을 추가, 삭제, 수정 기능
     
5. 조회 기능 ( 상품, 상점 )
   - Elastic Search를 이용한 상품검색기능 ( 상품이름, 카테고리별, 상품 상태, 상품거래상태로 검색)
   - 상점 검색 기능
     
6. 회원 메시지 기능
   - WebSocket을 이용한 회원상점끼리 채팅방에서의 메시지 전송기능
   - 상점간의 1대1 메시지 전송기능
  
7. 거래 기능
   - 택배거래시 포인트로 결제함 - 포인트 충전, 내보내기 기능
   - 직거래시 - 구매 상점에서 후에 거래완료처리 -> 상품 판매완료 처리
   - 택배결제시 -> 자동으로 포인트결제 -> 상품 판매완료 -> 물건을 받았으면 구매상점에서 상품 거래완료처리

8. 포인트 기능
    - 포인트 충전, 내보내기 기능
    - 회원의 포인트 거래내역 조회 기능

9. 리뷰 기능
   - 거래완료가 진행된 상점에 대해서 판매상점 , 구매상점 서로에게 리뷰작성가능
   - 한번 작성된 리뷰는 삭제불가능
   - 상점에 리뷰의 평점의 평균이 표시된다.
  
10. 도커로 배포


## 세부 구현 기능
### JWT토큰 및 시큐리티 기능
- [ ] JWT 토큰 구현
- [ ] Security기능 구현
- [ ] 회원 로그인 SignIn
- [ ] 로그아웃 Logout

### 회원 정보 기능
- 회원생성시 Form : 이메일, 비밀번호, 전화번호
<br><br>
- [ ] 회원 생성 및 자동 상점생성 기능
- [ ] 이메일 인증 -> 계정활성화 기능
- [ ] 회원정보조회
- [ ] 정보 조회
- [ ] 정보 수정
- [ ] 회원 탈퇴 
 
### 상점 관리
- 상점수정시 Form : 이름, 소개, 사진 
  <br><br>
- [ ] 자신의 상점 등록
- [ ] 자신의 상점 수정
- [ ] 자신의 상점 조회 :  등록한 판매 물품 목록, 구매한 상품 목록, 상점후기, 오픈일

### 포인트 관리
- [ ] 포인트 충전 (연결된 자신의 계좌에서 충전)
- [ ] 포인트 내보내기 (자신의 계좌로 내보내기)
- [ ] 포인트 조회 (충전, 내보내기 내역 조회)
      
### 상품 관리
- 상픔등록시 FORM: 사진, 제품 설명, 가격,  상태, 거래 방식, 배송비, 사진, 카테고리
  <br><br>
- [ ] 상품 등록
- [ ] 상품 수정
- [ ] 상품 상태 수정 (판매중, 예약, 완료)
- [ ] 상품 삭제 

### 상품 검색, 상점 검색
  <br><br>
- [ ] 상품이름 Like 검색
- [ ] Elastic Search 기본 설정
- [ ] 카테고리로 상품 필터링 검색
- [ ] 상품상태로 상품 필터링 검색
- [ ] 거래상태로 상품 필터링 검색 
- [ ] 물품 클릭시 상세정보 보기
- [ ] 상점 검색
- [ ] 상점 클릭시 상점 상세정보 보기 ( 상점이름, 이미지, 상점소개, 리뷰, 판매중인 상품 표시)

### 채팅 시스템
- [ ] WebSocket 설정
- [ ] 채팅 보내기 (상점끼리 대화방은 1대1로 하나만 생성가능)

### 거래 시스템
- [ ] 상품에 대한 구매상점의 거래요청 ( 직거래요청, 택배거래요청 )
- [ ] 상품에 대한 판매사점의 거래수락 ( 상품상태 예약중 표시로 변경됨 )
- 거래완료
  - [ ] 직거래 -> 구매상점이 상품상태 거래완료로 표시 -> 구매상점에서 상품 거래완료 표시
  - [ ] 택배거래 -> 포인트자동소진 -> 상품상태 거래완료 표시 -> 구매 상점이 상품 거래완료 표시

### 리뷰 시스템
- 리뷰생성FORM : 리뷰평점, 평가, 사진
  <br><br>
- [ ] 구매상점 -> 판매상점 리뷰 작성기능
- [ ] 판매상점 -> 구매상점 리뷰 작성
- [ ] 상점에 리뷰평점 평균 업데이트 



## ERD
![ERD_GRAPH](/doc/img/erd_graph.png)

## Trouble Shooting
[Go-To-Trouble-Shooting](/doc/trouble_shooting.md)
