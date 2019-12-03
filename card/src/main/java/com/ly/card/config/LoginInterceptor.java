package com.ly.card.config;

import com.ly.card.domain.UserInfo;
import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.utils.CookieUtils;
import com.ly.card.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bystander
 * @date 2018/10/3
 */
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties props;

    //定义一个线程域，存放登录的对象
    private static final ThreadLocal<UserInfo> t1 = new ThreadLocal<>();

    public LoginInterceptor() {
        super();
    }

    public LoginInterceptor(JwtProperties props) {
        this.props = props;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equals("OPTIONS")){
            return true; //不拦截options请求
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)){
            //用户未登录,返回401，拦截
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        //用户已登录，获取用户信息
        try {
            UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
            //放入线程域中
            t1.set(userInfo);
            return true;
        } catch (Exception e) {
            //抛出异常，未登录
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //生成Token【放到响应头中】
        try {
            String token = JwtUtils.generateToken(t1.get(), props.getPrivateKey(), props.getExpire());
            response.setHeader("token",token);
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.GENERATE_TOKEN_FAIL);
        }

        //过滤器完成后，从线程域中删除用户信息
        t1.remove();
    }

    /**
     * 获取登陆用户
     * @return
     */
    public static UserInfo getLoginUser() {
        return t1.get();
    }
}
