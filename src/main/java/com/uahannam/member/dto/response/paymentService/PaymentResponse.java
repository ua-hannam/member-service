package com.uahannam.member.dto.response.paymentService;

import com.uahannam.member.enums.PaymentStatus;

public record PaymentResponse(PaymentStatus status, String message) {
}