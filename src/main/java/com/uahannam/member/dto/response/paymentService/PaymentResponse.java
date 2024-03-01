package com.uahannam.member.dto.response.paymentService;

import com.uahannam.member.enums.PaymentStatus;
import lombok.ToString;

public record PaymentResponse(Long id, PaymentStatus status, Integer amount) {
}