package com.uahannam.member.controller;

import com.uahannam.member.dto.request.LoginReqDto;
import com.uahannam.member.dto.request.RegiReqDto;
import com.uahannam.member.dto.request.UpdateReqDto;
import com.uahannam.member.dto.response.LoginRegiResDto;
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

    /**
     * <p>단일 회원 정보 조회 API</p>
     *
     * <p>이 메소드는 주어진 memberId에 해당하는 회원의 정보를 조회합니다.
     * 조회된 회원 정보는 {@link RespDto} 객체에 포함되어 반환됩니다.
     * 만약 해당하는 회원이 존재하지 않을 경우, 빈 {@link RespDto} 객체가 반환됩니다.</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId:</b> 조회하고자 하는 회원의 고유 ID. 이 값은 URL 경로에서 받아집니다.</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>회원 정보를 담은 {@link RespDto} 객체와 HTTP 상태 코드 200 (OK).</p>
     *
     * @param memberId 조회하고자 하는 회원의 고유 ID
     * @return ResponseEntity 객체로, 회원 정보가 포함된 {@link RespDto}와 함께 HTTP 상태 코드 200 (OK)를 반환
     */
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
    public ResponseEntity<LoginRegiResDto> register(@RequestBody RegiReqDto regiReqDto) {
        LoginRegiResDto regiResp = memberService.registerMember(regiReqDto);
        return new ResponseEntity<>(regiResp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRegiResDto> login(@RequestBody LoginReqDto loginReqDto) {
        LoginRegiResDto loginResp = memberService.login(loginReqDto);
        return new ResponseEntity<>(loginResp, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity update(@RequestBody UpdateReqDto updateDto, @PathVariable Long memberId) {
        memberService.updateMember(updateDto, memberId);
        return ResponseEntity.ok(updateDto);
    }
}
