package com.ly.card.controller;

import com.ly.card.config.JwtProperties;
import com.ly.card.domain.Config;
import com.ly.card.domain.User;
import com.ly.card.domain.UserInfo;
import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.service.ConfigService;
import com.ly.card.service.UserService;
import com.ly.card.utils.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Tarry
 * @create 2019/11/29 9:43
 */
@RestController
public class UserController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private UserService userService;
    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    private JwtProperties props;
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;

    @Value("${wx.config.appid}")
    private String appid;
    @Value("${wx.config.secret}")
    private String secret;

    @GetMapping("login")
    public ResponseEntity<String> userLogin(@RequestParam("code") String code, HttpServletResponse response){
        JSONObject jsonObject = this.getopenId(code);
        String openid = jsonObject.get("openid").toString();
        String token = null;
        //通过openid查询数据库是否有此用户
        User user = userService.getUserByOpenId(openid);
        if (user != null){ //用户存在
            token= this.createToken(user);
            response.setHeader("token",token);
            return ResponseEntity.ok(token);
        }
        //新增用户
        User newUser = new User();
        newUser.setOpenid(openid);
        User user2 = userService.insertUser(newUser);
        token= this.createToken(user2);
        response.setHeader("token",token);
        return ResponseEntity.ok(token);
    }

    //创建token
    private String createToken(User user){
        //用户基本信息存入UserInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getOpenid());

        //生成Token
        try {
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            return token;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.GENERATE_TOKEN_FAIL);
        }
    }

/**
    public ResponseEntity login(String code){
        JSONObject jsonObject = this.getopenId(code);

        if(jsonObject!=null&&!jsonObject.containsKey("openid")) {
            throw new LyException(ExceptionEnum.LOGIN_FAIL); //登录失败
        }
        String openid = (String)jsonObject.get("openid");
        String sessionKey = (String)jsonObject.get("session_key");
        //通过openid查询数据库是否有此用户
        User user = userService.getUserByOpenId(openid);
        if (user != null){ //用户存在
            //返回手机号
            if (user.getPhone() == null){
                jsonObject.put("phone", "");
            }else {
                jsonObject.put("phone",user.getPhone());
            }
            stringRedisTemplate.boundValueOps(openid).set(sessionKey,60, TimeUnit.MINUTES);
            jsonObject.put("userId",user.getId());
            jsonObject.put("dateTime","");
            return ResponseEntity.ok(jsonObject.toString());
        }
//        新增用户
        User newUser = new User();
        newUser.setOpenid(openid);
        User user2 = userService.insertUser(newUser);
        jsonObject.put("phone", "");

        stringRedisTemplate.boundValueOps(openid).set(sessionKey,60, TimeUnit.MINUTES);//存到redis中,设置失效时间
        jsonObject.put("userId", user2.getId());
        jsonObject.put("dateTime","");
        return ResponseEntity.ok(jsonObject.toString());
    }

*/



    //请求微信服务器，获取返回值
    private JSONObject getopenId(String code){
//        String appid="XXXXXXXXXXXXX";
//        String secret="XXXXXXXXXXXXXXXXXXXXXXXXXX";
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String jsonData = this.restTemplate.getForObject(url, String.class);
        JSONObject jsonObject= JSONObject.fromObject(jsonData);

        return jsonObject;
    }



    //获取配置参数
    @GetMapping("getConfig")
    public ResponseEntity<Config> getConfig(){
        Config config = configService.getConfig();
        return ResponseEntity.ok(config);
    }
}
