package com.uahannam.member.controller;

import com.uahannam.member.dto.response.point.PointResponseDto;
import com.uahannam.member.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * 포인트 사용
     *
     * @param memberId 사용자 ID
     * @param amount   사용할 포인트 금액
     * @return 포인트 사용 결과
     */
    @PostMapping("/use/{memberId}")
    public ResponseEntity<PointResponseDto> usePoints(@PathVariable Long memberId, @RequestParam Integer amount) {
        PointResponseDto responseDto = pointService.usePoints(memberId, amount);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 포인트 충전
     *
     * @param memberId 사용자 ID
     * @param amount   충전할 포인트 금액
     * @return 충전 결과
     */
    @PostMapping("/charge/{memberId}")
    public ResponseEntity<PointResponseDto> chargePoints(@RequestHeader("X-CUSTOM-USER-ID") Long userId, @PathVariable Long memberId, @RequestParam Integer amount) {
        System.out.println("=============================================" + userId);

        PointResponseDto chargeRes = pointService.chargePoints(memberId, amount);

        if (chargeRes.isStatus()) {
            return ResponseEntity.ok(chargeRes);
        }

        return ResponseEntity.internalServerError().build();
    }

    /**
     * 포인트 조회
     *
     * @param memberId 사용자 ID
     * @return 사용자의 현재 포인트
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<Integer> getPoints(@PathVariable Long memberId) {
        Integer points = pointService.getPoints(memberId);
        return ResponseEntity.ok(points);
    }
}
