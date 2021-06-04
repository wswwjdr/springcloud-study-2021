package com.wwj.springcloud.controller;

import com.wwj.springcloud.entity.CommonResult;
import com.wwj.springcloud.entity.Payment;
import com.wwj.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tiancai_wwj
 * @create 2021-05-25 7:05
 */
@RestController
@Slf4j
public class PaymentFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    @PostMapping(value = "/consumer/payment/create")
    public CommonResult create(Payment payment) {
        log.info("*****调用了cloud-consummer-feign-order80模块的create方法");
        CommonResult commonResult = paymentFeignService.create(payment);

        if (commonResult != null) {
            return new CommonResult(201, "插入数据库成功", commonResult);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        log.info("====>调用了cloud-consummer-feign-order80模块的getPaymentById方法,id: " + id);
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout() {
        // OpenFeign客户端一般默认等待1秒钟,超过即报错
        return paymentFeignService.paymentFeignTimeout();
    }
}
