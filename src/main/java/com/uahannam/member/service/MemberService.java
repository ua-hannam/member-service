package com.uahannam.member.service;

import com.uahannam.member.dto.MemberDto;
import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.UpdateMemberReqDto;
import com.uahannam.member.dto.response.LoginRegiResDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.response.RespDto;
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

    public RespDto getAllMembers() {
        return new RespDto(memberRepository.findAllMembersAsDto());
    }

    public RespDto getMemberById(Integer memberId) {
        return new RespDto(memberRepository.findMemberByIdAsDto(memberId));
    }

    public LoginRegiResDto registerMember(RegiReqDto regiReqDto) {
        if (memberRepository.existsByEmail(regiReqDto.getEmail()))
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS, "이미 사용 중인 이메일 입니다.");

        // 비밀번호 암호화, 강도 체크 등등

        memberRepository.save(regiReqDto.mapToMember());

        // 토큰 생성
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        return LoginRegiResDto.builder()
                .message("Registered Successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginRegiResDto login(LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.getEmail());

        if (!member.getPassword().matches(loginReqDto.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        // token 생성
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        return LoginRegiResDto.builder()
                .message("Logined Successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void updateMember(UpdateMemberReqDto updateDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID));
        // 업데이트 로직: DTO의 값을 엔티티에 적용
        updateDto.updateMemberEntity(member);
    }
}
