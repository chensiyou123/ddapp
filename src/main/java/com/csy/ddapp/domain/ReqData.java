package com.csy.ddapp.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ReqData {
    private Timestamp dateTime;//发送时间
    private String branchNo;//商户分行号
    private String merchantNo;//商户账号
    private Date date;//订单日期
    private String orderNo;//订单号
    private BigDecimal amount;//金额
    private  int expireTimeSpan;//过期时间
    private String payNoticeUrl;//商户接受成功支付地址
    private String payNoticePara;//成功结果附加参数
    private String returnUrl;//返回按钮返回地址
    private String clientIP;//客户的id地址
    private String cardType;//允许支付的卡类型
    private String agrNo;//	客户协议号{未签约（首次支付）客户，填写新协议号，用于协议开通；已签约（再次支付）客户，填写该客户已有的协议号}
    private String merchantSerialNo;//协议开通请求流水号，开通协议时必填。
    private String userID;//商户用户ID,不超过20位,数字字母都可以，建议纯数字
    private String mobile;//商户用户的手机号
    private String lon;//经度
    private String lat;//纬度
    private String riskLevel;//风险等级,用户在商户系统内风险等级标识
    private String signNoticeUrl;//成功签约结果通知地址
    private String signNoticePara;//成功签约结果通知附加参数,
    private String extendInfo;//

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getExpireTimeSpan() {
        return expireTimeSpan;
    }

    public void setExpireTimeSpan(int expireTimeSpan) {
        this.expireTimeSpan = expireTimeSpan;
    }

    public String getPayNoticeUrl() {
        return payNoticeUrl;
    }

    public void setPayNoticeUrl(String payNoticeUrl) {
        this.payNoticeUrl = payNoticeUrl;
    }

    public String getPayNoticePara() {
        return payNoticePara;
    }

    public void setPayNoticePara(String payNoticePara) {
        this.payNoticePara = payNoticePara;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAgrNo() {
        return agrNo;
    }

    public void setAgrNo(String agrNo) {
        this.agrNo = agrNo;
    }

    public String getMerchantSerialNo() {
        return merchantSerialNo;
    }

    public void setMerchantSerialNo(String merchantSerialNo) {
        this.merchantSerialNo = merchantSerialNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSignNoticeUrl() {
        return signNoticeUrl;
    }

    public void setSignNoticeUrl(String signNoticeUrl) {
        this.signNoticeUrl = signNoticeUrl;
    }

    public String getSignNoticePara() {
        return signNoticePara;
    }

    public void setSignNoticePara(String signNoticePara) {
        this.signNoticePara = signNoticePara;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
