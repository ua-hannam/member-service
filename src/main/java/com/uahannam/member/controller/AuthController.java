package com.uahannam.member.controller;

import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.response.LoginRegiResDto;
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
    public ResponseEntity<LoginRegiResDto> registerMember(@RequestBody @Valid RegiReqDto regiReqDto) {
        LoginRegiResDto response = memberService.registerMember(regiReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRegiResDto> login(@RequestBody LoginReqDto loginReqDto) {
        LoginRegiResDto result = memberService.login(loginReqDto); // MemberRegiRespDto 는 재사용성이 있어 네이밍 바꿔야함
        return ResponseEntity.ok(result);
    }
}
