package com.uahannam.member.service;

import com.uahannam.member.client.PaymentServiceClient;
import com.uahannam.member.dto.request.Payment.PostDto;
import com.uahannam.member.dto.response.paymentService.PaymentResponse;
import com.uahannam.member.dto.response.point.PointResponseDto;
import com.uahannam.member.entity.Member;
import com.uahannam.member.enums.PaymentStatus;
import com.uahannam.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final MemberRepository userRepository; // 예시로 사용되는 UserRepository
    private final PaymentServiceClient paymentServiceClient;

    @Override
    public PointResponseDto usePoints(Long memberId, Integer amount) {
        boolean status = false;

        Member user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 애초에 금액이 부족한 경우
        if (user.getBalance() < amount) {
            return new PointResponseDto(status, 0); // 잔액 부족
        }

        PaymentResponse paymentResponse = paymentServiceClient
                .usePoints(new PostDto(memberId, amount));
        if (paymentResponse.status() == PaymentStatus.SUCCESS) {
            user.setBalance(user.getBalance() - amount); // 포인트 차감
            status = true;
        }

        return new PointResponseDto(status, 0);
    }

    @Override
    public PointResponseDto chargePoints(Long memberId, int amount) {
        boolean status = false;

        Member user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PaymentResponse paymentResponse = paymentServiceClient
                .chargePoint(new PostDto(memberId, amount));
        System.out.println(paymentResponse.toString());

        if (paymentResponse.status() == PaymentStatus.SUCCESS) {
            user.setBalance(user.getBalance() + amount);
            status = true;
        }

        return new PointResponseDto(status, amount);
    }

    @Override
    public Integer getPoints(Long memberId) {
        Member user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return user.getBalance(); // 현재 잔액 반환
    }
}
