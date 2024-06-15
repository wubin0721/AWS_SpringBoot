package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.demo.model.Flight;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.OrderInfoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

  @Autowired
  MockMvc mvc;

  // 模拟航班列表数据库
  @MockBean
  FlightRepository flightRepository;

  // 模拟订单详情数据库
  @MockBean
  OrderInfoRepository orderInfoRepository;

  // 正常パータン
  @Test
  void testFlight_success() throws Exception {
    // 准备一个假数据
    Flight flight = Flight
      .builder() //
      .id((long) 1)
      .airNo("a01")
      .airName("南方航空")
      .price(1000)
      .departure("北京")
      .arrival("上海")
      .departureDate("2024.04.27")
      .arrivalDate("2024.04.27")
      .startTime("10:00")
      .endTime("15:00")
      .isDirectFlight(true)
      .standby(300)
      .build();

    when(flightRepository.findById(Long.valueOf(1)))
      .thenReturn(Optional.of(flight));

    // 测试
    mvc
      .perform(MockMvcRequestBuilders.get("/payPage").param("id", "1"))
      .andDo(print())
      .andExpect(status().isOk()) // 判定状态
      .andExpect(model().attribute("flight", flight))
      .andExpect(view().name("payPage.html")); // 判定返回的view页面是否符合预期
  }
}
