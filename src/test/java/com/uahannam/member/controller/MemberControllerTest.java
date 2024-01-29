package com.uahannam.member.controller;

import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.request.UpdateReqDto;
import com.uahannam.member.dto.response.LoginRegiResDto;
import com.uahannam.member.dto.response.RespDto;
import com.uahannam.member.dto.query.MemberDto;
import com.uahannam.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Nested
    @DisplayName("회원 조회")
    class MemberRetrieval {

        @Test
        @DisplayName("단일 회원 조회 - 성공 시 회원 정보 반환")
        void getMember_Success_ReturnsMemberInfo() throws Exception {
            // given - 유효한 회원 ID
            Integer memberId = 1;
            MemberDto memberDto = new MemberDto("nick@example.com", "Nick", "member", "010-1234-5678", "NickName", 0);
            RespDto respDto = new RespDto(memberDto);
            given(memberService.getMemberById(memberId)).willReturn(respDto);

            // when - 단일 회원 조회 API 호출
            mockMvc.perform(get("/members/" + memberId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(respDto)));
        }

        // 추가적인 테스트 케이스들을 여기에 작성
    }

    @Nested
    @DisplayName("회원 등록")
    class MemberRegistration {

        @Test
        @DisplayName("회원 등록 - 유효한 정보로 등록 성공")
        void register_ValidInformation_Success() throws Exception {
            // given - 유효한 회원 등록 정보
            RegiReqDto regiReqDto = new RegiReqDto("NickName", "nick@example.com", "pass123", "Name", "010-1234-5678", 1L);
            LoginRegiResDto regiResp = LoginRegiResDto.builder()
                    .message("Registration Successful")
                    .accessToken("accessToken")
                    .refreshToken("refreshToken")
                    .build();
            given(memberService.registerMember(regiReqDto)).willReturn(regiResp);

            // when - 회원 등록 API 호출
            mockMvc.perform(post("/members/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(regiReqDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(regiResp)));
        }

        // 추가적인 테스트 케이스들을 여기에 작성
    }

    // 유사한 방식으로 다른 테스트 케이스들을 추가할 수 있습니다.
}