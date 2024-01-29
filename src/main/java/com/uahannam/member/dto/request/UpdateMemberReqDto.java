package com.uahannam.member.dto.request;

import com.uahannam.member.entity.Member;
import lombok.Getter;

@Getter
public class UpdateMemberReqDto {
    private Long id;
    private String nickname;
    private String password;
    private String contact;

    public void updateMemberEntity(Member member) {
        if (!nickname.equals(""))
            member.setNickname(nickname);

        if (!password.equals(""))
            member.setPassword(password);

        if (!contact.equals(""))
            member.setContact(contact);
    }
}