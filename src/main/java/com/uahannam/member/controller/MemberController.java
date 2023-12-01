package com.uahannam.member.controller;

import com.uahannam.member.dto.MemberResponseDto;
import com.uahannam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
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
