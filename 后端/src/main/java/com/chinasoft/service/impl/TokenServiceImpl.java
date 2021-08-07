package com.chinasoft.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chinasoft.pojo.User;
import com.chinasoft.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{
	
	@Override
	public String getToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";

        // 生成token，开始时间，结束时间
        token = JWT.create().withAudience(user.getUid() + "").withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPassword()));
        
        return token;
    }
}
