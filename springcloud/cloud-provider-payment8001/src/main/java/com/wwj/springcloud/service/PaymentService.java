package com.wwj.springcloud.service;

import com.wwj.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author tiancai_wwj
 * @create 2021-05-19 22:18
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
