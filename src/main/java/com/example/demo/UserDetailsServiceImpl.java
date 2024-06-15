package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserDetailsImpl;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service // サービスクラスであることを示す@Serviceアノテーション
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo user = userInfoRepository.findByUsername(username);
        
        if(user == null) {
			throw new UsernameNotFoundException("Not Found");
		}
                log.info("登陆界面，查找数据库用户信息:{}", user.username);

        return new UserDetailsImpl(user);
    }

}
