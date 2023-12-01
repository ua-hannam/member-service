package com.uahannam.member.controller;

import com.uahannam.member.dto.MemberRegiReqDto;
import com.uahannam.member.dto.MemberRegiRespDto;
import com.uahannam.member.dto.MemberResponseDto;
import com.uahannam.member.service.MemberService;
import jakarta.validation.Valid;
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
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Integer memberId) {
        MemberResponseDto memberById = memberService.getMemberById(memberId);
        return ResponseEntity.ok(memberById);
    }

    @GetMapping
    public ResponseEntity<MemberResponseDto> getAllMembers() {
        MemberResponseDto allMembers = memberService.getAllMembers();
        return ResponseEntity.ok(allMembers);
    }
}
