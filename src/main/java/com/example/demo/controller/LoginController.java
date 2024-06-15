package com.example.demo.controller;

import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class LoginController {

  // 真正的数据库
  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/login")
  public ModelAndView getLoginView(ModelAndView mv) {
    mv.addObject("isWrong", false);
    mv.setViewName("login.html");
    return mv;
  }

  @GetMapping("/register")
  public String getRegisterView(ModelAndView mv) {
    mv.addObject("isRepeat", false);
    return "register.html";
  }

  @PostMapping("/register")
  public ModelAndView register(
    @RequestParam String username,
    @RequestParam String password,
    ModelAndView mv
  ) {
    UserInfo userInfo = userInfoRepository.findByUsername(username);
    log.info("数据库的查询结果：{}", userInfo);

    if (userInfo != null && userInfo.username.equals(username)) {
      mv.addObject("isRepeat", true);
      return mv;
    } else {
      mv.addObject("isRepeat", false);

      String hashedPassword = passwordEncoder.encode(password);

      UserInfo user = UserInfo
        .builder()
        .username(username)
        .password(hashedPassword)
        .roleName("GENERAL")
        .build();

      userInfoRepository.saveAndFlush(user); // 保存处理，成功保存在数据库

      mv.setViewName("redirect:/login");
      return mv;
      // return "redirect:/login";
    }
  }
}
