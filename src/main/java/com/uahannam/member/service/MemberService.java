package com.uahannam.member.service;

import com.uahannam.member.dto.LoginRequestDto;
import com.uahannam.member.dto.MemberRegiRespDto;
import com.uahannam.member.dto.MemberRegiReqDto;
import com.uahannam.member.dto.MemberResponseDto;
import com.uahannam.member.entity.Member;
import com.uahannam.member.exception.CustomException;
import com.uahannam.member.exception.ErrorCode;
import com.uahannam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public MemberRegiRespDto registerMember(MemberRegiReqDto memberRegiReqDto) {
        if (memberRepository.existsByEmail(memberRegiReqDto.getEmail()))
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS, "이미 사용 중인 이메일 입니다.");

        // 비밀번호 암호화, 강도 체크 등등

        memberRepository.save(memberRegiReqDto.mapToMember());

        // 토큰 생성
        String token = "";

        return new MemberRegiRespDto("회원가입이 성공적으로 이뤄졌습니다", token);
    }

    public MemberRegiRespDto login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmail(loginRequestDto.getEmail());

        if (!member.getPassword().matches(loginRequestDto.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        // token 생성
        String token = "";

        return new MemberRegiRespDto("로그인 되었습니다", token);
    }
}
