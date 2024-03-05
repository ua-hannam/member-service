package com.uahannam.member;

import com.uahannam.member.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
@Slf4j
@RequiredArgsConstructor
public class MemberApplication implements CommandLineRunner {

    private final JwtUtils jwtUtils;

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("토큰 나옴 = {}", jwtUtils.createJwtToken(1L));
    }
}
