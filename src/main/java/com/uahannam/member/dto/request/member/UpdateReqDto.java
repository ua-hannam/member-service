package com.uahannam.member.dto.request.member;

import com.uahannam.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateReqDto {
    private String nickname;
    private String password;
    private String contact;

    public void updateMemberEntity(Member member) {
        if (nickname != null && !nickname.isEmpty())
            member.setNickname(nickname);

        if (password != null && !password.isEmpty())
            member.setPassword(password);

        if (contact != null && !contact.isEmpty())
            member.setContact(contact);
    }
}