package com.uahannam.member.dto.request.member;

import com.uahannam.member.entity.Member;
import com.uahannam.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegiReqDto {
    private String nickname;
    private String email;
    private String password;
    private String name;
    private String contact;
    private Long role;

    public Member mapToMember(Role role) {
        return Member.builder()
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .contact(this.contact)
                .role(role)
                .build();
    }

}
