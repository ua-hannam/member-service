package com.uahannam.member.dto.response.point;

import lombok.Getter;

@Getter
public class PointResponseDto {
    private boolean status; // 포인트 사용 또는 충전 성공 여부
    private int amount; // 사용 또는 충전된 금액

    // 기본 생성자
    public PointResponseDto() {}

    // 매개변수가 있는 생성자
    public PointResponseDto(boolean status, int amount) {
        this.status = status;
        this.amount = amount;
    }
}
