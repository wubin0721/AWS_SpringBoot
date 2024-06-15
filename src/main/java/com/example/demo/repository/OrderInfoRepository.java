package com.example.demo.repository;

import com.example.demo.model.OrderInfo;
import com.example.demo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
        OrderInfo findByOrderNo(String orderNo);

        List<OrderInfo> findByUserInfo(UserInfo userInfo);
}
