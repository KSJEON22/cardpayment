package com.kakao.cardpayment.controller;
 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kakao.cardpayment.dto.CardDto;
import com.kakao.cardpayment.form.CardForm;
import com.kakao.cardpayment.service.CardService;
 
@Controller
@RequestMapping(value = "/card")
public class CardController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CardService cardService;
 
    @RequestMapping( value = "/cardList")
    //jsp페이지 파싱처리
    public String getCardList(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	logger.debug("cardList.jsp parse in CardController==>");
        return "card/cardList";
    }
    
    //REST API Insert서비스 구현 : 카드 승인(CardPayment 테이블에 승인데이터 적재)
    @RequestMapping(value = "/insertCardPayment")
    @ResponseBody
    public CardDto insertCardPayment(HttpServletRequest request, HttpServletResponse response, CardForm cardForm) throws Exception {
    	logger.debug("insertCardPayment in CardController==>");
        CardDto cardDto = cardService.insertCardPayment(cardForm);
        return cardDto;
    }
    
    //REST API UPDATE서비스 구현 : 카드 취소(CardPayment 테이블에서 카드번호 취소 서비스 결제상태코드 변경으로 취소처리)
    @RequestMapping(value = "/modifyCardPayment")
    @ResponseBody
    public CardDto modifyCardPayment(HttpServletRequest request, HttpServletResponse response, CardForm cardForm) throws Exception {
    	logger.debug("modifyCardPayment in CardController==>");
        CardDto cardDto = cardService.modifyCardPayment(cardForm);
        return cardDto;
    }
    
    //REST API DELETE서비스 구현 : 카드 취소(CardPayment 테이블에서 카드번호 취소 승인데이터 삭제로 카드취소처리)
    @RequestMapping(value = "/deleteCardPayment")
    @ResponseBody
    public CardDto deleteCardPayment(HttpServletRequest request, HttpServletResponse response, CardForm cardForm) throws Exception {
    	logger.debug("deleteCardPayment in CardController==>");
        CardDto cardDto = cardService.deleteCardPayment(cardForm);
        return cardDto;
    }
    

    
    //REST API Get서비스 구현 : 카드 승인(CardPayment 테이블에서 카드번호로 데이터조회)
    @RequestMapping(value = "/getCardPaymentList")
    @ResponseBody
    public CardDto getCardPaymentList(HttpServletRequest request, HttpServletResponse response, CardForm cardForm) throws Exception {
    	logger.debug("getCardPaymentList in CardController==>");
        CardDto cardPaymentList = cardService.getCardPaymentList(cardForm);
        return cardPaymentList;
    }
    
}
