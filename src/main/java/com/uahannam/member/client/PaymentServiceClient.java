package com.uahannam.member.client;

import com.uahannam.member.dto.request.Payment.PostDto;
import com.uahannam.member.dto.response.paymentService.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/payment")
    PaymentResponse usePoints(@RequestBody @Valid PostDto postDto);

    @PostMapping("/payment/charges")
    PaymentResponse chargePoint(@RequestBody @Valid PostDto postDto);
}
