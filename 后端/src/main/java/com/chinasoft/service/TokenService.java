package com.chinasoft.service;

import com.chinasoft.pojo.User;

public interface TokenService {
	String getToken(User user);
}
