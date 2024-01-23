package com.uahannam.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegiRespDto {
    String message;
    String jwtToken;
}