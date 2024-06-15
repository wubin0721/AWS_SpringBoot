package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.Flight;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.FlightRepository;
import com.example.demo.repository.UserInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {
	@Autowired
	MockMvc mvc;

	@MockBean
	FlightRepository flightRepository;

	@Test
	void testGetSearchView_success() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
	}

//	@Test
//	void testGetSearchView_failure() throws Exception {
//		// 存疑
//		mvc.perform(MockMvcRequestBuilders.get("/123")).andExpect(status().isNotFound());
//	}

	@Test
	void testSearch_success() throws Exception {
		mvc.perform(//
				MockMvcRequestBuilders.post("/search")//
						.param("from", "北京")// 传入正确的出发地址
						.param("to", "上海")// 传入正确的到达地址
						.param("date", "2024-03-02"))//
				.andDo(print())
				.andExpect(status().isOk()); // 判定返回的view页面是否符合预期
	}

	@Test
	void testSearch_failure() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/search")
		.param("from", "北京")// 传入正确的出发地址
		.param("to", "上海"))// 传入正确的到达地址
		.andExpect(status().isNotFound());
		//.param("date", "2024-03-02"))
		
		// mvc.perform(//
		// MockMvcRequestBuilders.post("/search")//
		// .param("departure", "北京")//传入正确的出发地址
		// .param("arrival", "上海"))// 传入正确的到达地址
		// .andDo(print()).andExpect(status().isOk()) //
		// .andExpect(status().isNotFound()); // 传入错误地址
	}
	
	@Test
	void testSearch_rightInfo() throws Exception {
		//假数据
		 
		 List<Flight> flightList = new ArrayList<>();
		 flightList.add(Flight.builder().departure("北京").arrival("上海")
				 .startTime("2024-1-2")
				 .endTime("2024-1-3")
				 .isDirectFlight(false)
				 .price(100)
				 .build());
		 Example<Flight> example = Example.of(Flight.builder().departure("北京").arrival("上海").build());
		 when(flightRepository.findAll(example)).thenReturn(flightList);

		mvc.perform(MockMvcRequestBuilders.post("/search")
		.param("from", "北京")// 传入正确的出发地址
		.param("to", "上海")// 传入正确的到达地址
		.param("date", "2024-03-02"))
		.andExpect(model().attribute("list", flightList))
		.andExpect(view().name("list.html"));
	}
	
	@Test
	void testSearch_wrongInfo() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/search")
		.param("from", "北京")// 传入正确的出发地址
		.param("to", "上海")// 传入正确的到达地址
		.param("date", "2024-03-02"))
		.andExpect(status().isNotFound());
		// mvc.perform(//
		// MockMvcRequestBuilders.post("/search")//
		// .param("departure", "北京")//传入正确的出发地址
		// .param("arrival", "上海"))// 传入正确的到达地址
		// .andDo(print()).andExpect(status().isOk()) //
		// .andExpect(status().isNotFound()); // 传入错误地址
	}
}
