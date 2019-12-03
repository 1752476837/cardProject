package com.ly.card.controller;

import com.ly.card.config.LoginInterceptor;
import com.ly.card.domain.Card;
import com.ly.card.domain.vo.BaseCard;
import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/2 10:53
 */
@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;
    @GetMapping("go")
    public void get(){
        throw new LyException(ExceptionEnum.LOGIN_FAIL);
    }


//  用户新增或更新自己的名片
    @PostMapping("update")
    public ResponseEntity<Void> insertCard(@RequestBody Card card){
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
