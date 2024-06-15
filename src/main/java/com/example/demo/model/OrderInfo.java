package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.demo.model.UserInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
public class OrderInfo {
    // ID
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    // 订单号
    private String orderNo;
    // 支付方式
    private String payMethod;
    // 支付时间
    private LocalDateTime payTime;
    // 支付金额
    private BigDecimal price;

    //用户信息
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id",referencedColumnName = "id")
    private UserInfo userInfo;

    // 航班信息
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id",referencedColumnName = "id")
    private Flight flight;
}