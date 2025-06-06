package com.qingqing.common.dto.user;

public class SecondHandOrderCreateDTO {
    private Long buyerId;
    private Long sellerId;
    private Double dealPrice;

    // getters and setters
    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public Double getDealPrice() { return dealPrice; }
    public void setDealPrice(Double dealPrice) { this.dealPrice = dealPrice; }
} 