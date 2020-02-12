package com.ly.card.service;

import com.ly.card.domain.Card;
import com.ly.card.domain.vo.BaseCard;
import com.ly.card.exception.ExceptionEnum;
import com.ly.card.exception.LyException;
import com.ly.card.mapper.CardMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Tarry
 * @create 2019/12/2 10:54
 */
@Service
public class CardService {
    @Autowired
    private CardMapper cardMapper;

    //新增或更新名片
    @Transactional
    public void addCard(Long userId, Card card) {
        card.setId(null);
        card.setUserId(userId);
        if (card == null){
            throw new LyException(ExceptionEnum.CONTENT_IS_NOT_NULL);
        }
        if (StringUtils.isBlank(card.getName())){
            throw new LyException(ExceptionEnum.NAME_IS_NOT_NULL);
        }

        int count = cardMapper.queryCountByUserId(userId);
        if (count == 0){ //新增
            Date date = new Date();
            card.setCreateTime(date);
            card.setUpdateTime(date);
            cardMapper.insert(card);
        }else { //修改
            card.setUpdateTime(new Date());
            cardMapper.updateInfo(userId,card);
        }
    }


    //根据 userid 获取名片[基本信息]
    public Card getMyCard(Long userId) {
        return cardMapper.getMyCard(userId);
    }

    //根据userId 获取名片 [详细信息]
    public Card getCardDetail(Long userId) {
        //过滤条件
        Example example = new Example(Card.class);
        Example.Criteria criteria = example.createCriteria();
        //根据名字过滤
        if (userId == null){
            throw new LyException(ExceptionEnum.USERID_IS_NOT_NULL);
        }
        criteria.andEqualTo("userId",userId);
        List<Card> cardList = cardMapper.selectByExample(example);
        if (cardList == null){
            return null;
        }
        return cardList.get(0);
    }

    //查询收藏列表
    public List<Card> queryCardListByUserId(Long userId) {
        return cardMapper.queryCardListByUserId(userId);
    }

    //添加收藏
    public void addCollect(Long userId, Long cardId) {
        cardMapper.addCollect(userId,cardId);
    }

    //移除收藏
    public void removeCollect(Long userId, Long cardId) {
        cardMapper.removeCollect(userId,cardId);
    }

    //校验改名片是否被收藏
    public Boolean verifyCollect(Long userId, Long cardId) {
        Integer count = cardMapper.verifyCollect(userId, cardId);
        //System.out.println("数量："+count);
        return count == 0?false:true;
    }

//    //修改基本信息
//    public void updateBaseInfo(Long userId, BaseCard baseCard) {
//        if (baseCard == null){
//            throw new LyException(ExceptionEnum.CONTENT_IS_NOT_NULL);
//        }
//        if (StringUtils.isBlank(baseCard.getName())){
//            throw new LyException(ExceptionEnum.NAME_IS_NOT_NULL);
//        }
//
//        cardMapper.updateBaseInfo(userId,baseCard);
//    }
}
