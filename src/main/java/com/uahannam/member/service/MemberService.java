package com.uahannam.member.service;

import com.uahannam.member.dto.request.member.LoginReqDto;
import com.uahannam.member.dto.request.member.UpdateReqDto;
import com.uahannam.member.dto.response.member.LoginRegiResDto;
import com.uahannam.member.dto.request.member.RegiReqDto;
import com.uahannam.member.dto.response.member.RespDto;
import com.uahannam.member.entity.Member;
import com.uahannam.member.entity.Role;
import com.uahannam.member.exception.CustomException;
import com.uahannam.member.exception.ErrorCode;
import com.uahannam.member.repository.MemberRepository;
import com.uahannam.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

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

        Role role = roleRepository.findById(regiReqDto.getRole())
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_NOT_FOUND_BY_ID));
        memberRepository.save(regiReqDto.mapToMember(role));

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

    public void updateMember(UpdateReqDto updateDto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID));
        updateDto.updateMemberEntity(member);
    }
}
