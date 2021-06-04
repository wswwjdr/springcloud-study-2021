package com.wwj.springcloud.controller;

import com.wwj.springcloud.entity.CommonResult;
import com.wwj.springcloud.entity.Payment;
import com.wwj.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tiancai_wwj
 * @create 2021-05-19 22:32
 */
@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****serverPort==>" + serverPort + "插入结果：" + result);

        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort==>" + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据库失败,serverPort==>" + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****serverPort==>" + serverPort + "查询结果===>" + payment);

        if (payment != null) {
            return new CommonResult(200, "查询成功,serverPort==>" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录,查询ID: " + id + ",serverPort==>" + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        // 获得注册的服务应用名列表
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("application===>: " + element);
        }
        // 获得指定应用名下的所有微服务
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("server===>: " + instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getServicePort() {
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {
        // 业务逻辑处理正确，但是需要耗费3秒钟
        long startTime = new Date().getTime();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = new Date().getTime();
        String returnStr = "请求耗时:" + (endTime - startTime) + "ms,请求端口:" + serverPort;
        return returnStr;
    }

}
