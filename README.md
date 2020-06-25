## README
-eclips프로젝트파일, SQL프로젝트파일, 개발명세파일 업로드(SQLFile폴더)

결제요청을 받아 카드사와 통신하는 인터페이스 제공하는 결제시스템 
1. 개발프레임워크
- java spring mvc 패턴 웹 프로그래밍, 메이븐 빌드, MySQL DB 사용
- REST API형태의 백앤드 프로그래밍 개발
- URL형태의 서비스 호출
- 카드결제 : insertCardPayment
- 카드취소 : modifyCardPayment / deleteCardPayment
- 카드조회 : getCardPaymentList
2. 테이블 설계 
- 스키마 : cardpayment
- 테이블명 : cardpay
- 컬럼 및 데이터 타입
CARD_NO	varchar(20) PK	카드번호
CARD_VDATE	varchar(4)	  유효기간
CARD_CVCNO	varchar(3)	  cvc번호
PAY_STCD	varchar(2)	  카드상태코드
PAY_MON	varchar(2)	  할부개월코드
PAYMENT	varchar(10)	  결제금액
VAT_PAYMENT	varchar(9)	  부가가치세
MANAGE_NO	varchar(20)	  관리번호
CARD_AES	varchar(300)	  주요정보암호화
ETC	varchar(47)	  기타공간
INPUT_DATE	datetime	  입력일자
MODIFY_DATE	datetime	  수정일자
 - 카드결제, 조회, 취소 기능만을 구현하였기 때문에 테이블을 1개로 통합하여 구성하였습니다. 향후 여러건의 결제이력관리 및 부분취소 기능을 위해서는 카드기본정보, 결제이력, 부분취소이력관리 테이블을 만들어 프로그램 개발이 필요합니다.
 - 카드취소 기능구현을 위해 카드상태코드(CARD_STCD)를 사용하여 코드값 변경으로 취소여부를 판단하였습니다. DELETE기능도 구현하였으나, 이력관리를 위해서 UPDATE를 통한 취소프로세스를 구현하였습니다.


3.문제해결전략
ㄱ. 입력값체크
 - 카드자리수, 유효기간, cvc데이터, 부가가치세, 관리번호발생 등의 유효성체크로직을 넣어 데이터 생성전에 발생할 수 있는 오류항목들을 최소화 하였습니다. 특히 부가가치세의경우 미입력 시 자동계산하여 화면에 보여주고 저장하는 단계를 추가하였습니다.
ㄴ. 암복호화
 - AES256모듈을 사용하여 카드번호+유효기간+CVC번호를 조합하여 암복호화 로직을 구현하였습니다.
ㄹ. 관리번호부여
 - 관리번호는 발생규칙에 대한 정의가 없어 임의로 구성하였으며, 카드번호 앞4자리+CVC번호+시,분,초,밀리세컨 조합의 20자리형태의 값으로 정의하였습니다.
4.빌드 및 실행방법
- 이클립스 메이븐 환경에서의 빌드를 진행하였습니다.
ㄱ. 메이븐 라이브러리 구성
- pom.xml 파일에 dependency 정의를 통해 프레임웍 환경을 구성하였습니다.
ㄴ. 웹, 백앤드 연결
 - 톰캣9.0 로컬서버를 구동하였으며, root-context.xml을 통해 mySQLDB와 연동하였습니다.
ㄷ. MVC 패턴 구현
 - controller, dao, form, service 구조로 화면, 서비스, DB연동형 풀스택환경을 개발하였습니다.
ㄹ. HTML, CSS, JavaScript, REST API구조 호출
 - 화면은 HTML구조 및 JavaScrip를 사용하여 구성하였고, REST API형태의 호출방식에 준하여 각 서비스를 호출하였습니다.
ㅁ. 각단계별 실행 화면을 캡쳐하였습니다.
 - <첨부>항목 참고
깃허브 : SQLFile/ README_개발명세.pdf 참조
