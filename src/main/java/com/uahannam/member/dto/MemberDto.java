package com.uahannam.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {

    private String email;
    private String name;
    private String role;
    private String contact;
    private String nickname;
    private Integer balance;

    public MemberDto(String email, String name, String role, String contact, String nickname, Integer balance) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.contact = contact;
        this.nickname = nickname;
        this.balance = balance;
    }
}
