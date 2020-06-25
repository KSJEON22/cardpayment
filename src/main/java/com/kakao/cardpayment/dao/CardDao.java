package com.kakao.cardpayment.dao;
 
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kakao.cardpayment.dto.CardDto;
import com.kakao.cardpayment.form.CardForm;
 
@Repository
public class CardDao {
    @Resource(name = "sqlSession")
    private SqlSession sqlSession;
 
    private static final String NAMESPACE = "com.kakao.cardpayment.cardMapper";
 
    //카드결제승인
    public int insertCardPayment(CardForm cardForm) throws Exception {
    	 
        return sqlSession.insert(NAMESPACE + ".insertCardPayment", cardForm);
    }
    //카드결제수정 --> 상태값 변경으로 취소처리
    public int modifyCardPayment(CardForm cardForm) throws Exception {
    	 
        return sqlSession.update(NAMESPACE + ".modifyCardPayment", cardForm);
    }
    
    // 카드결제삭제 --> 결제이력 자체를전부 삭제
    public int deleteCardPayment(CardForm cardForm) throws Exception {
    	 
        return sqlSession.delete(NAMESPACE + ".deleteCardPayment", cardForm);
    } 
    
    //카드결제조회
    public CardDto getCardPaymentList(CardForm cardForm) throws Exception {
 
        return sqlSession.selectOne(NAMESPACE + ".getCardPaymentList", cardForm);
    }
    
    
}