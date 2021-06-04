package com.wwj.springcloud.controller;

import com.wwj.springcloud.entity.CommonResult;
import com.wwj.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author tiancai_wwj
 * @create 2021-05-21 23:14
 */
@RestController
@Slf4j
public class OrderController {

    // public static final String PAYMENT_URL = "http://127.0.0.1:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        log.info("消费者调用插入接口~~~");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") long id) {
        log.info("消费者调用查询接口~~~");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping(value = "/order/discovery")
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

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        log.info("===>"+entity.getStatusCode() + "\t" + entity.getHeaders());
        // 状态码为2XX时,请求成功
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();//getForObject()
        } else {
            return new CommonResult<>(444, "操作失败");
        }
    }

}
