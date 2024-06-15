package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//注解
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	public String username;
	
	public String password;

	public String roleName;
		
}
