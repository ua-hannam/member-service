package com.uahannam.member.client;

import com.uahannam.member.dto.request.Payment.PostDto;
import com.uahannam.member.dto.response.paymentService.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PaymentService")
public interface PaymentServiceClient {

    @PostMapping("/payments")
    PaymentResponse usePoints(@RequestBody @Valid PostDto postDto);

    @PostMapping("/payments/charge")
    PaymentResponse chargePoint(@RequestBody @Valid PostDto postDto);
}
