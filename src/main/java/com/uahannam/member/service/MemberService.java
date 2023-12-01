package com.uahannam.member.service;

import com.uahannam.member.dto.MemberResponseDto;
import com.uahannam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getAllMembers() {
        return new MemberResponseDto(memberRepository.findAllMembersAsDto());
    }

    public MemberResponseDto getMemberById(Integer memberId) {
        return new MemberResponseDto(memberRepository.findMemberByIdAsDto(memberId));
    }
}
