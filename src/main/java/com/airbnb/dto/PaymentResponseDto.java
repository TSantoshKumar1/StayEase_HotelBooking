package com.airbnb.dto;

public class PaymentResponseDto {
    private String paymentId;
    private String orderId;
    private String status;
    private String message;

    // Constructor, Getters, and Setters
    public PaymentResponseDto(String paymentId, String orderId, String status, String message) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.status = status;
        this.message = message;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

