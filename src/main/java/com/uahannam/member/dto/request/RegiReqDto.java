package com.uahannam.member.dto.request;

import com.uahannam.member.entity.Member;
import lombok.Getter;

@Getter
public class RegiReqDto {
    private String nickname;
    private String email;
    private String password;
    private String name;
    private String contact;

    public Member mapToMember() {
        return Member.builder()
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .contact(this.contact)
                .build();
    }
}

