package com.ly.card.controller;

import com.ly.card.config.LoginInterceptor;
import com.ly.card.domain.Card;
import com.ly.card.domain.vo.BaseCard;
import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.service.CardService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Tarry
 * @create 2019/12/2 10:53
 */
@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CardService cardService;
    @Value("${wx.config.appid}")
    private String appid;
    @Value("${wx.config.secret}")
    private String secret;


    @GetMapping("go")
    public void get(){
        throw new LyException(ExceptionEnum.LOGIN_FAIL);
    }

    //请求微信服务器，获取返回值
    private String getToken(){
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
        ResponseEntity<String> forEntity = this.restTemplate.getForEntity(accessTokenUrl, String.class);
        JSONObject jsonObject= JSONObject.fromObject(forEntity.getBody());
        String access_token = jsonObject.get("access_token").toString();
        return access_token;
    }
    public void msgSecCheck(String content){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
//        map.add("content", content);
//请求参数JOSN类型
        JSONObject postData = new JSONObject();
        postData.put("content",content);
     //   HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        String token = getToken();;
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+token;


        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        String code = json.get("errcode").toString();

        if (!code.equals("0")){
            throw new LyException(ExceptionEnum.CONTENT_FAIL);
        }
    }


//  用户新增或更新自己的名片
    @PostMapping("update")
    public ResponseEntity<Void> insertCard(@RequestBody Card card){
        String content = card.getName()+","+card.getAddress()+","+card.getCompany()
                +","+card.getMail()+","
                +card.getPhone()+card.getPosition();
        msgSecCheck(content);
        System.out.println(content);
        Long userId = LoginInterceptor.getLoginUser().getId();
        cardService.addCard(userId,card);
        return ResponseEntity.ok().build();
    }



    //用户首页，用来显示自己的名片【基本信息】
    @GetMapping("myCard")
    public ResponseEntity<Card> getMyCard(){
        Long userId = LoginInterceptor.getLoginUser().getId();
        Card myCard = cardService.getMyCard(userId);
        return ResponseEntity.ok(myCard);
    }

    //显示自己名片的具体内容
    @GetMapping("cardDetail")
    public ResponseEntity<Card> getCardDetail(){
        Long userId = LoginInterceptor.getLoginUser().getId();
        Card card = cardService.getCardDetail(userId);
        return ResponseEntity.ok(card);
    }

    //显示他人的名片具体内容
    @GetMapping("cardDetail/{id}")
    public ResponseEntity<Card> getCardDetailByUserId(@PathVariable("id")Long userId){
        Card card = cardService.getCardDetail(userId);
        return ResponseEntity.ok(card);
    }

    //查询收藏列表
    @GetMapping("collectList")
    public ResponseEntity<List<Card>> queryCardListByUserId(){
        Long userId = LoginInterceptor.getLoginUser().getId();
        List<Card> cardList = cardService.queryCardListByUserId(userId);
        return ResponseEntity.ok(cardList);
    }

    //添加收藏
    @GetMapping("addCollect/{id}")
    public ResponseEntity<Void> addCollect(@PathVariable("id") Long cardId){
        Long userId = LoginInterceptor.getLoginUser().getId();
        cardService.addCollect(userId,cardId);

        return ResponseEntity.ok().build();
    }

    //移除收藏
    @GetMapping("removeCollect/{id}")
    public ResponseEntity<Void> removeCollect(@PathVariable("id") Long cardId){
        Long userId = LoginInterceptor.getLoginUser().getId();
        cardService.removeCollect(userId,cardId);

        return ResponseEntity.ok().build();
    }

    //判断此人是否已收藏
    @GetMapping("verifyCollect/{id}")
    public ResponseEntity<Boolean>  verifyCollect(@PathVariable("id") Long cardId){
        Long userId = LoginInterceptor.getLoginUser().getId();
        Boolean flag = cardService.verifyCollect(userId,cardId);
        return ResponseEntity.ok(flag);
    }

//    //更新基本信息【名称，手机，公司，职位,邮箱，地址】
//    @PutMapping("updateBaseInfo")
//    public ResponseEntity<Void> updateBaseInfo(@RequestBody BaseCard baseCard){
//        Long userId = LoginInterceptor.getLoginUser().getId();
//        cardService.updateBaseInfo(userId,baseCard);
//        return ResponseEntity.ok().build();
//    }

}
