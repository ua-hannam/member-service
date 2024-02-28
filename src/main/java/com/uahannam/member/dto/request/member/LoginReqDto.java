package com.uahannam.member.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginReqDto {
    String email;
    String password;
}
