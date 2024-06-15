package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	UserInfoRepository userInfoRepository;

	// 正常パータン
	@Test
	void testLogin_success() throws Exception {
		// 准备一个假数据
		UserInfo expectedUserInfo = UserInfo.builder()//
				.username("aki")//
				.password("aki")//
				.build();
		when(userInfoRepository.findByUsername("aki")).thenReturn(expectedUserInfo);

		// 测试
		mvc.perform(MockMvcRequestBuilders.post("/user-login").param("username", "aki").param("password", "aki"))
				.andDo(print()).andExpect(status().isOk()) // 判定状态
				.andExpect(model().attribute("isWrong", false)) // 判定第一个参数是否符合预期
				.andExpect(model().attribute("username", "aki")) // 判定第二个参数是否符合预期
				.andExpect(view().name("hello.html")); // 判定返回的view页面是否符合预期
	}

	// 失敗パータン
	@Test
	void testLogin_fail() throws Exception {
		// 准备一个假数据
		UserInfo expectedUserInfo = UserInfo.builder()//
				.username("aki")//
				.password("aki")//
				.build();
		when(userInfoRepository.findByUsername("aki")).thenReturn(expectedUserInfo);

		// 测试
		mvc.perform(//
				MockMvcRequestBuilders.post("/user-login")//
						.param("username", "aki")//
						.param("password", "password"))// 传入一个错误的密码
				.andDo(print()).andExpect(status().isOk()) // 判定状态
				.andExpect(model().attribute("isWrong", true)) // 判定第一个参数是否符合预期
				.andExpect(view().name("login.html")); // 判定返回的view页面是否符合预期
		
		// 测试
		mvc.perform(//
				MockMvcRequestBuilders.post("/user-login")//
						.param("username", "username")// 传入一个错误的用户名
						.param("password", "aki"))// 传入一个错误的密码
				.andDo(print()).andExpect(status().isOk()) // 判定状态
				.andExpect(model().attribute("isWrong", true)) // 判定第一个参数是否符合预期
				.andExpect(view().name("login.html")); // 判定返回的view页面是否符合预期
		
	}
}
