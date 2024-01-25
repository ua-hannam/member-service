package com.uahannam.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRegiDto {
    String message;
    String accessToken;
    String refreshToken;
}