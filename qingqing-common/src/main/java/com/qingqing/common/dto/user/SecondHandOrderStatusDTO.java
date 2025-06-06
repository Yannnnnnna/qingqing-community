package com.qingqing.common.dto.user;

public class SecondHandOrderStatusDTO {
    private Long orderId;
    private String status;

    // getters and setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 