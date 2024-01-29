package com.uahannam.member.dto.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String email;
    private String name;
    private String role;
    private String contact;
    private String nickname;
    private Integer balance;
}
