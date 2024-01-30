package com.uahannam.member.controller;

import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.request.UpdateReqDto;
import com.uahannam.member.dto.response.LoginRegiResDto;
import com.uahannam.member.dto.response.RespDto;
import com.uahannam.member.dto.query.MemberDto;
import com.uahannam.member.exception.CustomException;
import com.uahannam.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static com.uahannam.member.exception.ErrorCode.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
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

        @Test
        @DisplayName("전체 회원 조회 - 성공 시 모든 회원 정보 반환")
        void getAllMembers_Success_ReturnsAllMembers() throws Exception {
            // given - 회원 정보 목록
            MemberDto memberDto1 = new MemberDto(
                    "nick1@example.com", "Nick1", "member", "010-1111-5678", "NickName2", 0);
            MemberDto memberDto2 = new MemberDto(
                    "nick2@example.com", "Nick2", "member", "010-2222-5678", "NickName2", 0);
            RespDto allMembers = new RespDto(Arrays.asList(memberDto1, memberDto2));
            given(memberService.getAllMembers()).willReturn(allMembers);

            // when - 전체 회원 조회 API 호출
            mockMvc.perform(get("/members"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(allMembers)));
        }
    }

    @Nested
    @DisplayName("회원 등록")
    class MemberRegistration {

        @Test
        @DisplayName("유효한 정보로 등록 성공")
        void register_ValidInformation_Success() throws Exception {
            // given - 유효한 회원 등록 정보
            RegiReqDto regiReqDto = new RegiReqDto(
                    "NickName", "nick@example.com", "pass123", "Name", "010-1234-5678", 1L);
            LoginRegiResDto regiResp = LoginRegiResDto.builder()
                    .message("Registration Successful")
                    .accessToken("accessToken")
                    .refreshToken("refreshToken")
                    .build();
            given(memberService.registerMember(any(RegiReqDto.class)))
                    .willReturn(regiResp);

            // when - 회원 등록 API 호출
            ResultActions perform = mockMvc.perform(post("/members/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(regiReqDto)));

            // then - 회원 등록 성공 확인 (status 201)
            perform.andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(regiResp)));
        }

        @Test
        @DisplayName("이미 존재하는 이메일 사용 시 실패")
        void register_ExistingEmail_Fails() throws Exception {
            // given - 이미 존재하는 이메일로 회원 등록 시도
            RegiReqDto regiReqDto = new RegiReqDto(
                    "Nick", "existing@example.com", "pass123", "Nick Name", "010-1234-5678", 1L);
            given(memberService.registerMember(any(RegiReqDto.class)))
                    .willThrow(new CustomException(EMAIL_ALREADY_EXISTS, "existing@example.com"));

            // when - 회원 등록 API 호출
            ResultActions perform = mockMvc.perform(post("/members/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(regiReqDto)));

            // then - 검증
            perform
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("회원 로그인")
    class MemberLogin {
        @Test
        @DisplayName("로그인 - 유효한 자격 증명으로 성공")
        void login_ValidCredentials_Success() throws Exception {
            // given - 유효한 로그인 정보
            LoginReqDto loginReqDto = new LoginReqDto("nick@example.com", "password123");
            LoginRegiResDto loginResp = LoginRegiResDto.builder()
                    .message("Login Successful")
                    .accessToken("accessToken")
                    .refreshToken("refreshToken")
                    .build();
            given(memberService.login(any(LoginReqDto.class)))
                    .willReturn(loginResp);

            // when - 로그인 API 호출
            ResultActions perform = mockMvc.perform(post("/members/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginReqDto)));

            // then - 로그인 성공
            perform
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(loginResp)));
        }

        @Test
        @DisplayName("로그인 - 잘못된 자격 증명으로 실패")
        void login_InvalidCredentials_Fails() throws Exception {
            // given - 잘못된 로그인 정보
            LoginReqDto loginReqDto = new LoginReqDto("wrong@example.com", "wrongpassword");
            given(memberService.login(any(LoginReqDto.class)))
                    .willThrow(new CustomException(LOGIN_FAILED, "Invalid credentials"));

            // when - 로그인 API 호출
            ResultActions perform = mockMvc.perform(post("/members/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginReqDto)));

            // then - 로그인 실패
            perform
                    .andExpect(status().isUnauthorized());
        }

    }

    @Nested
    @DisplayName("회원 정보 수정")
    class MemberUpdate {

        @Test
        @DisplayName("회원 정보 수정 - 유효한 정보로 성공")
        void update_ValidInformation_Success() throws Exception {
            // given - 유효한 정보 수정 요청
            Long memberId = 1L;
            UpdateReqDto updateDto = new UpdateReqDto("newNick", "newPassword", "010-9876-5432");

            // when - 회원 정보 수정 API 호출
            ResultActions perform = mockMvc.perform(put("/members/" + memberId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)));

            // then - 수정 성공
            perform
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("회원 정보 수정 - 존재하지 않는 회원 ID로 실패")
        void update_NonexistentMemberId_Fails() throws Exception {
            // given - 존재하지 않는 회원 ID로 정보 수정 시도
            Long memberId = 999L;
            UpdateReqDto updateDto = new UpdateReqDto("newNick", "newPassword", "010-9876-5432");
            // given() 은 반드시 T 를 받아야 하기 때문에 예외적으로 선언형 Mockito 사용
            doThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId))
                    .when(memberService)
                    .updateMember(any(UpdateReqDto.class), eq(memberId));

            // when - 회원 정보 수정 API 호출
            ResultActions perform = mockMvc.perform(put("/members/" + memberId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)));

            // then - 수정 실패
            perform
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("회원 정보 수정 - 유효하지 않은 정보(빈 필드) 로 실패")
        void update_InvalidInformation_Fails() throws Exception {
            // given - 유효하지 않은 정보(빈 필드)로 정보 수정 시도
            Long memberId = 1L;
            UpdateReqDto updateDto = new UpdateReqDto("", "", ""); // 유효하지 않은 정보
            doThrow(new CustomException(FILED_VALIDATION_FAILED))
                    .when(memberService)
                    .updateMember(any(UpdateReqDto.class), eq(memberId));

            // when - 회원 정보 수정 API 호출
            ResultActions perform = mockMvc.perform(put("/members/" + memberId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)));

            // then - 수정 실패
            perform
                    .andExpect(status().isBadRequest());
        }
    }
}