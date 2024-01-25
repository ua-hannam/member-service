package com.uahannam.member.controller;

import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.response.RegiRespDto;
import com.uahannam.member.dto.response.RespDto;
import com.uahannam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<RespDto> getMember(@PathVariable Integer memberId) {
        RespDto memberById = memberService.getMemberById(memberId);
        return ResponseEntity.ok(memberById);
    }

    @GetMapping
    public ResponseEntity<RespDto> getAllMembers() {
        RespDto allMembers = memberService.getAllMembers();
        return ResponseEntity.ok(allMembers);
    }

    @PostMapping("/register")
    public ResponseEntity<RegiRespDto> register(@RequestBody RegiReqDto regiReqDto) {
        RegiRespDto regiResp = memberService.registerMember(regiReqDto);
        return new ResponseEntity<>(regiResp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<RegiRespDto> login(@RequestBody LoginReqDto loginReqDto) {
        RegiRespDto loginResp = memberService.login(loginReqDto);
        return new ResponseEntity<>(loginResp, HttpStatus.OK);
    }
}
