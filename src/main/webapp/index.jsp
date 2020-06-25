<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>KAKAO APPLY</title>
<style>
      /* 브라우저의 기본 값 초기화 */
		html, body {margin: 0; padding: 0; line-height: 1.25;}
		table {table-layout: fixed; border-collapse: collapse;}
		h1, h2, h3, h4, h5, h6, p,
		th, td {margin: 0; padding: 0;}
		ul, ol, li {list-style: none;}
		legend, caption {display: none;}
		
		/* 사이트에서 사용할 예쁜폰트(노토산스) 웹폰트 적용 */
		body {font-family: 'Noto Sans KR', sans-serif;}
		
		/* body 바로 하위의 껍데기 */
		.wrapper {padding: 48px;}
		
		.contents {padding: 20px 0; border-top: 1px solid #dadce0;}
		
		/* h1제목 (결제시스템) */
		h1 {
		  margin-bottom: 8px;
		  font-size: 30px;
		  font-weight: bold;
		  color: #666;
		}
		/* h1설명문 (결제시스템 아래 작은글씨) */
		.h1_desc {
		  font-size: 20px;
		  margin-bottom: 30px;
		  color: #999
		}
		/* section별 제목 (결제 입력) */
		h2 {
		  margin: 20px 0 24px;
		  font-size: 20px;
		  color: #666;
		}
		.button {
		  background-color: #555555;
		  border: none;
		  color: white;
		  font: 'Noto Sans KR';
		  text-align: center;
		  text-decoration: none;
		  display: inline-block;
		  font-size: 14px;
		  margin: 2px 2px;
		  cursor: pointer;
		}
		/* table의 최대값은 600px 그 이상 커질수 없도록 제한 걸어 놓음 
		아래 table 또 쓰면 값이 먹힐까봐 table .table_write로 네이밍 부여
		*/
		.table_write {width: 100%; max-width: 600px;}
		.table_write th { text-align: left; vertical-align: middle; height: 45px;}
		.table_write label {display: inline-block; vertical-align: middle; color: #666;}
		.table_write td {vertical-align: middle;}
		.table_write select,
		.table_write input {
		  box-sizing: border-box;
		  width: 100%;
		  height: 36px;
		  padding: 8px 10px;
		  border: 1px solid #ccc;
		  border-radius: 4px;
		  display: inline-block;
		}
		.table_write select:focus,
		.table_write input:focus {border-color: #53db9b;outline: none;}
    </style>
</head>
<body>
    <div class="wrapper">
      <h1>카드 결제시스템</h1>
      <p class="h1_desc">
      	카드결제 승인, 취소, 이력조회를 위한 페이지입니다.<br>
      	페이지 주소는 /card/cardList 입니다.
      </p>
    </div>>
</body>
</html>