package com.uahannam.member.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberResponseDto {

    private List<MemberDto> members;

    public MemberResponseDto(List<MemberDto> members) {
        this.members = members;
    }

    public MemberResponseDto(MemberDto member) {
        members = new ArrayList<>();
        if (member != null)
            members.add(member);
    }
}
