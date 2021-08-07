package com.chinasoft.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chinasoft.annotation.PassToken;
import com.chinasoft.annotation.UserLoginToken;
import com.chinasoft.pojo.User;
import com.chinasoft.service.UserService;

/**
* 未登录拦截器
*/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
    	String token = httpServletRequest.getHeader("Authorization");// 从 http 请求头中取出 token
    	System.out.println(token);
    	
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证，无token
                if (token == null) {
                	httpServletResponse.setCharacterEncoding("UTF-8");
                	httpServletResponse.setContentType("application/json; charset=utf-8");
	            	try{
            	      JSONObject json = new JSONObject();
            	      json.put("msg","token verify fail");
            	      json.put("code","50000");
            	      httpServletResponse.getWriter().append(json.toJSONString());
            	    }catch (Exception e){
            	      e.printStackTrace();
            	      httpServletResponse.sendError(500);
            	      return false;
            	    }
	            	
	            	return false;
                }
                
                // 获取 token 中的 user id
                String userId = null;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                	
                	try{
              	      JSONObject json = new JSONObject();
              	      json.put("msg","usertoken verify fail");
              	      json.put("code","401");
              	      httpServletResponse.getWriter().append(json.toJSONString());
              	      System.out.println("身份验证错误");
              	    }catch (Exception e){
              	      e.printStackTrace();
              	      httpServletResponse.sendError(401);
              	      return false;
              	    }
                	return false;
                }

                // 获取用户
                int id = Integer.parseInt(userId);
                User user = userService.findOneUserById(id);
                if (user == null) {
                	try{
              	      JSONObject json = new JSONObject();
              	      json.put("msg","no user");
              	      json.put("code","50000");
              	      httpServletResponse.getWriter().append(json.toJSONString());
              	      System.out.println("不存在该用户");
              	    }catch (Exception e){
              	      e.printStackTrace();
              	      httpServletResponse.sendError(500);
              	      return false;
              	    }
                	return false;
                }
                
                //token 就是jwt生成的token
//                DecodedJWT jwt = JWT.decode(token);
//                Map<String, Claim> claims = jwt.getClaims();
//                Claim exp = claims.get("exp");
//                Date date = exp.asDate();
//                
//                //获取到过期时间了
//                System.out.println("date = " + date);
                
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                	DecodedJWT jwt = jwtVerifier.verify(token);
                	System.out.println("认证通过：");
                    System.out.println("过期时间：      " + jwt.getExpiresAt()); 
                } catch (JWTVerificationException e) {
                	try{
            	      JSONObject json = new JSONObject();
            	      json.put("msg","token has expired");
            	      json.put("code","401");
            	      httpServletResponse.getWriter().append(json.toJSONString());
            	      System.out.println("token已过期");
            	    }catch (Exception ex){
            	      ex.printStackTrace();
            	      httpServletResponse.sendError(401);
            	      return false;
            	    }
                	return false;
                }
                
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
