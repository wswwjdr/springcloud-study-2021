package com.wwj.springcloud.service;

import org.springframework.stereotype.Service;

/**
 * @author tiancai_wwj
 * @create 2021-05-30 22:32
 */
@Service
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService fall back===>paymentInfo_OK ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService fall back===>paymentInfo_TimeOut ,o(╥﹏╥)o";
    }
}
