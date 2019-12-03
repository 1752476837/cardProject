package com.ly.cardadmin.controller;

import com.ly.cardadmin.domain.Card;
import com.ly.cardadmin.exception.ExceptionEnum;
import com.ly.cardadmin.exception.LyException;
import com.ly.cardadmin.service.CardService;
import com.ly.cardadmin.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Date;

/**
 * @author Tarry
 * @create 2019/12/2 8:39
 */
@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;



    //条件分页查询名片列表
    @GetMapping("queryList")
    public ResponseEntity<PageResult<Card>> queryList(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String phone,
            @RequestParam(required=false) Long startDate,
            @RequestParam(required=false) Long endDate,
            @RequestParam(required=false) Integer currentPage,
            @RequestParam(required=false) Integer pageSize,
            @RequestParam(required=false) Integer pageTotal
    ){
        Date sDate = null;
        Date eDate = null;
        if (startDate != null){
            sDate = new Date(startDate);
        }
        if (endDate != null){
            eDate = new Date(endDate+86399999);
        }

        PageResult<Card> list = cardService.queryList(name,phone,sDate,eDate,currentPage,pageSize,pageTotal);
        return ResponseEntity.ok(list);
    }

    //根据id，查看名片详情
    @GetMapping("queryCardById/{id}")
    public ResponseEntity<Card> queryCardById(@PathVariable("id") Long id){
        Card card = cardService.queryCardById(id);
        return ResponseEntity.ok(card);
    }

}
