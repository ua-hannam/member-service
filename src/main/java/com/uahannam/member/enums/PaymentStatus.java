package com.uahannam.member.enums;

// 결제 그 자체의 상태
public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED,  // 결제 실패
    CANCELED,
    DECLINED // Point 가 부족한 경우
}
