package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Flight;
import com.example.demo.repository.FlightRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class SearchController {

	@Autowired
	private FlightRepository flightRepository;

	// 検索画面
	@GetMapping("/")
	public String getSearchView() {
		return "search.html";
	}

	// 検索結果を戻す
	@PostMapping("/search")
	public ModelAndView search(@RequestParam String from, @RequestParam String to, @RequestParam String date,
			ModelAndView mv) {

		log.info("查询条件：出发地:{},到达地:{}，时间:{} ", from, to, date);

		Example<Flight> example = Example.of(Flight.builder().departure(from).arrival(to).build());
		List<Flight> flightList = flightRepository.findAll(example);
		
		log.info("数据库的查询结果：{}", flightList);

		mv.addObject("list", flightList);
		mv.setViewName("list.html");
		return mv;
	}

}
