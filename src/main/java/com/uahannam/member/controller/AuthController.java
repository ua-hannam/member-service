package com.uahannam.member.controller;

import com.uahannam.member.dto.LoginRequestDto;
import com.uahannam.member.dto.MemberRegiReqDto;
import com.uahannam.member.dto.MemberRegiRespDto;
import com.uahannam.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberRegiRespDto> registerMember(@RequestBody @Valid MemberRegiReqDto memberRegiReqDto) {
        MemberRegiRespDto response = memberService.registerMember(memberRegiReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberRegiRespDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        MemberRegiRespDto result = memberService.login(loginRequestDto); // MemberRegiRespDto 는 재사용성이 있어 네이밍 바꿔야함
        return ResponseEntity.ok(result);
    }
}
