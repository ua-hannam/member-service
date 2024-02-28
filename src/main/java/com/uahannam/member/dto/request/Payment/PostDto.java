package com.uahannam.member.dto.request.Payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    @NotNull(message = "사용자 id 는 필수입니다.")
    private Long userId;

    @Min(value = 1, message = "충전 금액은 1 이상이어야 합니다.")
    private Integer amount;
}
