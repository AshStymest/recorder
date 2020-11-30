package com.example.recorder.Filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.recorder.Entity.User;
import com.example.recorder.Repository.UserRepository;
import com.example.recorder.annotations.PassToken;
import com.example.recorder.annotations.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object object)throws Exception{
        //从http请求头中取出token
        String token = httpServletRequest.getHeader("token");

        //如果不是映射到方法则通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)object;
        Method method = handlerMethod.getMethod();

        //检查是否有passToken注解，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }

        //检查是否有UserLoginToken注解
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                //执行认证
                if(token == null){
                    throw new RuntimeException("无token，请重新登录");
                }
                //获取token中的userId
                String userId;
                try{
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j){
                    throw new RuntimeException("401");
                }

                User user = userRepository.findById(Integer.parseInt(userId)).get();

                Long time = new Date().getTime();
                if(time>user.getDeadLine()){
                        throw new RuntimeException("token已过期，请重新登录");
                }
                if(!token.equals(user.getToken())){
                        throw new RuntimeException("token错误，请重新登陆");
                }
                return true;
            }

        }
        return true;
    }

}
