package com.ly.cardadmin.controller;

import com.ly.cardadmin.config.JwtProperties;
import com.ly.cardadmin.config.LoginInterceptor;
import com.ly.cardadmin.domain.Admin;
import com.ly.cardadmin.domain.Config;
import com.ly.cardadmin.domain.UserInfo;
import com.ly.cardadmin.domain.vo.Login;
import com.ly.cardadmin.domain.vo.PassWord;
import com.ly.cardadmin.exception.ExceptionEnum;
import com.ly.cardadmin.exception.LyException;
import com.ly.cardadmin.service.AdminService;
import com.ly.cardadmin.service.ConfigService;
import com.ly.cardadmin.utils.CodeUtil;
import com.ly.cardadmin.utils.CookieUtils;
import com.ly.cardadmin.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tarry
 * @create 2019/11/29 13:36
 */
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties props;
    @Autowired
    private ConfigService configService;
    /**
     * 登录授权
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        String token = adminService.login(login.getUsername(),login.getPassword());
        if (StringUtils.isBlank(token)) {
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        //将Token写入cookie中
        CookieUtils.newBuilder(response).maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), token);

        return ResponseEntity.ok().build();
    }

    //设置参数的保存
    @PostMapping("saveConfig")
    public ResponseEntity<Void> saveConfig(@RequestBody Config config){
        configService.saveConfig(config);
        return ResponseEntity.ok().build();
    }

    //获取配置参数
    @GetMapping("getConfig")
    public ResponseEntity<Config> getConfig(){
        Config config = configService.getConfig();
        return ResponseEntity.ok(config);
    }

    @GetMapping("verify")     //获取cookie使用【@CookieValue注解】
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String token,
                                           HttpServletResponse response,
                                           HttpServletRequest request){
        UserInfo info;
        //解析token
        try {
            //1.解析token
            info = JwtUtils.getUserInfo(props.getPublicKey(), token);
            //2.防止token过期，重新生成token
            String newToken = JwtUtils.generateToken(info, props.getPrivateKey(), props.getExpire());
            //写入Cookie
            CookieUtils.newBuilder(response).maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
        } catch (Exception e) {
            //token无效，过期
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        return ResponseEntity.ok().body(info);
    }


    //修改密码，重新登录
    @PostMapping("savePass")
    public ResponseEntity<UserInfo> updatePassWord(@RequestBody PassWord passWord){
        Long id = LoginInterceptor.getLoginUser().getId();
        if (!passWord.getNewPass().equals(passWord.getNewPass1())){
            throw new LyException(ExceptionEnum.PASS_NOT_FIT);
        }

        Admin admin = adminService.queryPassword(id);
        if (admin == null){
            throw new LyException(ExceptionEnum.INVALID_LOGIN_STATE);
        }
        String pass = CodeUtil.md5Hex(passWord.getOldPass(), admin.getSalt());
        String pass1 = admin.getPassword();

        //校验密码
        if(!pass1.equals(pass)){
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }


        //更新密码
        Admin newAdmin = new Admin();
        String salt= CodeUtil.generateSalt();
        newAdmin.setSalt(salt);
        newAdmin.setPassword(CodeUtil.md5Hex(passWord.getNewPass(),salt));
        adminService.updatePassWord(id,newAdmin);

        return ResponseEntity.ok().build();
    }



}
