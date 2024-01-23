package com.uahannam.member.dto.response;

import com.uahannam.member.dto.MemberDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RespDto {

    private List<MemberDto> members;

    public RespDto(List<MemberDto> members) {
        this.members = members;
    }

    public RespDto(MemberDto member) {
        members = new ArrayList<>();
        if (member != null)
            members.add(member);
    }
}
