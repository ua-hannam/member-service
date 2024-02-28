package com.uahannam.member.service;

import com.uahannam.member.dto.response.point.PointResponseDto;

public interface PointService {
    PointResponseDto usePoints(Long memberId, Integer amount);
    PointResponseDto chargePoints(Long memberId, int amount);
    Integer getPoints(Long memberId);
}
