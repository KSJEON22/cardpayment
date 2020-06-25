package com.kakao.cardpayment.service;
 
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.cardpayment.dao.CardDao;
import com.kakao.cardpayment.dto.CardDto;
import com.kakao.cardpayment.form.CardForm;
 
@Service
public class CardService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CardDao cardDao;
 
    //카드결제데이터 조회
    public CardDto getCardPaymentList(CardForm cardForm) throws Exception {
 
    	CardDto cardDto = new CardDto();
    	cardDto = cardDao.getCardPaymentList(cardForm);
       	
    	logger.debug("===============getCardPaymentList START=============== + " + cardDto.getCard_aes());
    	
       	String aesd = cardDto.getCard_aes();
       	
       	String key = "aes256-test-key!!";    //key는 16자 이상
       	AES256Util aes256 = new AES256Util(key);
       	URLCodec codec = new URLCodec();
       	 
       	String decryption = aes256.aesDecode(codec.decode(aesd));

       	
       	//String decryption = aes256.aesDecode(key);
    	
       	int declength =  decryption.length();
       	
       	String cardno = decryption.substring(0, declength-7);
       	String cardVdate = decryption.substring(declength-7, declength-3);
       	String cardCvcno = decryption.substring(declength-3, declength);
       	
       	//복호화된 값을 dto에 담기
       	cardDto.setCard_no(cardno);
       	cardDto.setCard_vdate(cardVdate);
       	cardDto.setCard_cvcno(cardCvcno);
       	
       	//카드번호 앞6자리와 뒤 3자리를 제외한 나머지 마스킹처리
       	int cardlength = cardno.length();
       	String cardFront = cardno.substring(0,6);
       	String cardMid = cardno.substring(6,cardlength-3);
       	String cardEnd = cardno.substring(cardlength-3, cardlength);
       	String addStar = null;
       	String maskingCardno = null;
       	
       	for(int i =0 ; i < cardlength-10 ; i++) {
       		if(i==0) {
       			addStar = "*";
       		}else {
       			addStar = addStar + "*";
       		}
       	}
       	maskingCardno = cardFront + addStar + cardEnd;
       	
       	//마스킹처리된 카드번호를 리턴
       	cardDto.setCard_no(maskingCardno);
       	
       	
    	logger.debug("===============getCardPaymentList START===============");
    	logger.debug("key value :==>" + key);
    	logger.debug("decryption :==>" + decryption);
    	logger.debug("cardno :==>" + cardno);
    	logger.debug("cardVdate :==>" + cardVdate);
    	logger.debug("cardCvcno :==>" + cardCvcno);
    	logger.debug("cardFront :==>" + cardFront);
    	logger.debug("cardMid :==>" + cardMid);
    	logger.debug("cardEnd :==>" + cardEnd);
    	logger.debug("Masking cardno :==>" + maskingCardno);
    	
    	return cardDto;
    }
    
    //카드결제데이터 등록
    public CardDto insertCardPayment(CardForm cardForm) throws Exception {
    	//암호화 할 값을 key값에 세팅 카드번호 +유효기간+CVC번호
    	String enkey = cardForm.getCard_no()+cardForm.getCard_vdate()+cardForm.getCard_cvcno();
    	
    	CardDto cardDto = new CardDto();
    	
       	String key = "aes256-test-key!!";    //key는 16자 이상
       	AES256Util aes256 = new AES256Util(key);
       	URLCodec codec = new URLCodec();
    	
    	String encryption = codec.encode(aes256.aesEncode(enkey)); //카드번호 암호화
    	cardForm.setCard_aes(encryption); //암호화된 값을 card_aes필드에저장
    	
    	String decryption = aes256.aesDecode(codec.decode(encryption));
    	
    	logger.debug("===============insertCardPayment START===============");
    	logger.debug("key value :==>" + enkey);
    	logger.debug("encryption :==>" + encryption);
    	logger.debug("decryption :==>" + decryption);
    	
    	//DAO통해서 insert쿼리문 수행 후 결과값 리턴
    	int insertCnt = cardDao.insertCardPayment(cardForm);
    	if (insertCnt > 0) {
    		logger.debug("insertCardPayment in CardService SUCCESS:==>");
    		
    		String header = "_446PAYMENT____";         //헤더부
    		String manageNo1 = "____________________"; //관리번호1
    		String cardNo = cardForm.getCard_no();                        //카드번호
    		String cardNoEmpty = "";                        //카드번호
    		String monNo = cardForm.getPay_mon();      //할부개월수
    		String vDate = cardForm.getCard_vdate();   //유효기간
    		String cvcNo = cardForm.getCard_cvcno();   //cvc번호
    		String pay =  cardForm.getPayment();       //결제금액
    		String payEmpty = "";       //결제금액
    		String vPay = cardForm.getVat_payment();   //부가가치세
    		String vPayEmpty = "";   //부가가치세
    	    String manageNo2 = "____________________"; //관리번호2
    	    String encInfo = cardForm.getCard_aes(); //암호화데이터
    	    String encInfoEmpty = "";                       //암호화정보
    	    String ETC = "";                           //여분공간
    	    String resultStr = "";
    		
    		int cardlen = cardForm.getCard_no().length();
    		int plymentlen = cardForm.getPayment().length();
    		int vpaymentlen = cardForm.getVat_payment().length();
    		int declen = decryption.length();
    		int etcnum = 47;
    		
    		
    	    //카드번호부분 공백란 만들기
    		for(int i=0 ; i < 20-cardlen ; i++){
    			if(i==0){
    				cardNoEmpty = "_";
    			}else{
    				cardNoEmpty = cardNoEmpty + "_";
    			}
    		}
    		
    		//금액부분 공백란 만들기
    		for(int i=0 ; i < 10-plymentlen ; i++){
    			if(i==0){
    				payEmpty = "_";
    			}else{
    				payEmpty = payEmpty + "_";
    			}
    		}
    		
    		//부가금액부분 공백란 만들기
    		for(int i=0 ; i < 10-vpaymentlen ; i++){
    			if(i==0){
    				vPayEmpty = "_";
    			}else{
    				vPayEmpty = vPayEmpty + "_";
    			}
    		}
    		
    		//암호화부분 공백란 만들기
    		for(int i=0 ; i < 300-declen ; i++){
    			if(i==0){
    				encInfoEmpty = "_";
    			}else{
    				encInfoEmpty = encInfoEmpty + "_";
    			}
    		}
    		
    		//기타부분 공백란 만들기
    		for(int i=0 ; i < etcnum ; i++){
    			if(i==0){
    				ETC = "_";
    			}else{
    				ETC = ETC + "_";
    			}
    		}
    		
    		//전문 구성하기
    		resultStr = header + manageNo1 + cardNo + cardNoEmpty + monNo + vDate 
    				   + cvcNo + payEmpty + pay + vPayEmpty + vPay + manageNo2 + encInfo + encInfoEmpty + ETC ;
    		
    		cardDto.setResult(resultStr);
    		logger.debug("insertCardPayment in CardService resultStr:==>\n" + resultStr);
        } else {
        	logger.debug("insertCardPayment in CardService FAIL:==>");
        }
    	
        return cardDto;
    }
    
    //카드결제 데이터를 수정
    public CardDto modifyCardPayment(CardForm cardForm) throws Exception {
    	logger.debug("modifyCardPayment CardService :==>" + cardForm.getCard_no());
    	
    	CardDto cardDto = new CardDto();
    	
    	//수정 후 조회값을 가져와서 수정 전문 구성
    	int insertCnt = cardDao.modifyCardPayment(cardForm);
    	cardDto = cardDao.getCardPaymentList(cardForm);
    	
    	if (insertCnt > 0) {
    		logger.debug("modifyCardPayment in CardService SUCCESS:==>");
    		
    		String header = "_446CANCLE____";         //헤더부
    		String manageNo1 = cardDto.getManage_no(); //관리번호1
    		String cardNo = cardDto.getCard_no();                        //카드번호
    		String cardNoEmpty = "";                        //카드번호
    		String monNo = cardDto.getPay_mon();      //할부개월수
    		String vDate = cardDto.getCard_vdate();   //유효기간
    		String cvcNo = cardDto.getCard_cvcno();   //cvc번호
    		String pay =  cardDto.getPayment();       //결제금액
    		String payEmpty = "";       //결제금액
    		String vPay = cardDto.getVat_payment();   //부가가치세
    		String vPayEmpty = "";   //부가가치세
    	    String manageNo2 = cardDto.getManage_no(); //관리번호2
    	    String encInfo = cardDto.getCard_aes(); //암호화데이터
    	    String encInfoEmpty = "";                       //암호화정보
    	    String ETC = "";                           //여분공간
    	    String resultStr = "";
    		
    		int cardlen = cardDto.getCard_no().length();
    		int plymentlen = cardDto.getPayment().length();
    		int vpaymentlen = cardDto.getVat_payment().length();
    		int declen = cardDto.getCard_aes().length();
    		int etcnum = 47;
    		
    		
    	    //카드번호부분 공백란 만들기
    		for(int i=0 ; i < 20-cardlen ; i++){
    			if(i==0){
    				cardNoEmpty = "_";
    			}else{
    				cardNoEmpty = cardNoEmpty + "_";
    			}
    		}
    		
    		//금액부분 공백란 만들기
    		for(int i=0 ; i < 10-plymentlen ; i++){
    			if(i==0){
    				payEmpty = "_";
    			}else{
    				payEmpty = payEmpty + "_";
    			}
    		}
    		
    		//부가금액부분 공백란 만들기
    		for(int i=0 ; i < 10-vpaymentlen ; i++){
    			if(i==0){
    				vPayEmpty = "_";
    			}else{
    				vPayEmpty = vPayEmpty + "_";
    			}
    		}
    		
    		//암호화부분 공백란 만들기
    		for(int i=0 ; i < 300-declen ; i++){
    			if(i==0){
    				encInfoEmpty = "_";
    			}else{
    				encInfoEmpty = encInfoEmpty + "_";
    			}
    		}
    		
    		//기타부분 공백란 만들기
    		for(int i=0 ; i < etcnum ; i++){
    			if(i==0){
    				ETC = "_";
    			}else{
    				ETC = ETC + "_";
    			}
    		}
    		
    		//전문 구성하기
    		resultStr = header + manageNo1 + cardNo + cardNoEmpty + monNo + vDate 
    				   + cvcNo + payEmpty + pay + vPayEmpty + vPay + manageNo2 + encInfo + encInfoEmpty + ETC ;
    		
    		cardDto.setResult(resultStr);
    		logger.debug("modifyCardPayment in CardService resultStr:==>\n" + resultStr);
    		
    		
    		
        } else {
        	logger.debug("modifyCardPayment in CardService FAIL:==>");
        }
        return cardDto;
    }
    
    //카드결제 데이터를 삭제
    public CardDto deleteCardPayment(CardForm cardForm) throws Exception {
    	logger.debug("CardService :==>" + cardForm.getCard_no());
    	
    	CardDto cardDto = new CardDto();
    	int insertCnt = cardDao.deleteCardPayment(cardForm);
    	if (insertCnt > 0) {
    		logger.debug("deleteCardPayment in CardService SUCCESS:==>");
        } else {
        	logger.debug("deleteCardPayment in CardService FAIL:==>");
        }
        return cardDto;
    }
}
