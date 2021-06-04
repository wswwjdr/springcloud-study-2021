package com.wwj.springcloud.service.Impl;

import com.wwj.springcloud.dao.PaymentDao;
import com.wwj.springcloud.entity.Payment;
import com.wwj.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tiancai_wwj
 * @create 2021-05-19 22:19
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
