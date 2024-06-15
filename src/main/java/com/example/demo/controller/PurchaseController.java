package com.example.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Flight;
import com.example.demo.model.OrderInfo;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.OrderInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PurchaseController {

	@Autowired
	private FlightRepository flightRepository;

	//用户数据库操作
	@Autowired
	private UserInfoRepository userInfoRepository;

	// 订单详情数据库操作
	@Autowired
	private OrderInfoRepository orderInfoRepository;

	@GetMapping("/payPage")
	public ModelAndView getPayPageView(@RequestParam Long id, ModelAndView mv) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			mv.addObject("userName", userDetails.getUsername());
        }
		
		log.info("flight.id:{}", id);

		Flight flight = flightRepository.findById(id).get();
		mv.setViewName("payPage.html");
		mv.addObject("flight", flight);

		log.info("返回前端的mv:{}", mv);
		return mv;
	}

	@GetMapping("/userDetail")
	public ModelAndView getUserDetailView(ModelAndView mv) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			mv.addObject("userName", userDetails.getUsername());
			UserInfo userInfo = userInfoRepository.findByUsername(userDetails.getUsername());
			List<OrderInfo> orderInfos = orderInfoRepository.findByUserInfo(userInfo);

			mv.addObject("orderInfos", orderInfos);
		}


		mv.setViewName("userDetail.html");

		return mv;
	}
	
	@PostMapping("/paymentDetail")
	public ModelAndView gotoFlightlist(@RequestParam String payMethod,
			@RequestParam String id, ModelAndView mv) {

		UserInfo userInfo = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			userInfo = userInfoRepository.findByUsername(userDetails.getUsername());
		}

		log.info("flight.id:{}, payMethod:{}", id, payMethod);

		Flight flight = flightRepository.findById(Long.valueOf(id)).get();
		flight.setStandby(flight.getStandby() - 1);//余票-1

		UUID uuid = UUID.randomUUID();
		String orderNo = uuid.toString();

		OrderInfo orderInfo = OrderInfo.builder()//
				.price(new BigDecimal(flight.getPrice()))//
				.orderNo(orderNo)//
				.payMethod(payMethod)//
				.payTime(LocalDateTime.now())//
				.flight(flight)//
				.userInfo(userInfo)
				.build();


		orderInfoRepository.saveAndFlush(orderInfo);
		mv.setViewName("paymentDetail.html");
		mv.addObject("orderInfo", orderInfo);
		return mv;
	}

}
