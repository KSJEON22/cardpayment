package com.kakao.cardpayment.form;
 
import java.util.Date;
 
public class CardForm {
 
	String card_no;
	String card_vdate;
	String card_cvcno;
	String pay_stcd;
    String pay_mon;
	String payment;
    String vat_payment;
	String manage_no;
    String card_aes;
    String etc;
    String result;
    Date input_date;
    Date modify_date;
    
    public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_vdate() {
		return card_vdate;
	}
	public void setCard_vdate(String card_vdate) {
		this.card_vdate = card_vdate;
	}
	public String getCard_cvcno() {
		return card_cvcno;
	}
	public void setCard_cvcno(String card_cvcno) {
		this.card_cvcno = card_cvcno;
	}
	public String getPay_stcd() {
		return pay_stcd;
	}
	public void setPay_stcd(String pay_stcd) {
		this.pay_stcd = pay_stcd;
	}
	public String getPay_mon() {
		return pay_mon;
	}
	public void setPay_mon(String pay_mon) {
		this.pay_mon = pay_mon;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getVat_payment() {
		return vat_payment;
	}
	public void setVat_payment(String vat_payment) {
		this.vat_payment = vat_payment;
	}
	public String getManage_no() {
		return manage_no;
	}
	public void setManage_no(String manage_no) {
		this.manage_no = manage_no;
	}
	public String getCard_aes() {
		return card_aes;
	}
	public void setCard_aes(String card_aes) {
		this.card_aes = card_aes;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getInput_date() {
		return input_date;
	}
	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
}
