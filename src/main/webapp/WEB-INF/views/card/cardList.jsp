<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>카드결제페이지 구현</title>

<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
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
		  font-size: 26px;
		  font-weight: bold;
		  color: #666;
		}
		/* h1설명문 (결제시스템 아래 작은글씨) */
		.h1_desc {
		  font-size: 14px;
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
		.table_write {width: 100%; max-width: 1000px;}
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
	
	<script type="text/javascript">

    $(document).ready(function(){        
        //getCardList();
    });
 
    function getCardPaymentList(){
        
        $.ajax({            
            type:"POST",
            url:"/card/getCardPaymentList",
            data: {"card_no":input8.value},
            dataType:"JSON",
            success : function(obj) {
                getCardListCallback(obj);                
            },           
            error : function(xhr, status, error) {
            	alert("결제이력을 가져올 수 없습니다. 카드번호를 확인해주세요.");
            	location.reload();
            }
         });
    }
    
	function insertCardPayment(){
	        $.ajax({       
	            url:"/card/insertCardPayment",
	            data: {
	        		"card_no" : input1.value,
		        	"card_vdate" : input2.value,
		        	"card_cvcno" : input3.value,
		        	"pay_stcd" : "00",
		        	"pay_mon" : input4.value,
		        	"payment" : input5.value,
		        	"vat_payment" : input6.value,
		        	"manage_no" : "",
		        	"card_aes" : "",
		        	"etc" : ""
        		},
                cache   : false,
                async   : true,
	            dataType:"JSON",
	            type:"POST",
	            success : function(obj) {
	            	alert("카드결제를 완료 하였습니다.\n" + "관리번호:" + obj.manage_no + "\n" + "결제데이터String :" + obj.result);
	            	location.reload();
	            },           
	            error : function(xhr, status, error) {
	            	alert("카드결제에 실패하였습니다. 카드번호의 값이 올바른지 다시 한 번 확인해주십시오.");
	            }
	         });
	    }
	    
	function modifyCardPayment(){
	    
	    $.ajax({            
	        type:"POST",
	        url:"/card/modifyCardPayment",
	        data: {
	        	"card_no" : input7.value,
	        	"pay_stcd" : "01",
	        	"manage_no" : makeManagementKey()
	        },
            cache   : false,
            async   : true,
	        dataType:"JSON",
	        success : function(obj) {
	        	alert("카드결제를 취소 하였습니다.\n" + "관리번호:" + obj.manage_no + "\n" + "취소데이터String :" + obj.result);
            	location.reload();
	        },           
	        error : function(xhr, status, error) {
	        	alert("카드결제취소에 실패하였습니다. 카드번호의 값이 올바른지 다시 한 번 확인해주십시오.");
	        }
	     });
	}
	
	function deleteCardPayment(){
		
	    $.ajax({            
	        type:"POST",
	        url:"/card/deleteCardPayment",
	        data: {"card_no":input1.value},
	        dataType:"JSON",
            cache   : false,
            async   : true,
	        success : function(obj) {
	        	alert("카드결제를 취소 하였습니다");
            	location.reload();
	        },           
	        error : function(xhr, status, error) {
	        	alert("카드결제취소에 실패하였습니다. 카드번호의 값이 올바른지 다시 한 번 확인해주십시오.");
	        }
	     });
	}
    
    function getCardListCallback(obj){
        
        var list = obj;
        var str = "";
        
        if(list.card_no != null){
                var cardNo       = list.card_no; 
                var cardVdate    = list.card_vdate; 
                var cardVvcno    = list.card_cvcno; 
                var payStcd      = list.pay_stcd; 
                var payMon       = list.pay_mon; 
                var payment      = list.payment; 
                var vatPayment   = list.vat_payment; 
                var manageNo     = list.manage_no;
                var cardAes      = list.card_aes; 
                var etc          = list.etc;
                var inputdate    = list.input_date; 
                var modifydate   = list.modify_date;
                
                str += "<tr>";
                str += "<td>"+ cardNo +"</td>";
                str += "<td>"+ cardVdate +"</td>";
                str += "<td>"+ cardVvcno +"</td>";
                str += "<td>"+ payStcd +"</td>";      
                str += "<td>"+ payMon +"</td>";     
                str += "<td>"+ payment +"</td>";     
                str += "<td>"+ vatPayment +"</td>";     
                str += "</tr>";
        }else{
            
            str += "<tr>";
            str += "<td colspan='7'>카드 승인내역이 존재하지 않습니다.</td>";
            str += "</tr>";
        }
        
        $("#tbody").html(str);
    }
    
	//결제단계 전 필수값들 정합성체크
	function insertValid(){
		var cardno  = input1.value;
		var vdate   = input2.value;
		var	cvcno   = input3.value;
		var	payment = input5.value;
		var	vpayment = input6.value;
		
		if(cardno.length < 10 || cardno == null || cardno == ""){
			alert("카드번호를 확인해 주십시오: 카드번호는 최소 10자리 이상이어야 합니다.");
			return;
		}
		
		if(vdate == null || vdate == "" || vdate.length < 4){
			alert("유효기간이 올바르지 않습니다. 값이 없거나 4자리가 모두 입력되었는지 확인해주십시오.");
			return;
		}
		
		if(cvcno == null || cvcno == "" || cvcno.length < 3){
			alert("CVC값이 올바르지 않습니다. 값이 없거나 3자리가 모두 입력되었는지 확인해주십시오.");
			return;
		}
		
		if(payment == null|| payment == "" || payment*1 < 100){
			alert("카드결제금액이 없거나, 100원미만입니다. 값을 확인해주세요.");
			return;
		}
		
		if(vpayment == null || vpayment == ""){
			alert("부가가치세를 입력하지 않으셨습니다. 자동으로계산되어 입력된 값을 확인하신 후 다시 결제해주세요.결제금액*0.11 을 반올림한 값");
			vpayment = Math.round(payment/11);
			input6.value = vpayment;
			return;
		}else if(payment*1 < vpayment*1){
			alert("부가가치세가 결제가격보다 큽니다. 입력값을 확인해주세요.");
			return;
		}
		confirm("결제하시겠습니까?");
		insertCardPayment();
	}
	
	//결제취소단계 전 필수값들 정합성체크
	function modifyValid(id){
		
		var pivotud = id;
		var cardno  = input7.value;
		
		if(cardno.length < 10 || cardno == null || cardno == ""){
			alert("카드번호를 확인해 주십시오: 카드번호는 최소 10자리 이상이어야 합니다.");
			return;
		}
		if(pivotud == "buttonU"){
			confirm("결제를 취소 하시겠습니까?");
			modifyCardPayment();
		}else if(pivotud == "buttonD"){
			confirm("결제를 취소 하시겠습니까?");
			deleteCardPayment();
		}
	}
	
	//결제취소단계 전 필수값들 정합성체크
	function getListValid(){
		
		var cardno  = input8.value;
		if(cardno.length < 10 || cardno == null || cardno == ""){
			alert("카드번호를 확인해 주십시오: 카드번호는 최소 10자리 이상이어야 합니다.");
			return;
		}		
		confirm("결제이력을 조회하시겠습니까?");
		getCardPaymentList();
	}
	
	//고유관리번호부여
	function makeManagementKey(){ 
	  var date = new Date();
	  var time = date.getTime();
	  var cardno = input1.value.substring(0,4);
	  var cvcno  = input3.value;
	  var manageno = null;
	  //고유관리번호부여 카드번호+CVC+시분초밀리세컨 조합
	  manageno = cardno+cvcno+time;
	  return manageno; 
	}
	
    function checkNum(e) {
   		var keyVal = event.keyCode;
        if(((keyVal >= 48) && (keyVal <= 57))){
            return true;
        }
        else{
            alert("숫자만 입력가능합니다");
            return false;
        }
     } 
</script>
</head>
<body>
	<div class="wrapper">
      <h1>카드 결제시스템</h1>
      <p class="h1_desc">카드결제 승인, 취소, 이력조회를 위한 페이지입니다.</p>
      <section>
        <h2>카드결제하기</h2>
        <div class="contents">
          <table class="table_write">
            <colgroup>
              <col style="width: 35%">
              <col style="width: 50%">
              <col style="width: auto">
            </colgroup>
            <tbody>
              <tr>
                <th><label for="id1">카드번호(10~16자리숫자)</label></th>
                <td><input type="text" name="id1" id="input1" maxlength="16" type="text" onKeyPress="return checkNum(event)"></td>
                <td><input class="button" type="button" id="buttonC" value="결제승인"     onclick="javascript:insertValid();"></td>
              </tr>
              <tr>
                <th><label for="id2">유효기간(월/년도)</label></th>
                <td colspan="2"><input type="text" name="id2" id="input2" maxlength="4" type="text" onKeyPress="return checkNum(event)"></td>
              </tr>
              <tr>
                <th><label for="id3">CVC(3자리숫자)</label></th>
                <td colspan="2"><input type="text" name="id3" id="input3" maxlength="3" type="text" onKeyPress="return checkNum(event)"></td>
              </tr>
              <tr>
                <th><label for="id4">할부개월수</label></th>
                <td colspan="2"><select id="input4" onchange=alerthandler(value)>
	                <option value='00'>일시불</option>
	                <option value='02'>2개월</option>
	                <option value='03'>3개월</option>
	                <option value='04'>4개월</option>
	                <option value='05'>5개월</option>
	                <option value='06'>6개월</option>
	                <option value='07'>7개월</option>
	                <option value='08'>8개월</option>
	                <option value='09'>9개월</option>
	                <option value='10'>10개월</option>
	                <option value='11'>11개월</option>
	                <option value='12'>12개월</option>
	                </select>
                </td>
              </tr>
              <tr>
                <th><label for="id2">결제금액</label></th>
                <td colspan="2"><input type="text" name="id5" id="input5" maxlength="10" type="text" onKeyPress="return checkNum(event)"></td>
              </tr>
              <tr>
                <th><label for="id2">부가가치세</label></th>
                <td colspan="2"><input type="text" name="id6" id="input6" maxlength="10" type="text" onKeyPress="return checkNum(event)"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </section><!-- // section1 -->

      <section>
        <h2>카드결제취소하기</h2>
        <div class="contents">
          <table class="table_write">
            <colgroup>
              <col style="width: 35%">
              <col style="width: 50%">
              <col style="width: auto">
            </colgroup>
            <tbody>
              <tr>
                <th><label for="id1">카드번호(10~16자리숫자)</label></th>
                <td><input type="text" name="id1" id="input7" maxlength="16" type="text" onKeyPress="return checkNum(event)"></td>
                <td><input class="button" type="button" id="buttonU" value="결제취소"     onclick="javascript:modifyValid(id);"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </section><!-- // section2 -->

      <section>
        <h2>카드결제이력 조회하기기</h2>
        <div class="contents">
          <table class="table_write">
            <colgroup>
              <col style="width: 35%">
              <col style="width: 50%">
              <col style="width: auto">
            </colgroup>
            <tbody>
              <tr>
                <th><label for="id1">카드번호(10~16자리숫자)</label></th>
                <td><input type="text" name="id1" id="input8" maxlength="16" type="text" onKeyPress="return checkNum(event)"></td>
                <td><input class="button" type="button" id="buttonR" value="이력조회"     onclick="javascript:getListValid();"></td>
              </tr>
            </tbody>
          </table>
          <table class="table_write">
       		<thead class="contents">
             <tr>
               <td>카드번호</td>
               <td>유효기간</td>
               <td>CVC번호</td>
               <td>결재상태</td>
               <td>할부개월</td>
               <td>결재금액</td>
               <td>부가세</td>
             </tr>
           	</thead>
           	<tbody id="tbody">
          </table>
        </div>
      </section><!-- // section2 -->
    </div><!-- // wrapper -->
</body>
</html>